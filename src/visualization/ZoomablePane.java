package visualization;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

class ZoomablePane extends Pane {

    DoubleProperty myScale = new SimpleDoubleProperty(0.005);

    public ZoomablePane() {
        setPrefSize(10000, 10000);

        //Add scale transform
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);
    }

    public double getScale() {
        return myScale.get();
    }

    public void setScale( double scale) {
        myScale.set(scale);
    }

    public void setPivot(double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);
    }
}

/**
 * Mouse drag context
 */
class DragContext {

    double mouseAnchorX;
    double mouseAnchorY;

    double translateAnchorX;
    double translateAnchorY;
}

/**
 * Listeners for making the scene's canvas draggable and zoomable
 */
class SceneGestures {

    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;

    private DragContext sceneDragContext = new DragContext();

    ZoomablePane zoomablePane;
    SolarSystemPane solarSystemPane;

    public SceneGestures(ZoomablePane zoomablePane, SolarSystemPane solarSystemPane) {
        this.zoomablePane = zoomablePane;
        this.solarSystemPane = solarSystemPane;
    }

    public EventHandler<MouseEvent> getOnEnteredEventHandler() {
        return onEnteredEventHandler;
    }

    public EventHandler<MouseEvent> getOnDragEventHandler() {
        return onDragEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    /**
     * Mouse pressed handler: to get starting position of drag
     */
    EventHandler<MouseEvent> onEnteredEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            //starting coordinates for dragging
            sceneDragContext.mouseAnchorX = event.getX();
            sceneDragContext.mouseAnchorY = event.getY();

            sceneDragContext.translateAnchorX = solarSystemPane.getTranslateX();
            sceneDragContext.translateAnchorY = solarSystemPane.getTranslateY();

            event.consume();
        }
    };

    /**
     * Mouse drag handler: drag mouse to move pane
     */
    public EventHandler<MouseEvent> onDragEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            sceneDragContext.translateAnchorX = event.getX();
            sceneDragContext.translateAnchorY = event.getY();

            double translateX = sceneDragContext.translateAnchorX - sceneDragContext.mouseAnchorX;
            double translateY = sceneDragContext.translateAnchorY - sceneDragContext.mouseAnchorY;

            Translate t = new Translate();
            t.setX(translateX/8);
            t.setY(translateY/8);

            solarSystemPane.getTransforms().add(t);

            event.consume();
        }
    };

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    public EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<>() {

        @Override
        public void handle(ScrollEvent event) {

            double delta = 1.2;

            double scale = solarSystemPane.getScale();  //currently we only use Y, same value is used for X
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = clamp(scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale)-1;

            double dx = (event.getSceneX() - (solarSystemPane.getBoundsInParent().getWidth()/2 + solarSystemPane.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (solarSystemPane.getBoundsInParent().getHeight()/2 + solarSystemPane.getBoundsInParent().getMinY()));

            solarSystemPane.setScale(scale);

            //note: pivot value must be untransformed, i. e. without scaling
            solarSystemPane.setPivot(f*dx, f*dy);

            event.consume();
        }
    };

    public static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }
}