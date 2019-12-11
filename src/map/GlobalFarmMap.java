package map;

import java.util.LinkedList;

public class GlobalFarmMap {
    public static FarmMap farmMap;

    static{
        farmMap = new FarmMap();
        generateMap();
    }

    static void generateMap(){
        LinkedList<Point> realTimeList = farmMap.getPoints();
        farmMap.makePointAt(100.0, 100.0);//0
        farmMap.makePointAt(60.0, 60.0).makeNeighbor(realTimeList.get(0));//1
        farmMap.makePointAt(40.0, 40.0).makeNeighbor(realTimeList.get(0));//2
    }
}
