package testing;

import org.junit.jupiter.api.Test;
import simulator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ODEFunctionTest {
    static final double ACCURACY = 1; // titan
    static final double EXPECTEDX = 6.332873363075275E+11;
    static final double EXPECTEDY = -1.357175507991633E+12;
    static final double EXPECTEDZ = -2.134644659949720E+09;
    static PlanetStart2020 planet2020 = new PlanetStart2020();
    static final int T = 0; // time point
    static final int H = 8; // Step size
    static final ODEFunction odeF = new ODEFunction();

    // covers x coordinates for the runge-kutta solver
    @Test
    void testRungeKuttaX() {
        RungeKuttaSolver rungeKutta = new RungeKuttaSolver();
        State y0 = new State();
        y0.initializeState();
        State rungeKuttaStep = (State) (rungeKutta.step(odeF, T, y0, H));
        assertEquals(EXPECTEDX, rungeKuttaStep.state[8][0].getX(), ACCURACY);
    }

    // covers z coordinates for the runge-kutta solver
    @Test
    void testRungeKuttaZ() {
        RungeKuttaSolver rungeKutta = new RungeKuttaSolver();
        State y0 = new State();
        y0.initializeState();
        State rungeKuttaStep = (State) (rungeKutta.step(odeF, T, y0, H));
        assertEquals(EXPECTEDZ, rungeKuttaStep.state[8][0].getZ(), ACCURACY);
    }

    // covers y coordinates for the runge-kutta solver
    @Test
    void testRungeKuttaY() {
        RungeKuttaSolver rungeKutta = new RungeKuttaSolver();
        State y0 = new State();
        y0.initializeState();
        State rungeKuttaStep = (State) (rungeKutta.step(odeF, T, y0, H));
        assertEquals(EXPECTEDY, rungeKuttaStep.state[8][0].getY(), ACCURACY);
    }

    // covers x coordinates for the verlet solver
    @Test
    void testVerletX() {
        VerletSolver verlet = new VerletSolver();
        State y0 = new State();
        y0.initializeState();
        State verletStep = (State) (verlet.step(odeF, T, y0, H));
        assertEquals(EXPECTEDX, verletStep.state[8][0].getX(), ACCURACY);
    }

    // covers z coordinates for the verlet solver
    @Test
    void testVerletZ() {
        VerletSolver verlet = new VerletSolver();
        State y0 = new State();
        y0.initializeState();
        State verletStep = (State) (verlet.step(odeF, T, y0, H));
        assertEquals(EXPECTEDZ, verletStep.state[8][0].getZ(), ACCURACY);
    }

    // covers y coordinates for the verlet solver
    @Test
    void testVerletY() {
        VerletSolver verlet = new VerletSolver();
        State y0 = new State();
        y0.initializeState();
        State verletStep = (State) (verlet.step(odeF, T, y0, H));
        assertEquals(EXPECTEDY, verletStep.state[8][0].getY(), ACCURACY);
    }

    // covers x coordinates for the euler solver
    @Test
    void testEulerX() {
        EulerSolver eulersolver = new EulerSolver();
        State y0 = new State();
        y0.initializeState();
        State eulersolverstep = (State) (eulersolver.step(odeF, T, y0, H));
        assertEquals(EXPECTEDX, eulersolverstep.state[8][0].getX(), ACCURACY);
    }

    // covers z coordinates for the euler solver
    @Test
    void testEulerZ() {
        EulerSolver eulersolver = new EulerSolver();
        State y0 = new State();
        y0.initializeState();
        State eulersolverstep = (State) (eulersolver.step(odeF, T, y0, H));
        assertEquals(EXPECTEDZ, eulersolverstep.state[8][0].getZ(), ACCURACY);
    }

    // covers y coordinates for the euler solver
    @Test
    void testEulerY() {
        EulerSolver eulersolver = new EulerSolver();
        State y0 = new State();
        y0.initializeState();
        State eulersolverstep = (State) (eulersolver.step(odeF, T, y0, H));
        assertEquals(EXPECTEDY, eulersolverstep.state[8][0].getY(), ACCURACY);
    }
}