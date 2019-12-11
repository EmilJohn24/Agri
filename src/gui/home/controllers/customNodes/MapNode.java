package gui.home.controllers.customNodes;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import map.Point;


public class MapNode extends Circle {
    public static final int ASSIGN_FARM = 1;
    public static final int SHORTEST_PATH = 2;

    private Point associatedPoint;

    public MapNode(Point associatedPoint){
        this.associatedPoint = associatedPoint;
        super.setLayoutX(this.associatedPoint.getX());
        super.setLayoutY(this.associatedPoint.getY());
        super.setStrokeWidth(20.0);
    }



    public Point getAssociatedPoint(){
        return associatedPoint;
    }


}
