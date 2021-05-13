package visualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.input.ScrollEvent;

/**
 * set the stage for the visualization
 *
 * @author Chiara & Leo
 */
public class SolarSystemStage extends Application {

    static boolean DEBUG = true;

    @Override
    public void start(Stage stage) {

        stage.setTitle("SolarSystem");

        BorderPane root = new BorderPane();

        Pane center = new Pane();
        center.setStyle("-fx-background-color:black");

        Pane right = new Pane();

        ZoomablePane zoomablePane = new ZoomablePane();
        SidePane sidePane = new SidePane();

        center.getChildren().add(zoomablePane.getPane());
        right.getChildren().add(sidePane.getPane());

        root.setCenter(center);
        root.setRight(right);

        Scene scene = new Scene(root);

        //Control mouse movements on pane
        SceneGestures sceneGestures = new SceneGestures(zoomablePane);
        center.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        center.setOnMousePressed(sceneGestures.getOnEnteredEventHandler());
        center.setOnMouseDragged(sceneGestures.getOnDragEventHandler());

        //start movements of objects in solarSystem
        zoomablePane.movingPlanets.move();

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
