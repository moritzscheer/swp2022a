package de.uol.swp.server.gamelogic.unitTest.tiles;

import de.uol.swp.server.gamelogic.tiles.enums.TileBehaviour;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TileBehaviourTest {

    /**
     * Tests the enum values of TileBehaviour
     *
     * @author WKempel
     * @since 2023-06-21
     */
    @Test
    public void testEnumValues() {
        // Überprüft, ob die Anzahl der Werte in der Enumeration korrekt ist
        Assertions.assertEquals(9, TileBehaviour.values().length);

        // Überprüft, ob die einzelnen Werte der Enumeration vorhanden sind
        TileBehaviour.valueOf("RepairBehaviour");
        Assertions.assertTrue(true);
        TileBehaviour.valueOf("WallBehaviour");
        Assertions.assertTrue(true);
        TileBehaviour.valueOf("LaserBehaviour");
        Assertions.assertTrue(true);
        TileBehaviour.valueOf("PitBehaviour");
        Assertions.assertTrue(true);
        TileBehaviour.valueOf("ConveyorBeltBehaviour");
        Assertions.assertTrue(true);
        TileBehaviour.valueOf("CheckPointBehaviour");
        Assertions.assertTrue(true);
        TileBehaviour.valueOf("PusherBehaviour");
        Assertions.assertTrue(true);
        TileBehaviour.valueOf("GearBehaviour");
        Assertions.assertTrue(true);
        TileBehaviour.valueOf("PressorBehaviour");
        Assertions.assertTrue(true);
    }
}
