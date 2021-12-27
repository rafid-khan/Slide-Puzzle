package puzzles.common.solver;

import puzzles.slide.model.SlideConfig;
import java.util.*;

/**
 * This class represents a breadth first search puzzle solver
 * that can be applied to any valid configuration, to determine
 * its solution.
 * @param <E>
 *
 * @author Rafid Khan
 */

public class Solver<E>{
    /** number of total configs */
    private int total;

    /** number of unique configs */
    private int unique;

    /**
     * the method solve uses a bfs algorithm to determine the
     * path to a solution of a given configuration
     */
    public List<SlideConfig> solve(Configuration<E> config) {
        ArrayList<SlideConfig> path = new ArrayList<>();
        ArrayList<SlideConfig> queue = new ArrayList<>();
        HashMap<SlideConfig, SlideConfig> predecessor = new HashMap<>();
        SlideConfig current = new SlideConfig((SlideConfig) config);
        SlideConfig finish = (SlideConfig) current.Solution();
        predecessor.put(current, null);
        queue.add(current);
        boolean found = false;

        while (queue.size() != 0 && !found) {
            current = queue.remove(0);
            ArrayList<Configuration<SlideConfig>> neighbors = current.getNeighbors();
            for (Configuration<SlideConfig> x : neighbors) {
                total += 1;
                if (x.equals(finish)) {
                    found = true;
                    predecessor.put((SlideConfig) x, current);
                    current = (SlideConfig) x;
                    break;
                }
                if (!predecessor.containsKey(x)) {
                    predecessor.put((SlideConfig) x, current);
                    queue.add((SlideConfig) x);
                    unique += 1;
                }
            }
            }

            if (found) {
                while (predecessor.get(current) != null) {
                    path.add(current);
                    current = predecessor.get(current);
                }
                path.add(current);
                Collections.reverse(path);
            }
            return path;
    }

    /**
     * this helper method determines the number of total / unique
     * configurations in the search for a solution
     * @param config
     * @return array where array[0] is number of total configs
     * and array[1] is the number of unique
     */
    public Integer[] getNum(Configuration<E> config) {
        ArrayList<SlideConfig> path = new ArrayList<>();
        ArrayList<SlideConfig> queue = new ArrayList<>();
        HashMap<SlideConfig, SlideConfig> predecessor = new HashMap<>();
        SlideConfig current = new SlideConfig((SlideConfig) config);
        SlideConfig finish = (SlideConfig) current.Solution();
        predecessor.put(current, null);
        queue.add(current);
        boolean found = false;
        Integer[] num = new Integer[2];

        while (queue.size() != 0 && !found) {
            current = queue.remove(0);
            for (Configuration<SlideConfig> x : current.getNeighbors()) {
                total += 1;
                if (x.equals(finish)) {
                    found = true;
                    predecessor.put((SlideConfig) x, current);
                    current = (SlideConfig) x;
                    break;
                }
                if (!predecessor.containsKey(x)) {
                    predecessor.put((SlideConfig) x, current);
                    queue.add((SlideConfig) x);
                    unique += 1;
                }
            }
        }

        if (found) {
            while (predecessor.get(current) != null) {
                path.add(current);
                current = predecessor.get(current);
            }
            path.add(current);
            Collections.reverse(path);
        }
        num[0] = total;
        num[1] = unique;
        return num;
    }
}
