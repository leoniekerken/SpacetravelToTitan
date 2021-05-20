package visualization;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.transform.Translate;
import simulator.Planet;

/**
 * zoomable and draggable pane that displays all objects of the solar system
 *
 * @author Chiara & Leo
 */
class ZoomablePane extends Pane {

    static boolean DEBUG = true;

    DoubleProperty myScale = new SimpleDoubleProperty(0.5);

    int width = 1150;
    int height = 1000;
    double xCenter;
    double yCenter;
    double unitSize;

    Group solarSystem;
    Canvas grid;
    Circle[] circles;
    Planet[] planets;
    MovingPlanets movingPlanets;
    Rectangle spacecraft;


    public ZoomablePane() {

        this.setPrefSize(width, height);
        this.setStyle("-fx-background-color:black;");

        //Coordinates of the center
        xCenter = this.getPrefWidth()/2;   //xCenter = 575.0
        yCenter = this.getPrefHeight()/2;  //yCenter = 500.0


        //Add scale transform
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);

        solarSystem = new Group();
        this.getChildren().add(solarSystem);
        circles = new Circle[9];
        planets = Planet.planets;
        movingPlanets = new MovingPlanets(this);
    }

    /**
     * Set x/y pivot points
     * @param x x-coordinate of the pivot point
     * @param y y-coordinate of the pivot point
     */
    public void setPivot( double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);
    }

    /**
     * Set x/y scale
     * @param scale
     */
    public void setScale(double scale) {
        myScale.set(scale);
    }

    /**
     * Add a grid to the canvas (1 unit = 1 AU)
     * SCALING PURPOSES ONLY (can be deleted later)
     */
    public void addGrid() {

        grid = new Canvas(width, height);

        //Set the graphics of the grid
        GraphicsContext gc = grid.getGraphicsContext2D();
        gc.setLineWidth(0.5);
        gc.setStroke(Color.GRAY);

        //Draw grid lines
        unitSize = 50;
        for( double i=unitSize; i < width*2; i+=unitSize) {
            gc.strokeLine( i, 0, i, height);
            gc.strokeLine( 0, i, width, i);
        }

        solarSystem.getChildren().add(grid);
    }

    /**
     * Add planets to solar system
     * 1 unit of the grid = 50 pixels = 1 AU
     * Coordinates of the planets = 50 * planet's distance from the sun (AU)
     * Add 'planet name'
     * Add -/+ depending on the quadrant of the cartesian plane the planet is in
     * @param x x-coordinate of the planet = AU from sun
     * @param y y-coordinate of the planet = AU from sun
     */
    public void addSun(double x, double y) {
        double xSun = xCenter + x;
        double ySun = yCenter + y;
        Circle sun = new Circle(xSun, ySun, 10);
        Image imageSun = new Image(getClass().getResourceAsStream("/resources/Sun.png"));
        ImagePattern imagePatternSun = new ImagePattern(imageSun);
        sun.setFill(imagePatternSun);

        Label labelSun = new Label("Sun");
        Font font = Font.font("Verdana", FontPosture.REGULAR, 3); //Set font of the text
        labelSun.setFont(font);
        labelSun.setTextFill(Color.WHITE);           //Set label color
        labelSun.setTranslateX(xSun - 3);            //Setting the position
        labelSun.setTranslateY(ySun - 15);

        solarSystem.getChildren().add(sun);
        circles[0] = sun;
    }

    public void addMercury(double x, double y) {
        double xMercury = xCenter + unitSize * x;
        double yMercury = yCenter + unitSize * y;
        Circle mercury = new Circle(xMercury, yMercury, 2);
        Image imageMercury = new Image(getClass().getResourceAsStream("/resources/Mercury.png"));
        ImagePattern imagePatternMercury = new ImagePattern(imageMercury);
        mercury.setFill(imagePatternMercury);

        Label labelMercury = new Label("Mercury");
        Font font = Font.font("Verdana", FontPosture.REGULAR, 3); //Set font of the text
        labelMercury.setFont(font);
        labelMercury.setTextFill(Color.WHITE);           //Set label color
        labelMercury.setTranslateX(xMercury - 5);        //Setting the position
        labelMercury.setTranslateY(yMercury - 7);

        solarSystem.getChildren().add(mercury);
        circles[1] = mercury;
    }

    public void addVenus(double x, double y) {
        double xVenus = xCenter + unitSize * x;
        double yVenus = yCenter + unitSize * y;
        Circle venus = new Circle(xVenus, yVenus, 4);
        Image imageVenus = new Image(getClass().getResourceAsStream("/resources/Venus.png"));
        ImagePattern imagePatternVenus = new ImagePattern(imageVenus);
        venus.setFill(imagePatternVenus);

        solarSystem.getChildren().add(venus);
        circles[2] = venus;
    }

    public void addEarth(double x, double y) {
        double xEarth = xCenter + unitSize * x;
        double yEarth = yCenter + unitSize * y;
        if(DEBUG) {
            System.out.println();
            System.out.println("pos x earth: " + x + ", pos y earth: " + y);
            System.out.println("pos unit x earth: " + xEarth + ", pos unit y earth: " + yEarth);
            System.out.println();
        }
        Circle earth = new Circle(xEarth, yEarth, 2);
        Image imageEarth = new Image(getClass().getResourceAsStream("/resources/Earth.png"));
        ImagePattern imagePatternEarth = new ImagePattern(imageEarth);
        earth.setFill(imagePatternEarth);
        solarSystem.getChildren().add(earth);
        circles[3] = earth;
    }

    public void addMoon(double x, double y) {
        double xMoon = xCenter + unitSize * x;
        double yMoon = yCenter + unitSize * y;
        if(DEBUG) {
            System.out.println();
            System.out.println("pos x moon: " + x + ", pos y moon: " + y);
            System.out.println("pos unit x earth: " + xMoon + ", pos unit y earth: " + yMoon);
            System.out.println();
        }
        Circle moon = new Circle(xMoon, yMoon, 1);
        Image imageMoon = new Image(getClass().getResourceAsStream("/resources/Moon.png"));
        ImagePattern imagePatternMoon = new ImagePattern(imageMoon);
        moon.setFill(imagePatternMoon);

        solarSystem.getChildren().add(moon);
        circles[4] = moon;
    }

    public void addMars(double x, double y) {
        double xMars = xCenter + unitSize * x;
        double yMars = yCenter + unitSize * y;
        Circle mars = new Circle(xMars, yMars, 2);
        Image imageMars = new Image(getClass().getResourceAsStream("/resources/Mars.png"));
        ImagePattern imagePatternMars = new ImagePattern(imageMars);
        mars.setFill(imagePatternMars);

        solarSystem.getChildren().add(mars);
        circles[5] = mars;
    }

    public void addJupiter(double x, double y) {
        double xJupiter = xCenter + unitSize * x;
        double yJupiter = yCenter + unitSize * y;
        Circle jupiter = new Circle(xJupiter, yJupiter, 7);
        Image imageJupiter = new Image(getClass().getResourceAsStream("/resources/Jupiter.png"));
        ImagePattern imagePatternJupiter = new ImagePattern(imageJupiter);
        jupiter.setFill(imagePatternJupiter);

        solarSystem.getChildren().add(jupiter);
        circles[6] = jupiter;
    }

    public void addSaturn(double x, double y) {
        double xSaturn = xCenter + unitSize * x;
        double ySaturn = yCenter + unitSize * y;
        Circle saturn = new Circle(xSaturn, ySaturn, 5);
        Image imageSaturn = new Image(getClass().getResourceAsStream("/resources/Saturn.png"));
        ImagePattern imagePatternSaturn = new ImagePattern(imageSaturn);
        saturn.setFill(imagePatternSaturn);
        solarSystem.getChildren().add(saturn);
        circles[7] = saturn;
    }

    public void addTitan(double x, double y) {
        double xTitan = xCenter + unitSize * x;
        double yTitan = yCenter + unitSize * y;
        Circle titan = new Circle(xTitan, yTitan, 1);
        Image imageTitan = new Image(getClass().getResourceAsStream("/resources/Titan.png"));
        ImagePattern imagePatternTitan = new ImagePattern(imageTitan);
        titan.setFill(imagePatternTitan);
        solarSystem.getChildren().add(titan);
        circles[8] = titan;
    }

    public void addSpacecraft(double x, double y){
        double xSpacecraft = xCenter + unitSize * x;
        double ySpacecraft = yCenter + unitSize * y;
        if(DEBUG) {
            System.out.println();
            System.out.println("pos x probe: " + x + ", pos y probe: " + y);
            System.out.println("pos unit x probe: " + xSpacecraft + ", pos unit y probe: " + ySpacecraft);
            System.out.println();
        }
        spacecraft = new Rectangle(xSpacecraft, ySpacecraft, 3.0, 5.0);
        Image imageSpacecraft = new Image(getClass().getResourceAsStream("/resources/Spacecraft.png"));
        ImagePattern imagePatternSpacecraft = new ImagePattern(imageSpacecraft);
        spacecraft.setFill(imagePatternSpacecraft);
        solarSystem.getChildren().add(spacecraft);

    }

    /**
     * puts together all elements of the solarSystem
     * @return
     */
    public ZoomablePane getPane() {
        addGrid();
        addSun(0, 0);
        addMercury(planets[1].getInitX(), planets[1].getInitY());
        addVenus(planets[2].getInitX(), planets[2].getInitY());
        addEarth(planets[3].getInitX(), planets[3].getInitY());
        addMoon((planets[4].getInitX()+0.05), (planets[4].getInitY()+0.05)); //Add the distance from the earth to the moon
        addMars(planets[5].getInitX(), planets[5].getInitY());
        addJupiter(planets[6].getInitX(), planets[6].getInitY());
        addSaturn(planets[7].getInitX(), planets[7].getInitY());
        addTitan((planets[8].getInitX()+0.05), (planets[8].getInitY()+0.05)); //Add the distance from Saturn to Titan
        addSpacecraft(planets[11].getInitX(), planets[11].getInitY());
        return this;
    }

    public double getScale() {
        return myScale.get();
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

    public SceneGestures(ZoomablePane zoomablePane) {
        this.zoomablePane = zoomablePane;
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

            sceneDragContext.translateAnchorX = zoomablePane.getTranslateX();
            sceneDragContext.translateAnchorY = zoomablePane.getTranslateY();

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

            zoomablePane.getTransforms().add(t);

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

            double scale = zoomablePane.getScale();  //currently we only use Y, same value is used for X
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = clamp(scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale)-1;

            double dx = (event.getSceneX() - (zoomablePane.getBoundsInParent().getWidth()/2 + zoomablePane.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (zoomablePane.getBoundsInParent().getHeight()/2 + zoomablePane.getBoundsInParent().getMinY()));

            zoomablePane.setScale(scale);

            //note: pivot value must be untransformed, i. e. without scaling
            zoomablePane.setPivot(f*dx, f*dy);

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