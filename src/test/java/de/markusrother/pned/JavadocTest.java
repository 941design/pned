package de.markusrother.pned;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaType;

import de.markusrother.pned.control.requests.Request;

public class JavadocTest {

	private static final String defaultSourcePath = "src/main/java/de/markusrother/pned/";
	static final Pattern inheritPattern = Pattern.compile("^\\{@inheritDoc\\}.*", Pattern.DOTALL);

	private JavaProjectBuilder getBuilder() {
		return getBuilder(defaultSourcePath);
	}

	private JavaProjectBuilder getBuilder(final String path) {
		final JavaProjectBuilder builder = new JavaProjectBuilder();
		builder.addSourceTree(new File(path));
		return builder;
	}

	private Collection<JavaClass> getClasses() {
		return getBuilder().getClasses();
	}

	private Collection<JavaClass> getListenerClasses() {
		final Collection<JavaClass> listeners = new LinkedList<>();
		final Collection<JavaClass> classes = getClasses();
		for (final JavaClass clazz : classes) {
			if (isListener(clazz)) {
				listeners.add(clazz);
			}
		}
		return listeners;
	}

	private boolean isListener(final JavaClass clazz) {
		// TODO - Simplify isA should do!
		if (!clazz.isInterface()) {
			return false;
		}
		final List<JavaClass> interfaces = clazz.getInterfaces();
		if (interfaces.isEmpty()) {
			return false;
		}
		for (final JavaClass iface : interfaces) {
			if (iface.isA(EventListener.class.getName())) {
				return true;
			}
			if (isListener(iface)) {
				return true;
			}
		}
		return false;
	}

	private Collection<JavaClass> getEventClasses() {
		final Collection<JavaClass> events = new LinkedList<>();
		final Collection<JavaClass> classes = getClasses();
		for (final JavaClass clazz : classes) {
			if (isEvent(clazz) && !clazz.getFullyQualifiedName().equals(Request.class.getName())) {
				events.add(clazz);
			}
		}
		return events;
	}

	private boolean isEvent(final JavaClass clazz) {
		return clazz.isA(EventObject.class.getName());
	}

	static abstract class MethodMatcher extends BaseMatcher<JavaMethod> {

		private JavaMethod methodUnderTest;
		private JavaParameter parameterUnderTest;

		@Override
		public boolean matches(final Object item) {
			this.methodUnderTest = (JavaMethod) item;
			final List<DocletTag> tags = methodUnderTest.getTagsByName("param");
			final List<JavaParameter> parameters = methodUnderTest.getParameters();
			final String comment = methodUnderTest.getComment();
			if (comment == null) {
				return false;
			} else if (inheritPattern.matcher(comment).matches()) {
				return true;
			} else if (parameters.size() > tags.size()) {
				return false;
			}
			final Iterator<JavaParameter> paramIterator = parameters.iterator();
			final Iterator<DocletTag> tagIterator = tags.iterator();
			while (paramIterator.hasNext()) {
				final DocletTag tag = tagIterator.next();
				parameterUnderTest = paramIterator.next();
				// TODO - parameter.getTag() should do!
				if (!tagMatchesParameter(tag, parameterUnderTest)) {
					return false;
				}
			}
			while (tagIterator.hasNext()) {
				System.out.println("TODO - expecting generic type " + tagIterator.next().getValue());
			}
			return true;
		}

		public abstract boolean tagMatchesParameter(DocletTag tag, JavaParameter parameter);

		@Override
		public void describeTo(final Description description) {
			description.appendText("not " //
					+ methodUnderTest.getDeclaringClass().getName() + '.'//
					+ methodUnderTest.getName() + "(..)" //
					+ " for parameter: " + parameterUnderTest);
		}

	}

	static abstract class ClassMatcher extends BaseMatcher<JavaClass> {

		private JavaClass classUnderTest;
		private String causeOfFailure;

		@Override
		public boolean matches(final Object item) {
			this.classUnderTest = (JavaClass) item;
			return matches(classUnderTest);
		}

		abstract boolean matches(final JavaClass clazz);

		@Override
		public void describeTo(final Description description) {
			description.appendValue(classUnderTest);
			description.appendValue(causeOfFailure);
		}

		protected boolean mismatch(final String cause) {
			this.causeOfFailure = cause;
			return false;
		}

	}

	@Test
	public void testAllParamsHaveDocletInParameterOrder() {
		final Matcher<JavaMethod> matcher = new MethodMatcher() {
			@Override
			public boolean tagMatchesParameter(final DocletTag tag, final JavaParameter parameter) {
				return tag.getValue().startsWith(parameter.getName());
			}
		};
		assertMatchesAllMethods(matcher);
	}

	@Test
	public void testAllParamDocletsContainQualifiedName() {
		final Matcher<JavaMethod> matcher = new MethodMatcher() {
			@Override
			public boolean tagMatchesParameter(final DocletTag tag, final JavaParameter parameter) {
				final JavaClass clazz = parameter.getJavaClass();
				if (clazz.isArray() || clazz.isPrimitive()) {
					return true;
				}
				final JavaType type = parameter.getType();
				final String qualifiedName = type.getFullyQualifiedName().replaceAll("\\$", ".");
				final String tagValue = tag.getValue();
				if (tagValue.contains(qualifiedName)) { // For debugging!
					return true;
				} else {
					return false;
				}
			}
		};
		assertMatchesAllMethods(matcher);
	}

	@Test
	public void testSeeAlsoDocletsOfEventsHaveQualifiedReferenceToListener() {
		final Collection<JavaClass> listenerClasses = getListenerClasses();
		final Collection<JavaClass> eventClasses = getEventClasses();
		assertThat(eventClasses, everyItem(new ClassMatcher() {

			@Override
			boolean matches(final JavaClass clazz) {
				if (clazz.isAbstract()) {
					return true;
				}
				final List<DocletTag> tags = clazz.getTagsByName("see");
				if (tags.isEmpty()) {
					return mismatch("No tag: @see");
				}
				for (final DocletTag tag : tags) {
					final String value = tag.getValue();
					if (value.contains("Listener")) {
						return true;
					}
				}
				return false;
			}
		}));
	}

	private void assertMatchesAllMethods(final Matcher<JavaMethod> matcher) {
		for (final JavaClass clazz : getClasses()) {
			final List<JavaMethod> methods = clazz.getMethods();
			for (final JavaMethod method : methods) {
				assertThat(method, is(matcher));
			}
		}
	}

}
