package simulator;

/**
 * this class initializes the constellation of objects of the solar system on 01-04-2019
 * as found on https://ssd.jpl.nasa.gov/horizons.cgi
 *
 * @author Leo
 */

public class PlanetStart2019 {

    public Planet[] planets;
    public int c = 1000;

    public PlanetStart2019(){

        //array of all objects
        planets = new Planet[12];

        //sun
        Planet sun = new Planet("Sun");
        sun.mass = 1.988500e+30;
        sun.radius = 6957e+05;
        sun.gravity = 274;
        sun.positionX = -2.220223643148978E+05 * c;
        sun.positionY = 1.132165621518817E+06 * c;
        sun.positionZ = -5.755450853535614E+03 * c;
        sun.velocityX = -1.434226552236659E-02 * c;
        sun.velocityY = 1.841961419006339E-03 * c;
        sun.velocityZ = 3.733812783597716E-04 * c;
        sun.vectors();
        planets[0] = sun;

        //mercury
        Planet mercury = new Planet("Mercury");
        mercury.mass = 3.302e+23;
        mercury.radius = 2440e+03;
        mercury.gravity = 3.7;
        mercury.positionX = -4.271851323985465E+07 * c;
        mercury.positionY = -5.197449079646812E+07 * c;
        mercury.positionZ = -4.467681870179586E+05 * c;
        mercury.velocityX = 2.814799891042631E+01 * c;
        mercury.velocityY = -2.816804264941771E+01 * c;
        mercury.velocityZ = -4.885030894801613E+00 * c;
        mercury.vectors();
        planets[1] = mercury;

        //venus
        Planet venus = new Planet("Venus");
        venus.mass = 4.8685e+24;
        venus.radius = 6051e+03;
        venus.gravity = 8.87;
        venus.positionX = 2.696003224762395E+07 * c;
        venus.positionY = -1.042328192071913E+08 * c;
        venus.positionZ = -3.020104668324806E+06 * c;
        venus.velocityX = 3.366147729438745E+01 * c;
        venus.velocityY = 8.630903048450678E+00 * c;
        venus.velocityZ = -1.824575444253862E+00 * c;
        venus.vectors();
        planets[2] = venus;

        //earth
        Planet earth = new Planet("Earth");
        earth.mass = 5.97219e+24;
        earth.radius = 6371e+3;
        earth.gravity = 9.807;
        earth.positionX = -1.470744775510983E+08 * c;
        earth.positionY = -2.666419726116017E+07 * c;
        earth.positionZ = -3.865926889829338E+03 * c;
        earth.velocityX = 5.034091569706142E+00 * c;
        earth.velocityY = -2.938848777588662E+01 * c;
        earth.velocityZ = 2.594348980244021E-03 * c;
        earth.vectors();
        planets[3] = earth;

        //moon
        Planet moon = new Planet ("Moon");
        moon.mass = 7.349e+22;
        moon.radius = 1737e+03;
        moon.gravity = 1.62;
        moon.positionX = -1.467532382331994E+08 * c;
        moon.positionY = -2.691117349081933E+07 * c;
        moon.positionZ = -2.121510101069510E+04 * c;
        moon.velocityX = 5.619774806445962E+00 * c;
        moon.velocityY = -2.862167691469771E+01 * c;
        moon.velocityZ = -7.176561688326544E-02 * c;
        moon.vectors();
        planets[4] = moon;

        //mars
        Planet mars = new Planet ("Mars");
        mars.mass = 6.4171e+23;
        mars.radius = 3389e+03;
        mars.gravity = 3.711;
        mars.positionX = 2.917744772199295E+06 * c;
        mars.positionY = 2.355946016656816E+08 * c;
        mars.positionZ = 4.829998463231131E+06 * c;
        mars.velocityX = -2.332615781790776E+01 * c;
        mars.velocityY = 2.384809232457906E+00 * c;
        mars.velocityZ = 6.223031603924362E-01 * c;
        mars.vectors();
        planets[5] = mars;

        //jupiter
        Planet jupiter = new Planet("Jupiter");
        jupiter.mass= 1.89813e+27;
        jupiter.radius = 69911e+03;
        jupiter.gravity = 24.79;
        jupiter.positionX = -2.252143078201484E+08 * c;
        jupiter.positionY = -7.626047300387172E+08 * c;
        jupiter.positionZ = 8.200593879594862E+06 * c;
        jupiter.velocityX = 1.237453723824857E+01 * c;
        jupiter.velocityY = -3.077057009749617E+00 * c;
        jupiter.velocityZ = -2.639915777468331E-01 * c;
        jupiter.vectors();
        planets[6] = jupiter;

        //saturn
        Planet saturn = new Planet("Saturn");
        saturn.mass = 5.6834e+26;
        saturn.radius = 8232e+03;
        saturn.gravity = 10.44;
        saturn.positionX = 3.621944113756218E+08 * c;
        saturn.positionY = -1.458855029903598E+09 * c;
        saturn.positionZ = 1.094821745069247E+07 * c;
        saturn.velocityX = 8.843429739946849E+00 * c;
        saturn.velocityY = 2.297292211260905E+00 * c;
        saturn.velocityZ = -3.920059743470743E-01 * c;
        saturn.vectors();
        planets[7] = saturn;

        //titan
        Planet titan = new Planet("Titan");
        titan.mass = 1.34553e+23;
        titan.radius = 2575000.5;
        titan.gravity = 1.352;
        titan.positionX = 3.622714432386897E+08 * c;
        titan.positionY = -1.457768706158453E+09 * c;
        titan.positionZ = 1.038065152195418E+07 * c;
        titan.velocityX = 3.343628795144511E+00 * c;
        titan.velocityY = 2.968901401206745E+00 * c;
        titan.velocityZ = -1.911888971563741E-01 * c;
        titan.vectors();
        planets[8] = titan;

        //uranus
        Planet uranus = new Planet("Uranus");
        uranus.mass = 8.6813e+25;
        uranus.radius = 25362e+03;
        uranus.gravity = 8.87;
        uranus.positionX = 2.517351227698894E+09 * c;
        uranus.positionY = 1.576111719287513E+09 * c;
        uranus.positionZ = -2.675904988243723E+07 * c;
        uranus.velocityX = -3.663959036422636E+00 * c;
        uranus.velocityY = 5.454607982106285E+00 * c;
        uranus.velocityZ = 6.759431605207888E-02 * c;
        uranus.vectors();
        planets[9] = uranus;

        //neptune
        Planet neptune = new Planet("Neptune");
        neptune.mass = 1.02413e+26;
        neptune.radius = 24.622e+03;
        neptune.gravity = 11.15;
        neptune.positionX = 4.345679882700908E+09 * c;
        neptune.positionY = -1.077948775704903E+09 * c;
        neptune.positionZ = -7.795243389091682E+07 * c;
        neptune.velocityX = 1.271677195531269E+00 * c;
        neptune.velocityY = 5.307487851509888E+00 * c;
        neptune.velocityZ = -1.382045786938526E-01 * c;
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
