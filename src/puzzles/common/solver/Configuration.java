package puzzles.common.solver;
import java.util.*;

/**
 * The representation of a single configuration for a puzzle
 * The solver depends on these routines in order to solve a puzzle.
 * All puzzles must have configurations that implement this interface
 * @param <E>
 * @author Rafid Khan
 */
public interface Configuration<E>{
    /**
     * checks if the config is a goal or not
     * @return true if config is goal, otherwise false
     */
    boolean isGoal();
}
