package visualization;

import simulator.Planet;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.control.Label;


public class SolarSystemPane extends Pane {

    static boolean DEBUG = true;

    DoubleProperty myScale = new SimpleDoubleProperty(2.0); //Enable zooming also here

    Group solarSystem;
    ZoomablePane zoomablePane;
    Canvas grid;
    MovingPlanets movement;

    int height;
    int width;

    double xCenter;
    double yCenter;

    double unitSize;

    Circle sun;
    Circle jupiter;
    Circle saturn;
    Circle titan;

    Rectangle spacecraft;

    Circle[] circles;

    Planet [] planets;

    //Constructor
    public SolarSystemPane() {

        if(DEBUG){
            System.out.println();
            System.out.println();
            System.out.println("SolarSystemPane DEBUG");
            System.out.println();
            System.out.println();
        }

        solarSystem = new Group();

        this.getChildren().add(solarSystem);

        height = 1000;
        width = 1150;

        zoomablePane = new ZoomablePane();
        zoomablePane.setPrefSize(width, height);
        zoomablePane.setStyle("-fx-background-color:black;");

        solarSystem.getChildren().add(zoomablePane);

        //Coordinates of the center
        xCenter = zoomablePane.getPrefWidth()/2;   //xCenter = 575.0
        yCenter = zoomablePane.getPrefHeight()/2;  //yCenter = 500.0

        circles = new Circle[9];

        planets = Planet.planets;

        //init movement class with duration of each movement and pane
        movement = new MovingPlanets(this);
    }

    /**
     * Add a grid to the canvas (1 unit = 1 AU)
     * SCALING PURPOSES ONLY (can be deleted later)
     */
    //20 x 23 = measurements of the grid in units
    public void addGrid() {

        grid = new Canvas(width*2, height*2);

        //Set the graphics of the grid
        GraphicsContext gc = grid.getGraphicsContext2D();
        gc.setLineWidth(0.5);
        gc.setStroke(Color.GRAY);

        //Draw grid lines
        unitSize = 50;
        for( double i=unitSize; i < width*2; i+=unitSize) {
            gc.strokeLine( i, 0, i, height*2); //Vertical lines = 40
            gc.strokeLine( 0, i, width*2, i);  //Horizontal lines = 46
        }

        solarSystem.getChildren().add(grid);
    }

    /**
     * Method to convert m to AU
     */
    public double fromMToAU(double m) {
        return m*6.6846e-12;
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
        sun = new Circle(xSun, ySun, 10);
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

    //0.39 AU
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

    //0.72 AU
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

    //1 AU
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

    //1.52 AU
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

    //5.2 AU
    public void addJupiter(double x, double y) {
        double xJupiter = xCenter + unitSize * x;
        double yJupiter = yCenter + unitSize * y;
        jupiter = new Circle(xJupiter, yJupiter, 7);
        Image imageJupiter = new Image(getClass().getResourceAsStream("/resources/Jupiter.png"));
        ImagePattern imagePatternJupiter = new ImagePattern(imageJupiter);
        jupiter.setFill(imagePatternJupiter);

        solarSystem.getChildren().add(jupiter);
        circles[6] = jupiter;
    }

    //9.54 AU
    public void addSaturn(double x, double y) {
        double xSaturn = xCenter + unitSize * x;
        double ySaturn = yCenter + unitSize * y;
        saturn = new Circle(xSaturn, ySaturn, 5);
        Image imageSaturn = new Image(getClass().getResourceAsStream("/resources/Saturn.png"));
        ImagePattern imagePatternSaturn = new ImagePattern(imageSaturn);
        saturn.setFill(imagePatternSaturn);
        solarSystem.getChildren().add(saturn);
        circles[7] = saturn;
    }

    public void addTitan(double x, double y) {
        double xTitan = xCenter + unitSize * x;
        double yTitan = yCenter + unitSize * y;
        titan = new Circle(xTitan, yTitan, 1);
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

    //Returns all the planets to the solar system
    public SolarSystemPane getPane() {
        addGrid();
        addSun(0, 0); //addSun(fromKMToAU(physics.sun.getX()), fromKmToAU(physics.sun.getY()));
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

    public ZoomablePane getZoomPane(){
        return zoomablePane;
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
    public void setScale( double scale) {
        myScale.set(scale);
    }

    public double getScale() {
        return myScale.get();
    }



}