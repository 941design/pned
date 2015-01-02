package de.markusrother.pned;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
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

	static abstract class MethodMatcher extends BaseMatcher<JavaMethod> {

		@Override
		public boolean matches(final Object item) {
			final JavaMethod method = (JavaMethod) item;
			final List<DocletTag> tags = method.getTagsByName("param");
			final List<JavaParameter> parameters = method.getParameters();
			final String comment = method.getComment();
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
				final JavaParameter parameter = paramIterator.next();
				// TODO - parameter.getTag() should do!
				if (!tagMatchesParameter(tag, parameter)) {
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
			description.appendText("TODO");
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

	private void assertMatchesAllMethods(final Matcher<JavaMethod> matcher) {
		for (final JavaClass clazz : getClasses()) {
			final List<JavaMethod> methods = clazz.getMethods();
			for (final JavaMethod method : methods) {
				assertThat(method, is(matcher));
			}
		}
	}

}
