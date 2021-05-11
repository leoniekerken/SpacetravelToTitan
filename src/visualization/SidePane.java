package visualization;

import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;

/**
 * Side pane where data is shown
 */
public class SidePane
{

    Group sidePaneGroup;
    Pane background;
    int height;
    int width;

    /**
     * Constructor
     */
    public SidePane()
    {
        sidePaneGroup = new Group();

        width = 300;
        height = 10000;

        background = new Pane();
        background.setStyle("-fx-background-color: gray;");
        background.setPrefSize(width, height);
        sidePaneGroup.getChildren().add(background);
    }

    /**
     * Add a new label to the pane
     * @param s name of the label
     * @param x x-coordinate of the label
     * @param y y-coordinate of the label
     * @param c colour of the text of the label
     */
    public void addLabel(String s, double x, double y, Color c)
    {
        Label label = new Label(s);

        //Set font of the text
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
        label.setFont(font);
        //Add text to label
        label.setTextFill(c);
        //Setting the position
        label.setTranslateX(x);
        label.setTranslateY(y);

        sidePaneGroup.getChildren().add(label);
    }

    /**
     * Add labels and buttons to the side pane
     */
    //Make the data change while the program is running
    public Group getPane()
    {
        addLabel("Start Date: ", 40, 100, Color.BLUE);
        addLabel("01-04-2020", 40, 125, Color.BLACK);
        addLabel("End Date: ", 40, 200, Color.BLUE);
        addLabel("01-04-2021", 40, 225, Color.BLACK);
        addLabel("Mass of the Probe: ", 40, 300, Color.BLUE);
        addLabel("15000 kg", 40, 325, Color.BLACK);
        addLabel("Coordinates of Titan: ", 40, 400, Color.BLUE);
        addLabel("X: ", 35, 425, Color.BLUE);
        addLabel("6.333e+11 km", 60, 425, Color.BLACK);
        addLabel("Y: ", 35, 450, Color.BLUE);
        addLabel("-1.352e+12 km", 60, 450, Color.BLACK);
        addLabel("Z: ", 35, 475, Color.BLUE);
        addLabel("-2.135e+09 km", 60, 475, Color.BLACK);
        addLabel("□ 1 unit = 1 AU", 70, 850, Color.WHITE);
        return sidePaneGroup;
    }
}