package de.markusrother.pned.gui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.markusrother.util.MathUtils;

public class TrigUtilsTest {

    // This was the greatest precision at which THESE tests still passed.
    public static final double precision = 0.0000000000001;

    @Test
    public void testFloorModPi() {
        //
        assertEquals(0.0, floorModPi(precision), precision);
        assertEquals(0.0, floorModPi(Math.PI * 2), precision);
        assertEquals(0.0, floorModPi(Math.PI * 4), precision);
        //
        assertEquals(-0.0, floorModPi(-precision), precision);
        assertEquals(-0.0, floorModPi(-Math.PI * 2), precision);
        assertEquals(-0.0, floorModPi(-Math.PI * 4), precision);
        //
        assertEquals(Math.PI, floorModPi(Math.PI), precision);
        assertEquals(Math.PI, floorModPi(Math.PI * 3), precision);
        //
        assertEquals(-Math.PI, floorModPi(-Math.PI), precision);
        assertEquals(-Math.PI, floorModPi(-Math.PI * 3), precision);
        //
        assertEquals(Math.PI * 0.5, floorModPi(Math.PI * 0.5), precision);
        assertEquals(Math.PI * 0.5, floorModPi(Math.PI * 2.5), precision);
        //
        assertEquals(-Math.PI * 0.5, floorModPi(-Math.PI * 0.5), precision);
        assertEquals(-Math.PI * 0.5, floorModPi(-Math.PI * 2.5), precision);
        //
        assertEquals(Math.PI * 0.123, floorModPi(Math.PI * 0.123), precision);
        assertEquals(Math.PI * 0.123, floorModPi(Math.PI * 2.123), precision);
    }

    private double floorModPi(final double theta) {
        return MathUtils.moduloPi(theta);
    }

}
