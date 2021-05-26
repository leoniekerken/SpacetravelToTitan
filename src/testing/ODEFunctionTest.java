package testing;

import org.junit.jupiter.api.Test;
import simulator.*;
import titan.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ODEFunctionTest {
    static final double ACCURACY = 1; // titan
    static final double EXPECTED = 6.332873363075275E+11;
    static PlanetStart2020 planet2020 = new PlanetStart2020();
    static final int T = 0; // time point
    static final int H = 8; // Step size
    static final ODEFunction odeF = new ODEFunction();
    
    @Test
    void testRungeKuttaX()
    {
        RungeKuttaSolver rungeKutta = new RungeKuttaSolver();
        State y0 = new State();
        y0.initializeState();
        State rungeKuttaStep = (State) (rungeKutta.step(odeF, T, y0, H));
        assertEquals(EXPECTED, rungeKuttaStep.state[8][0].getX(), ACCURACY);
    }

    @Test 
    void testVerletX()
    {
        VerletSolver verlet = new VerletSolver();
        State y0 = new State();
        y0.initializeState();

        State verletStep = (State) (verlet.step(odeF, T, y0, H));
        assertEquals(EXPECTED, verletStep.state[8][0].getX(), ACCURACY);
    }
    //Test
    @Test 
    void testEulerX()
    {
        EulerSolver eulersolver = new EulerSolver();
        State y0 = new State();
        y0.initializeState();

        State eulersolverstep = (State) (eulersolver.step(odeF, T, y0, H));
        assertEquals(EXPECTED, eulersolverstep.state[8][0].getX(), ACCURACY);
    }

    
}
