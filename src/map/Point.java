package map;

import management.Account;
import management.account_types.Producer;

import java.util.LinkedList;

public class Point {
    private Double x;
    private Double y;
    private Producer owner;
    private LinkedList<Point> adjacencyList;

    Point(Double x, Double y){
        this(x, y, null);
    }

    Point(Double x, Double y, Producer owner){
        this.owner = owner;
        set(x, y);
        adjacencyList = new LinkedList<>();
    }

    public void makeNeighbor(Point newNeighbor){
        addToAdjacency(newNeighbor);
        newNeighbor.addToAdjacency(this);
    }

    void addToAdjacency(Point adj){
        adjacencyList.add(adj);
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


    LinkedList<Point> explore() {
        return adjacencyList;
    }

    public void setOwner(Producer owner) {
        this.owner = owner;
        this.owner.setFarmLocation(this);
    }

    public Producer getOwner() {
        return owner;
    }
}
