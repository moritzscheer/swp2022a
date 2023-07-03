package de.uol.swp.server.gamelogic;

import java.util.Comparator;

/**
 * @author
 * @since
 */
public class BehaviourTypeComparator implements Comparator<Object> {

    /**
     * @author
     * @since
     */
    @Override
    public int compare(Object obj1, Object obj2) {
        // Vergleichen der BehaviourKlassen
        String behaviourType1 = obj1.getClass().getSimpleName();
        String behaviourType2 = obj2.getClass().getSimpleName();

        // Sortierreihenfolge anpassen
        if (behaviourType1.equals("CheckPointBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("CheckPointBehaviour")) {
            return 1;
        } else if (behaviourType1.equals("PitBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("PitBehaviour")) {
            return 1;
        } else if (behaviourType1.equals("RepairBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("RepairBehaviour")) {
            return 1;
        } else if (behaviourType1.equals("GearBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("GearBehaviour")) {
            return 1;
        } else if (behaviourType1.equals("ConveyorBeltBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("ConveyorBeltBehaviour")) {
            return 1;
        } else if (behaviourType1.equals("ExpressConveyorBeltBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("ExpressConveyorBeltBehaviour")) {
            return 1;
        } else if (behaviourType1.equals("PressorBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("PressorBehaviour")) {
            return 1;
        } else if (behaviourType1.equals("LaserBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("LaserBehaviour")) {
            return 1;
        } else if (behaviourType1.equals("PusherBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("PusherBehaviour")) {
            return 1;
        } else if (behaviourType1.equals("WallBehaviour")) {
            return -1;
        } else if (behaviourType2.equals("WallBehaviour")) {
            return 1;
        }

        return 0; // obj1 und obj2 haben denselben Klassentyp
    }
}
