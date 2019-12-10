package map;

import management.Account;

import java.util.LinkedList;

public class Point {
    private Double x;
    private Double y;
    private Account owner;
    private LinkedList<Point> adjacencyList;

    Point(Double x, Double y){
        set(x, y);
    }

    static Double distance(Point a, Point b){
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    Double distanceTo(Point point){
        return distance(this, point);
    }

    void setX(Double x){this.x = x;}
    void setY(Double y){this.y = y;}
    void set(Double x, Double y){
        setX(x);
        setY(y);
    }

    public LinkedList<Point> explore() {
        return adjacencyList;
    }
}
