package puzzles.slide.solver;

import puzzles.common.solver.Solver;
import puzzles.slide.model.SlideConfig;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * main class for the slide puzzle configuration (text)
 */
public class Slide {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java Slide filename");
        } else {
            String filename = args[0];
            SlideConfig initConfig = new SlideConfig(filename);
            Solver<SlideConfig> bfs = new Solver<>();
            List<SlideConfig> solution = bfs.solve(initConfig);
            Integer[] nums = bfs.getNum(initConfig);
            System.out.println(filename + initConfig);
            System.out.println("Total configs: " + nums[0]);
            System.out.println("Unique configs: " + nums[1]);
            String result = "";
            for(int i = 0; i < solution.size(); i++){
                result += ("Step " + i + ": " +
                        solution.get(i) + "\n" + "\n");
            }
            System.out.println(result);
        }
    }
}
