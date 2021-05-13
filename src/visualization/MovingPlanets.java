package visualization;

import simulator.Planet;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.*;
import javafx.util.Duration;

/**
 * called to start movement of the planets
 */
public class MovingPlanets {

    Planet[] planets = Planet.planets;
    ZoomablePane zoomablePane;
    final double duration = 100;

    Circle[] planetObj;

    public MovingPlanets(ZoomablePane zoomablePane) {

        this.zoomablePane = zoomablePane;
        planetObj = zoomablePane.circles;

    }

    public void move(){

        //get length for orbit array
        int n = planets[1].getOrbitX().length;

        SequentialTransition movementSun = new SequentialTransition();

        double sunX = zoomablePane.xCenter + zoomablePane.unitSize * planets[0].getOrbitX()[0];
        double sunY = zoomablePane.yCenter + zoomablePane.unitSize * planets[0].getOrbitY()[0];

        for(int i = 0; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[0].getOrbitX()[i]) - sunX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[0].getOrbitY()[i]) - sunY));
            t.setNode(planetObj[0]);
            movementSun.getChildren().add(t);
        }


        SequentialTransition orbitMercury = new SequentialTransition();

        double mercuryX = zoomablePane.xCenter + zoomablePane.unitSize * planets[1].getOrbitX()[0];
        double mercuryY = zoomablePane.yCenter + zoomablePane.unitSize * planets[1].getOrbitY()[0];

        for(int i = 0; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[1].getOrbitX()[i]) - mercuryX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[1].getOrbitY()[i]) - mercuryY));
            t.setNode(planetObj[1]);
            orbitMercury.getChildren().add(t);
        }

        SequentialTransition orbitVenus = new SequentialTransition();

        double venusX = zoomablePane.xCenter + zoomablePane.unitSize * planets[2].getOrbitX()[0];
        double venusY = zoomablePane.yCenter + zoomablePane.unitSize * planets[2].getOrbitY()[0];

        for(int i = 0; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[2].getOrbitX()[i]) - venusX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[2].getOrbitY()[i]) - venusY));
            t.setNode(planetObj[2]);
            orbitVenus.getChildren().add(t);
        }

        SequentialTransition orbitEarth = new SequentialTransition();

        double earthX = zoomablePane.xCenter + zoomablePane.unitSize * planets[3].getOrbitX()[0];
        double earthY = zoomablePane.yCenter + zoomablePane.unitSize * planets[3].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[3].getOrbitX()[i]) - earthX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[3].getOrbitY()[i]) - earthY));
            t.setNode(planetObj[3]);
            orbitEarth.getChildren().add(t);
        }

        SequentialTransition orbitMoon = new SequentialTransition();

        double moonX = zoomablePane.xCenter + zoomablePane.unitSize * planets[4].getOrbitX()[0];
        double moonY = zoomablePane.yCenter + zoomablePane.unitSize * planets[4].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[4].getOrbitX()[i]) - moonX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[4].getOrbitY()[i]) - moonY));
            t.setNode(planetObj[4]);
            orbitMoon.getChildren().add(t);
        }

        SequentialTransition orbitMars = new SequentialTransition();

        double marsX = zoomablePane.xCenter + zoomablePane.unitSize * planets[5].getOrbitX()[0];
        double marsY = zoomablePane.yCenter + zoomablePane.unitSize * planets[5].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[5].getOrbitX()[i]) - marsX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[5].getOrbitY()[i]) - marsY));
            t.setNode(planetObj[5]);
            orbitMars.getChildren().add(t);
        }

        SequentialTransition orbitJupiter = new SequentialTransition();

        double jupiterX = zoomablePane.xCenter + zoomablePane.unitSize * planets[6].getOrbitX()[0];
        double jupiterY = zoomablePane.yCenter + zoomablePane.unitSize * planets[6].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[6].getOrbitX()[i]) - jupiterX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[6].getOrbitY()[i]) - jupiterY));
            t.setNode(planetObj[6]);
            orbitJupiter.getChildren().add(t);
        }

        SequentialTransition orbitSaturn = new SequentialTransition();

        double saturnX = zoomablePane.xCenter + zoomablePane.unitSize * planets[7].getOrbitX()[0];
        double saturnY = zoomablePane.yCenter + zoomablePane.unitSize * planets[7].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[7].getOrbitX()[i]) - saturnX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[7].getOrbitY()[i]) - saturnY));
            t.setNode(planetObj[7]);
            orbitSaturn.getChildren().add(t);
        }


        SequentialTransition orbitTitan = new SequentialTransition();

        double titanX = zoomablePane.xCenter + zoomablePane.unitSize * planets[8].getOrbitX()[0];
        double titanY = zoomablePane.yCenter + zoomablePane.unitSize * planets[8].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[8].getOrbitX()[i]) - titanX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[8].getOrbitY()[i]) - titanY));
            t.setNode(planetObj[8]);
            orbitTitan.getChildren().add(t);
        }

        SequentialTransition trajectorySpacecraft = new SequentialTransition();

        double spacecraftX = zoomablePane.xCenter + zoomablePane.unitSize * planets[11].getOrbitX()[0];
        double spacecraftY = zoomablePane.yCenter + zoomablePane.unitSize * planets[11].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(zoomablePane.xCenter + zoomablePane.unitSize * planets[11].getOrbitX()[i]) - spacecraftX);
            t.setToY((Math.round(zoomablePane.yCenter + zoomablePane.unitSize * planets[11].getOrbitY()[i]) - spacecraftY));
            t.setNode(zoomablePane.spacecraft);
            trajectorySpacecraft.getChildren().add(t);
        }

        movementSun.setCycleCount(100);
        orbitMercury.setCycleCount(100);
        orbitVenus.setCycleCount(100);
        orbitEarth.setCycleCount(100);
        orbitMoon.setCycleCount(100);
        orbitMars.setCycleCount(100);
        orbitJupiter.setCycleCount(100);
        orbitSaturn.setCycleCount(100);
        orbitTitan.setCycleCount(100);
        trajectorySpacecraft.setCycleCount(100);

        movementSun.play();
        orbitMercury.play();
        orbitVenus.play();
        orbitEarth.play();
        orbitMoon.play();
        orbitMars.play();
        orbitJupiter.play();
        orbitSaturn.play();
        orbitTitan.play();
        trajectorySpacecraft.play();

    }
}