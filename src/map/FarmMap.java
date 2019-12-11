package map;

import java.util.*;

public class FarmMap {
    private LinkedList<Point> points;

    public Point makePointAt(Double x, Double y){
        Point newPoint = new Point(x, y);
        points.add(newPoint);
        return newPoint;
    }

    public FarmMap(){
        points = new LinkedList<>();
    }
    private LinkedList<Point> reconstructPath(HashMap<Point, Point> cameFrom, Point current){
        LinkedList<Point> totalPath = new LinkedList<>();
        totalPath.add(current);
        while (cameFrom.containsKey(current)){
            current = cameFrom.get(current);
            totalPath.addFirst(current);
        }
        return totalPath;
    }

    public LinkedList<Point> shortestPath(Point start, Point end){
        //TODO: Finish this
        //https://brilliant.org/wiki/a-star-search/
        //A* algorithm
        Double straightPathDistance = start.distanceTo(end);
        ArrayList<Point> openSet = new ArrayList<>();
        openSet.add(start);
        HashMap<Point, Point> cameFrom = new HashMap<>();
        //score from start to a node
        HashMap<Point, Double> gScore = new HashMap<>();
        gScore.put(start, 0.0);

        HashMap<Point, Double> fScore = new HashMap<>();
        fScore.put(start, 0.0);

        Point current = null;
        while (!openSet.isEmpty()){
            //find minimun f-scorer
            Double minScore = Double.MAX_VALUE;
            for (Point p : openSet){
                if (fScore.get(p) < minScore) {
                    current = p;
                    minScore = fScore.get(p);
                }
            }

            if (current == end) return reconstructPath(cameFrom, current);
            openSet.remove(current);

            for (Point neighbor : current.explore()){
                Double tentativeGScore = 0.0;
                gScore.putIfAbsent(neighbor, Double.MAX_VALUE);
                tentativeGScore = gScore.get(current) + current.distanceTo(neighbor);
                if (tentativeGScore < gScore.get(neighbor)){
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, gScore.get(neighbor) + current.distanceTo(neighbor));
                    if (!openSet.contains(neighbor))
                        openSet.add(neighbor);
                }

            }


        }



    return null;
    }

    public LinkedList<Point> getPoints(){
        return points;
    }

}
