package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.server.gamelogic.BehaviourTypeComparator;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.common.game.enums.CardinalDirection;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * Map Builder
 *
 * @author Finn Oldeboershuis
 * @since 2023-04-28
 */
public final class MapBuilder {

    public static List<AbstractMap> maps = new LinkedList<AbstractMap>();



    public static Block[][] getMap(String mapPath) {
        try {
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(mapPath));
            Block[][] map = (Block[][]) objIn.readObject();
            objIn.close();
            return map;
        } catch (IOException | ClassNotFoundException IOExcept) {
            System.out.println(IOExcept.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws IOException {

        maps.add(new MapOne());
        maps.add(new MapTwo());

        mapGen();

        Block[][] map = getMap("server/src/main/resources/maps/MapOne.map");
        if (map != null) {
            System.out.println(map.length);
        }
    }

    public static void mapGen() throws IOException {
        for (AbstractMap mapClass : maps) {
            Block[][] map = mapGenExtracted(mapClass.getMap());


            String path = "server/src/main/resources/maps/" + mapClass.getClass().getName().replace("de.uol.swp.server.gamelogic.map.", "") + ".map";

            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(path));
            objOut.writeObject(map);
            objOut.flush();
            objOut.close();
        }
    }

    private static Block[][] mapGenExtracted(Block[][] mapFromClass) {
        int x = 0;
        int y = 0;


        Block[][] map = mapFromClass;

        for (y = 0; y < 12; y++) {

            for (x = 0; x < 12; x++) {
                //boolean pitBehaviourFound = false;
                //boolean conveyorBeltBehaviourFound = false;
                //boolean repairBehaviourFound = false;
                //boolean gearBehaviourFound = false;

                for (int i = 0; i < map[x][y].getBehaviourList().length; i++) {
                    if (map[x][y].getBehaviourList()[i] instanceof LaserBehaviour) {
                        if (((LaserBehaviour) map[x][y].getBehaviourList()[i]).getStart() == true) {
                            laserStart(map, x, y, ((LaserBehaviour) map[x][y].getBehaviourList()[i]).getLaserBeam(), ((LaserBehaviour) map[x][y].getBehaviourList()[i]).getLaserDirection());
                        }
                    }
                    /*
                    else if(map[x][y].getBehaviourList()[i] instanceof PitBehaviour){
                        pitBehaviourFound = true;
                    }
                    else if(map[x][y].getBehaviourList()[i] instanceof ConveyorBeltBehaviour || map[x][y].getBehaviourList()[i] instanceof ExpressConveyorBeltBehaviour){
                        conveyorBeltBehaviourFound = true;
                    }
                    else if(map[x][y].getBehaviourList()[i] instanceof RepairBehaviour){
                        repairBehaviourFound = true;
                    }
                    else if(map[x][y].getBehaviourList()[i] instanceof GearBehaviour){
                        gearBehaviourFound = true;
                     */
                }


            }
        }


        for (y = 0; y < 12; y++) {

            for (x = 0; x < 12; x++) {

                map[x][y].setBehaviourList(sortBehaviourList(map[x][y].getBehaviourList()));
            }
        }
        return map;
    }
    private static void generateBlock(
            Block[][] map, int x, int y, AbstractTileBehaviour... behaviours) {
        map[x][y] = new Block(behaviours, null, new Position(x, y));
    }

    private static void laserStart(Block[][] map,int x, int y, int beam, CardinalDirection direction){
        int x2 = x;
        int y2 = y;

        boolean loop = true;
        CardinalDirection opposite = null;
        boolean innerWallorLaser = false;
        boolean fullLaser = true;

        while(loop) {

            switch (direction) {
                case West:
                    x2--;
                    opposite = CardinalDirection.East;
                    break;
                case East:
                    x2++;
                    opposite = CardinalDirection.West;
                    break;
                case South:
                    y2++;
                    opposite = CardinalDirection.North;
                    break;
                case North:
                    y2--;
                    opposite = CardinalDirection.South;
                    break;
                default:
                    break;
            }

            if(x2 >= 0 && y2 >= 0 && x2<= 11 && y2 <= 11 ) {
                for (int i = 0; i < map[x2][y2].getBehaviourList().length; i++) {
                    // Check surrounding
                    if (map[x2][y2].getBehaviourList()[i] instanceof WallBehaviour) {
                        if (map[x2][y2].getBehaviourList()[i].getObstruction(opposite)) {
                            loop = false;
                            innerWallorLaser = true;
                        } else if (map[x2][y2].getBehaviourList()[i].getObstruction(direction)) {
                            loop = false;
                        }
                    }
                    if (map[x2][y2].getBehaviourList()[i] instanceof PusherBehaviour && map[x2][y2].getBehaviourList()[i].getObstruction(direction)) {
                        fullLaser = false;
                        loop = false;
                    }
                    if (map[x2][y2].getBehaviourList()[i] instanceof LaserBehaviour ) {
                        if (((LaserBehaviour) map[x2][y2].getBehaviourList()[i]).getStart() &&
                                ((LaserBehaviour) map[x2][y2].getBehaviourList()[i]).getLaserDirection() == direction ||
                                ((LaserBehaviour) map[x2][y2].getBehaviourList()[i]).getLaserDirection() == opposite) {
                            ((LaserBehaviour) map[x2][y2].getBehaviourList()[i]).setStart(false);
                            ((LaserBehaviour) map[x2][y2].getBehaviourList()[i]).setLaserBeam(beam);
                            innerWallorLaser = true;
                        }

                    }
                }

                //Place Laser
                if (!innerWallorLaser) {
                    AbstractTileBehaviour[] copy = new AbstractTileBehaviour[map[x2][y2].getBehaviourList().length + 1];
                    System.arraycopy(map[x2][y2].getBehaviourList(), 0, copy, 0, copy.length - 1);
                    copy[copy.length - 1] = new LaserBehaviour(null, map, new int[]{1, 2, 3, 4, 5}, new Position(x2, y2), direction, beam, fullLaser);
                    map[x2][y2] = new Block(copy, null, new Position(x2, y2));
                }
            }
            else{
                loop = false;
            }
        }
    }


    private static AbstractTileBehaviour[] sortBehaviourList(AbstractTileBehaviour[] behaviourList){
        Arrays.sort(behaviourList, new BehaviourTypeComparator());

        AbstractTileBehaviour[] uniqueBehaviourList = new LinkedHashSet<>(Arrays.asList(behaviourList)).toArray(new AbstractTileBehaviour[0]);

        return behaviourList;
    }

}
