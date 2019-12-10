package map;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

public class FarmMap {
    private LinkedList<Point> points;

    static public Point lowestScore(Hashtable<Point, Integer> list){
        //TODO: Refactor zone
        Integer minScore = Integer.MAX_VALUE;
        Point minScorer = new Point(0.0, 0.0);
        for (Map.Entry<Point, Integer> entry : list.entrySet()){
            if (entry.getValue() < minScore){
                minScorer = entry.getKey();
                minScore = entry.getValue();
            }
        }

        return minScorer;
    }
    public LinkedList<Point> shortestPath(Point start, Point end){
        //TODO: Finish this
        //https://brilliant.org/wiki/a-star-search/
        LinkedList<Point> path = new LinkedList<>();
        Hashtable<Point, Integer> open = new Hashtable<>();
        open.put(start, Integer.MAX_VALUE);
        LinkedList<Point> closed = new LinkedList<>();
        Point current = start;
        while (!closed.contains(end)){
            Point lowestScorer = lowestScore(open);
            if (lowestScorer == end) return path;
            else{
                closed.add(current);
                for (Point p : current.explore()){

                }
            }
        }

        return null;
    }

}
