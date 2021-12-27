package puzzles.slide.model;

import puzzles.common.Observer;
import puzzles.common.solver.Solver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A model for the slide puzzle game
 *
 * @author Rafid Khan
 */
public class SlideModel {
    /** the collection of observers of this model */
    private final List<Observer<SlideModel, SlideClientData>> observers = new LinkedList<>();

    /** filename of config */
    private String filename;

    /** the current configuration */
    private SlideConfig currentConfig;

    /** user selected row */
    int selectedRow ;

    /** user selected column */
    int selectedCol;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<SlideModel, SlideClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(SlideClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    public SlideModel(String filename) throws IOException {
        this.filename = filename;
        this.currentConfig = new SlideConfig(filename);
        selectedCol = -1;
        selectedRow = -1;
    }

    /**
     * reset()
     * changes the config to the initial config
     */
    public void reset() {
        try{
            initConfig();
            alertObservers(new SlideClientData("Puzzle reset!"));
        }
        catch (FileNotFoundException e) {
            alertObservers(new SlideClientData("File not found"));
        }
    }

    /**
     * load()
     * loads a given file into the game
     *
     * @param filename directory of the file
     */
    public void load(String filename) {
        try{
            this.filename = filename;
            this.currentConfig = new SlideConfig(filename);
            alertObservers(new SlideClientData("Loaded: " + filename));
        }
        catch (FileNotFoundException e){
            alertObservers(new SlideClientData("File not found"));
        }
    }

    /**
     * select()
     * moves elements in the board according to the rules
     *
     * @param x desired x coord
     * @param y desired y coord
     */
    public void select(String x, String y) {
        if (!(Integer.parseInt(x) >= 0 && Integer.parseInt(x) < this.currentConfig.getRow() &&
            Integer.parseInt(y) >= 0 && Integer.parseInt(y) < this.currentConfig.getCol())){
            alertObservers(new SlideClientData("Invalid selection!"));
            return;
        }
        if (selectedRow == -1){
            selectedCol = Integer.parseInt(y);
            selectedRow = Integer.parseInt(x);
            alertObservers(new SlideClientData("Selected " + "(" + selectedRow + "," + selectedCol
            + ")"));
        }
        else{
            if(Math.abs(selectedCol - Integer.parseInt(y)) +
                    Math.abs(selectedRow - Integer.parseInt(x)) == 1) {
                String selectedCell = currentConfig.getBoard()[selectedRow][selectedCol];
                String oldCell = currentConfig.getBoard()[Integer.parseInt(x)][Integer.parseInt(y)];
                if (selectedCell.equals(".") || oldCell.equals(".")){
                    currentConfig.getBoard()[selectedRow][selectedCol] = oldCell;
                    currentConfig.getBoard()[Integer.parseInt(x)][Integer.parseInt(y)] = selectedCell;
                    alertObservers(new SlideClientData("Moved from (" + x + "," + y + ") to (" +
                            selectedRow + "," + selectedCol + ")"));
                    selectedRow = -1;
                    selectedCol = -1;
                }
                else{
                    alertObservers(new SlideClientData("Can't move from (" + x + "," + y + ") to (" +
                            selectedRow + "," + selectedCol + ")"));
                }
            }
            else{
                alertObservers(new SlideClientData("Can't move from (" + x + "," + y + ") to (" +
                        selectedRow + "," + selectedCol + ")"));
            }
        }
    }

    /**
     * hint()
     * changes configs board according to the next move given by the bfs solver
     */
    public void hint() {
        if (!(this.currentConfig.isSol())) {
            Solver<SlideConfig> bfs = new Solver<>();
            ArrayList<SlideConfig> solutions = (ArrayList<SlideConfig>) bfs.solve(this.currentConfig);
            this.currentConfig = solutions.get(1);
            alertObservers(new SlideClientData("Next Step!"));
        }
        else{
            alertObservers(new SlideClientData("Already solved!"));
        }
    }

    /**
     * toString()
     * @return a string representation of the model
     */
    public String toString(){
        String result = "   ";
        for (int i= 0; i < this.currentConfig.getCol(); i++){
            result += (i) + (" ");
        }
        result += "\n ";

        for (int j = 0; j < this.currentConfig.getCol(); j++){
            result+= "---";
        }

        for (int k = 0; k < this.currentConfig.getRow(); k++) {
            result += "\n";
            result += k + "|" + " ";
            for (int a = 0; a < this.currentConfig.getCol(); a++) {
                String num = this.currentConfig.getCoord(k, a);
                result += num + " ";
            }
        }
        return result;
    }

    /*
                              ****** HELPER METHODS ******
     */

    /**
     * gets the filename of the config
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * gets the initial configuration
     */
    public void initConfig() throws FileNotFoundException {
        this.currentConfig = new SlideConfig(this.filename);
    }

    /**
     * gets the current config of the model
     * @return slideConfig
     */
    public SlideConfig getCurrentConfig(){
        return this.currentConfig;
    }
}
