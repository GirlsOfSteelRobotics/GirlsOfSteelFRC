package com.girlsofsteel.objects;

import girlsofsteel.objects.ShooterLookupTable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShooterLookupTableTest {
    private static final double EPSILON = 1e-6;

    @Test
    public void testLookup() {
        ShooterLookupTable lookupTable = new ShooterLookupTable();

        assertEquals(0.0, lookupTable.getVelocityFrTable(0), EPSILON); // Below min
        assertEquals(23.7, lookupTable.getVelocityFrTable(4.394), EPSILON); // Right on number
        assertEquals(25.42505747126437, lookupTable.getVelocityFrTable(5.2), EPSILON); // Needs interp
        assertEquals(41.28013777267509, lookupTable.getVelocityFrTable(8.0), EPSILON); // Needs interp
        assertEquals(107.46268656716417, lookupTable.getVelocityFrTable(15.0), EPSILON); // Needs interp
        assertEquals(192.55453501722155, lookupTable.getVelocityFrTable(24.0), EPSILON); // Needs interp
        assertEquals(41.0, lookupTable.getVelocityFrTable(30), EPSILON); // Above max
    }
}
