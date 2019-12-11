package gui.home.controllers;

import gui.home.controllers.customNodes.MapNode;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import management.GlobalSessionHolder;
import map.GlobalFarmMap;
import map.Point;

import java.util.HashMap;
import java.util.LinkedList;

public class MapController {
    @FXML
    private Pane mapContainer;
    private Point userPoint;
    private MapNode userNode;
    private HashMap<Point, MapNode> nodes;

    private void clickedMapNode(MapNode clickedMapNode){
        if (userPoint == null){
            GlobalSessionHolder.currentSession.getSessionAccount().setFarmLocation(clickedMapNode.getAssociatedPoint());
        }
        else{
            LinkedList<Point> routeTo = GlobalFarmMap.farmMap.shortestPath(userPoint, clickedMapNode.getAssociatedPoint());
            for (Point point : routeTo){
                nodes.get(point).setFill(Color.RED);
            }
        }
        loadPoints();
    }

    @FXML
    private void initialize(){
        nodes = new HashMap<>();
        loadPoints();
    }


    private void loadPoints(){
        userPoint = GlobalSessionHolder.currentSession.getSessionAccount().getFarmLocation();
        mapContainer.getChildren().removeAll(mapContainer.getChildren());
        for (Point point : GlobalFarmMap.farmMap.getPoints()){
            MapNode newNode = new MapNode(point);
            newNode.setFill(Color.GREEN);
            if (point == userPoint) userNode = newNode;
            nodes.put(point, newNode);
            newNode.setOnMouseClicked(event -> clickedMapNode(newNode));
            newNode.setRadius(20.0);
            mapContainer.getChildren().add(newNode);
        }
        if (userNode != null){
            userNode.setFill(Color.YELLOW);
        }


    }
}
