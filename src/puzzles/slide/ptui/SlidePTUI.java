package puzzles.slide.ptui;

import puzzles.common.Observer;
import puzzles.slide.model.SlideModel;
import puzzles.slide.model.SlideClientData;

import java.io.IOException;
import java.util.Scanner;

/**
 * Plain Text User Interface for the slide puzzle game
 *
 * @author Rafid Khan
 */
public class SlidePTUI implements Observer<SlideModel, SlideClientData> {
    private SlideModel model;

    public void init(String filename) throws IOException {
        this.model = new SlideModel(filename);
        this.model.addObserver(this);
        displayHelp();
    }

    @Override
    public void update(SlideModel model, SlideClientData data) {
        if (data != null){
            System.out.println(data);
        }
    }

    /**
     * displays the help menu
     */
    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    /**
     * runs the PTUI
     */
    public void run() {
        String loaded = model.getFilename();
        System.out.println("Loaded: " + loaded);
        Scanner in = new Scanner(System.in);
        label:
        while (true) {
            System.out.println(model.toString());;
            System.out.print("> ");
            String line = in.nextLine();
            String[] words = line.split("\\s+");

            switch (words[0]) {
                case "q":
                    break label;
                case "s":
                    model.select(words[1], words[2]);
                    break;
                case "r":
                    this.model.reset();
                    break;
                case "l":
                    this.model.load(words[1]);
                    break;
                case "h":
                    this.model.hint();
                    break;
            }
            }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java JamPTUI filename");
        } else {
            try {
                SlidePTUI ptui = new SlidePTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}

