package simulator;

/**
 * this class initializes the constellation of objects of the solar system on 01-04-2020
 * as found on https://ssd.jpl.nasa.gov/horizons.cgi
 *
 * @author Oscar
 */

public class PlanetStart2020 {

    public Planet[] planets;

    public PlanetStart2020() {

        //array of all objects
        planets = new Planet[12];

        //sun
        Planet sun = new Planet("Sun");
        sun.mass = 1.988500e+30;
        sun.radius = 6957e+05;
        sun.gravity = 274;
        sun.positionX = -6.807847851469811E+08;
        sun.positionY = 1.079960529004681E+09;
        sun.positionZ = 6.577801896302961E+06;
        sun.velocityX = -1.420511174031879E+01;
        sun.velocityY = -4.954720611047453E+00;
        sun.velocityZ = 3.994075214250549E-01;
        sun.vectors();
        planets[0] = sun;

        //mercury
        Planet mercury = new Planet("Mercury");
        mercury.mass = 3.302e+23;
        mercury.radius = 2440e+03;
        mercury.gravity = 3.7;
        mercury.positionX = 5.941270492988122E+06;
        mercury.positionY = -6.801804551422262E+10;
        mercury.positionZ = -5.702728562917408E+09;
        mercury.velocityX = 3.892585186728442E+04;
        mercury.velocityY = 2.978342169553609E+03;
        mercury.velocityZ = -3.327964322084350E+03;
        mercury.vectors();
        planets[1] = mercury;

        //venus
        Planet venus = new Planet("Venus");
        venus.mass = 4.8685e+24;
        venus.radius = 6051e+03;
        venus.gravity = 8.87;
        venus.positionX = -9.435356118127899E+10;
        venus.positionY = 5.350355062360455E+10;
        venus.positionZ = 6.131466820352264E+09;
        venus.velocityX = -1.726404291136308E+04;
        venus.velocityY = -3.073432516609996E+04;
        venus.velocityZ = 5.741783064386929E-04;
        venus.vectors();
        planets[2] = venus;

        //earth
        Planet earth = new Planet("Earth");
        earth.mass = 5.97219e+24;
        earth.radius = 6371e+3;
        earth.gravity = 9.807;
        earth.positionX = -1.471923166635424E+11;
        earth.positionY = -2.861000299246477E+10;
        earth.positionZ = 8.291942464411259E+06;
        earth.velocityX = 5.427193376018815E+03;
        earth.velocityY = -2.931056623471520E+04;
        earth.velocityZ = 6.575114893578871E-01;
        earth.vectors();
        planets[3] = earth;

        //moon
        Planet moon = new Planet ("Moon");
        moon.mass = 7.349e+22;
        moon.radius = 1737e+03;
        moon.gravity = 1.62;
        moon.positionX = -1.472344969594165E+11;
        moon.positionY = -2.822582844526653E+10;
        moon.positionZ = 1.054166983666271E+07;
        moon.velocityX = 4.433121575882713E+03;
        moon.velocityY = -2.948453616031408E+04;
        moon.velocityZ = 8.896594867343843E+01;
        moon.vectors();
        planets[4] = moon;

        //mars
        Planet mars = new Planet ("Mars");
        mars.mass = 6.4171e+23;
        mars.radius = 3389e+03;
        mars.gravity = 3.711;
        mars.positionX = -3.615638921529161E+10;
        mars.positionY = -2.167633037046744E+11;
        mars.positionZ = -3.687670305939779E+09;
        mars.velocityX = 2.481551975121696E+04;
        mars.velocityY = -1.816368005464070E+03;
        mars.velocityZ = -6.467321619018108E+02;
        mars.vectors();
        planets[5] = mars;

        //jupiter
        Planet jupiter = new Planet("Jupiter");
        jupiter.mass= 1.89813e+27;
        jupiter.radius = 69911e+03;
        jupiter.gravity = 24.79;
        jupiter.positionX = 1.781303138592156E+11;
        jupiter.positionY =-7.551118436250294E+11;
        jupiter.positionZ =-8.532838524470329E+08;
        jupiter.velocityX = 1.255852554663750E+04;
        jupiter.velocityY = 3.622680206123269E+03;
        jupiter.velocityZ =-2.958620404125016E+02;
        jupiter.vectors();
        planets[6] = jupiter;

        //saturn
        Planet saturn = new Planet("Saturn");
        saturn.mass = 5.6834e+26;
        saturn.radius = 8232e+03;
        saturn.gravity = 10.44;
        saturn.positionX = 6.328646641500651E+11;
        saturn.positionY = -1.358172804527507e+12;
        saturn.positionZ = -1.578520137930810e+09;
        saturn.velocityX  = 8.220842186554890e+03;
        saturn.velocityY = 4.052137378979608e+03;
        saturn.velocityZ = -3.976224719266916e+02;
        saturn.vectors();
        planets[7] = saturn;

        //titan
        Planet titan = new Planet("Titan");
        titan.mass = 1.34553e+23;
        titan.radius = 2575000.5;
        titan.gravity = 1.352;
        titan.positionX = 6.332873118527889e+11;
        titan.positionY = -1.357175556995868e+12;
        titan.positionZ = -2.134637041453660e+09;
        titan.velocityX = 3.056877965721629e+03;
        titan.velocityY = 6.125612956428791e+03;
        titan.velocityZ = -9.523587380845593e+02;
        titan.vectors();
        planets[8] = titan;

        //uranus
        Planet uranus = new Planet("Uranus");
        uranus.mass = 8.6813e+25;
        uranus.radius = 25362e+03;
        uranus.gravity = 8.87;
        uranus.positionX = 2.395195716207961E+12;
        uranus.positionY = 1.744451068474438E+12;
        uranus.positionZ = -2.455128792996490E+10;
        uranus.velocityX  = -4.059468388338293E+03;
        uranus.velocityY = 5.187467342275506E+03;
        uranus.velocityZ = 7.182537902566288E+01;
        uranus.vectors();
        planets[9] = uranus;

        //neptune
        Planet neptune = new Planet("Neptune");
        neptune.mass = 1.02413e+26;
        neptune.radius = 24.622e+03;
        neptune.gravity = 11.15;
        neptune.positionX = 4.382692942729912E+12;
        neptune.positionY = -9.093501655460005E+11;
        neptune.positionZ = -8.227728929321569E+10;
        neptune.velocityX  = 1.068410753078312E+03;
        neptune.velocityY = 5.354959504463812E+03;
        neptune.velocityZ = -1.343918419749561E+02;
        neptune.vectors();
        planets[10] = neptune;

        //spacecraft
        Planet spaceCraft = new Planet ("SpaceProbe");
        spaceCraft.mass = 15000;
        spaceCraft.radius = 10;
        spaceCraft.gravity = 1e-10;
        spaceCraft.positionX = 0;
        spaceCraft.positionY = 0;
        spaceCraft.positionZ = 0;
        spaceCraft.velocityX = 0;
        spaceCraft.velocityY = 0;
        spaceCraft.velocityZ = 0;
        spaceCraft.vectors();
        planets[11] = spaceCraft;

        //copy array to be held in simulator.Planet class
        Planet.planets = planets;
    }


}

