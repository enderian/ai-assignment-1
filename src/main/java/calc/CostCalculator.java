package calc;

import state.Schedule;

import java.util.*;

import static state.Schedule.*;

public class CostCalculator {

    public static final int DEPTH_CONST = 1;
    public static final int CONST = 1;

    public static Integer calculate(Schedule next) {
        return DEPTH_CONST * next.getDepth() + CONST;
    }
}
