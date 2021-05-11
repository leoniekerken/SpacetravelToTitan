package visualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.input.ScrollEvent;

public class SolarSystemStage extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("SolarSystem");

        Pane center = new Pane();
        center.setStyle("-fx-background-color:black");
        Pane right = new Pane();

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, Color.BLACK);
        SolarSystemPane solarSystemPane = new SolarSystemPane();
        SidePane sidePane = new SidePane();

        center.getChildren().add(solarSystemPane.getPane());
        right.getChildren().add(sidePane.getPane());

        root.setCenter(center);
        root.setRight(right);

        //Control mouse movements on pane
        SceneGestures sceneGestures = new SceneGestures(solarSystemPane.zoomablePane, solarSystemPane);
        center.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        center.setOnMousePressed(sceneGestures.getOnEnteredEventHandler());
        center.setOnMouseDragged(sceneGestures.getOnDragEventHandler());

        solarSystemPane.movement.move();

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
