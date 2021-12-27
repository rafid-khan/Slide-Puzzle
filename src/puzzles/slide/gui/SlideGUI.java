package puzzles.slide.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.slide.model.SlideClientData;
import puzzles.slide.model.SlideModel;

import java.io.File;
import java.io.IOException;

/**
 * A JavaFX GUI for the Slide puzzle game
 *
 * @author Rafid Khan
 */
public class SlideGUI extends Application implements Observer<SlideModel, SlideClientData> {
    private SlideModel model;

    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int BUTTON_FONT_SIZE = 20;
    private final static int FONT_SIZE = 12;
    private final static int NUMBER_FONT_SIZE = 24;
    /** Colored buttons */
    private final static String EVEN_COLOR = "#ADD8E6";
    private final static String ODD_COLOR = "#FED8B1";
    private final static String EMPTY_COLOR = "#FFFFFF";

    /** stage object */
    private Stage stage;

    /** BorderPane object */
    private BorderPane borderPane;

    /** Label object */
    private Label topLabel;

    /**
     * initializer
     * @throws IOException
     */
    @Override
    public void init() throws IOException {
        String filename = getParameters().getRaw().get(0);
        this.model = new SlideModel(filename);
        this.model.addObserver(this);
        borderPane = new BorderPane();
        topLabel = new Label();
    }

    /**
     * makeHBox()
     *
     * @return the desired HBox()
     */
    private HBox makeHbox(){
        HBox hbox = new HBox();
        Button load = new Button("Load");
        load.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(stage);
            this.model.load(selectedFile.getAbsolutePath());
        });
        Button hint = new Button("Hint");
        hint.setOnAction(event -> {
            this.model.hint();
        });
        Button reset = new Button("Reset");
        reset.setOnAction(event -> {
            this.model.reset();
        });
        hbox.getChildren().add(load);
        hbox.getChildren().add(hint);
        hbox.getChildren().add(reset);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    /**
     * makeGridPane()
     *
     * @return the desired GridPane()
     */
    private GridPane makeGridPane(){
        GridPane gridPane = new GridPane();
        int gridrow = this.model.getCurrentConfig().getRow();
        int gridcol = this.model.getCurrentConfig().getCol();
        String[][] gridboard = this.model.getCurrentConfig().getBoard();
        for (int i = 0; i < gridrow; i++){
            for (int j = 0; j < gridcol; j++){
                Button button = new Button();
                int finalI = i;
                int finalJ = j;
                button.setOnAction(event -> {
                    this.model.select(Integer.toString(finalI), Integer.toString(finalJ));
                });
                if (gridboard[i][j].equals(".")){
                    button.setStyle(
                            "-fx-font-family: Arial;" +
                                    "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + EMPTY_COLOR + ";" +
                                    "-fx-font-weight: bold;");
                }
                else if ((Integer.parseInt(gridboard[i][j]) % 2 == 0)){
                    button.setStyle(
                            "-fx-font-family: Arial;" +
                                    "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + EVEN_COLOR + ";" +
                                    "-fx-font-weight: bold;");
                    button.setText(String.valueOf(gridboard[i][j]));
                }
                else if ((Integer.parseInt(gridboard[i][j]) % 2 != 0)){
                    button.setStyle(
                            "-fx-font-family: Arial;" +
                                    "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + ODD_COLOR + ";" +
                                    "-fx-font-weight: bold;");
                    button.setText(String.valueOf(gridboard[i][j]));
                }
                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                gridPane.add(button, j, i);
            }
        }
        return gridPane;
    }

    /**
     * start()
     * Constructs the layout for the game
     *
     * @param stage container which renders the GUI
     * @throws Exception if there is a problem
     */
    @Override
    public void start(Stage stage) throws Exception {
        init();
        borderPane = new BorderPane();
        HBox bottomHBox = makeHbox();

        topLabel = new Label("Loaded: " + model.getFilename());
        HBox topHBox = new HBox();
        topHBox.getChildren().add(topLabel);
        topHBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topHBox);
        borderPane.setCenter(makeGridPane());
        borderPane.setBottom(bottomHBox);

        this.stage = stage;
        stage.setTitle("Slide GUI");
        stage.setResizable(false);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * update()
     * called by model whenever there is a state change that needs
     * to be updated by the GUI
     *
     * @param model the config
     * @param data optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(SlideModel model, SlideClientData data) {
        borderPane.setCenter(makeGridPane());
        if (data != null) {
            topLabel.setText(data.toString());
        }
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
