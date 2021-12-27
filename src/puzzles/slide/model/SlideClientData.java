package puzzles.slide.model;

/**
 * Client data for the slide puzzle game
 *
 * @author Rafid Khan
 */
public class SlideClientData {
    String data = "";

    public SlideClientData(String s) {
        this.data = s;
    }

    public String toString(){
        return data;
    }
}
