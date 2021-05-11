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
    SolarSystemPane solarSystem;
    final double duration = 100;

    Circle[] planetObj;

    public MovingPlanets(SolarSystemPane solarSystem) {

        this.solarSystem = solarSystem;
        planetObj = solarSystem.circles;

    }

    public void move(){

        //get length for orbit array
        int n = planets[1].getOrbitX().length;

        SequentialTransition movementSun = new SequentialTransition();

        double sunX = solarSystem.xCenter + solarSystem.unitSize * planets[0].getOrbitX()[0];
        double sunY = solarSystem.yCenter + solarSystem.unitSize * planets[0].getOrbitY()[0];

        for(int i = 0; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[0].getOrbitX()[i]) - sunX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[0].getOrbitY()[i]) - sunY));
            t.setNode(planetObj[0]);
            movementSun.getChildren().add(t);
        }


        SequentialTransition orbitMercury = new SequentialTransition();

        double mercuryX = solarSystem.xCenter + solarSystem.unitSize * planets[1].getOrbitX()[0];
        double mercuryY = solarSystem.yCenter + solarSystem.unitSize * planets[1].getOrbitY()[0];

        for(int i = 0; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[1].getOrbitX()[i]) - mercuryX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[1].getOrbitY()[i]) - mercuryY));
            t.setNode(planetObj[1]);
            orbitMercury.getChildren().add(t);
        }

        SequentialTransition orbitVenus = new SequentialTransition();

        double venusX = solarSystem.xCenter + solarSystem.unitSize * planets[2].getOrbitX()[0];
        double venusY = solarSystem.yCenter + solarSystem.unitSize * planets[2].getOrbitY()[0];

        for(int i = 0; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[2].getOrbitX()[i]) - venusX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[2].getOrbitY()[i]) - venusY));
            t.setNode(planetObj[2]);
            orbitVenus.getChildren().add(t);
        }

        SequentialTransition orbitEarth = new SequentialTransition();

        double earthX = solarSystem.xCenter + solarSystem.unitSize * planets[3].getOrbitX()[0];
        double earthY = solarSystem.yCenter + solarSystem.unitSize * planets[3].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[3].getOrbitX()[i]) - earthX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[3].getOrbitY()[i]) - earthY));
            t.setNode(planetObj[3]);
            orbitEarth.getChildren().add(t);
        }

        SequentialTransition orbitMoon = new SequentialTransition();

        double moonX = solarSystem.xCenter + solarSystem.unitSize * planets[4].getOrbitX()[0];
        double moonY = solarSystem.yCenter + solarSystem.unitSize * planets[4].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[4].getOrbitX()[i]) - moonX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[4].getOrbitY()[i]) - moonY));
            t.setNode(planetObj[4]);
            orbitMoon.getChildren().add(t);
        }

        SequentialTransition orbitMars = new SequentialTransition();

        double marsX = solarSystem.xCenter + solarSystem.unitSize * planets[5].getOrbitX()[0];
        double marsY = solarSystem.yCenter + solarSystem.unitSize * planets[5].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[5].getOrbitX()[i]) - marsX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[5].getOrbitY()[i]) - marsY));
            t.setNode(planetObj[5]);
            orbitMars.getChildren().add(t);
        }

        SequentialTransition orbitJupiter = new SequentialTransition();

        double jupiterX = solarSystem.xCenter + solarSystem.unitSize * planets[6].getOrbitX()[0];
        double jupiterY = solarSystem.yCenter + solarSystem.unitSize * planets[6].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[6].getOrbitX()[i]) - jupiterX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[6].getOrbitY()[i]) - jupiterY));
            t.setNode(planetObj[6]);
            orbitJupiter.getChildren().add(t);
        }

        SequentialTransition orbitSaturn = new SequentialTransition();

        double saturnX = solarSystem.xCenter + solarSystem.unitSize * planets[7].getOrbitX()[0];
        double saturnY = solarSystem.yCenter + solarSystem.unitSize * planets[7].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[7].getOrbitX()[i]) - saturnX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[7].getOrbitY()[i]) - saturnY));
            t.setNode(planetObj[7]);
            orbitSaturn.getChildren().add(t);
        }


        SequentialTransition orbitTitan = new SequentialTransition();

        double titanX = solarSystem.xCenter + solarSystem.unitSize * planets[8].getOrbitX()[0];
        double titanY = solarSystem.yCenter + solarSystem.unitSize * planets[8].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[8].getOrbitX()[i]) - titanX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[8].getOrbitY()[i]) - titanY));
            t.setNode(planetObj[8]);
            orbitTitan.getChildren().add(t);
        }

        SequentialTransition trajectorySpacecraft = new SequentialTransition();

        double spacecraftX = solarSystem.xCenter + solarSystem.unitSize * planets[11].getOrbitX()[0];
        double spacecraftY = solarSystem.yCenter + solarSystem.unitSize * planets[11].getOrbitY()[0];

        for(int i = 1; i < n; i++){
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(duration));
            t.setToX(Math.round(solarSystem.xCenter + solarSystem.unitSize * planets[11].getOrbitX()[i]) - spacecraftX);
            t.setToY((Math.round(solarSystem.yCenter + solarSystem.unitSize * planets[11].getOrbitY()[i]) - spacecraftY));
            t.setNode(solarSystem.spacecraft);
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