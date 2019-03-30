import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.Font;
import java.util.*;

/**
 * Write a description of class Panel3 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Panel3
{
    // instance variables - replace the example below with your own
    private ArrayList<StatBox> statBoxes = new ArrayList<StatBox>();
    private static final int NUM_BOXES = 4;
    private BorderPane root = new BorderPane();
    private Scene panel3; 
    private TilePane tilePane = new TilePane();
    private AnchorPane borderAnchor = new AnchorPane();

    private Label title = new Label("Statistics Panel");

    // Forward back buttons
    private Button backButton = new Button("  <  ");
    private Button forwardButton = new Button("  >  ");

    private Panel3Data panel3Data = new Panel3Data();

    /**
     * Constructor for objects of class Panel3
     */
    public Panel3()
    {
        borderAnchor.setMinSize(800, 55);
        borderAnchor.getChildren().addAll(backButton, forwardButton);
        borderAnchor.setBottomAnchor(backButton, 15.0);
        borderAnchor.setLeftAnchor(backButton, 50.0);
        borderAnchor.setBottomAnchor(forwardButton, 15.0);
        borderAnchor.setRightAnchor(forwardButton, 50.0);

        createStatBoxes();

        title.setFont(new Font("Helvetica", 24));
        root.setAlignment(title, javafx.geometry.Pos.CENTER);
        root.setMargin(title, new Insets(0, 0, 0, 10));
        title.setStyle("-fx-font-weight: bold");

        root.setBottom(borderAnchor);
        root.setCenter(tilePane);
        root.setTop(title);

        panel3 = new Scene(root, 800, 500);
        
        panel3.getStylesheets().add("panel3.css");
    }

    private void createStatBoxes()
    {
        for (int i = 0; i < Panel3.NUM_BOXES; i++) {
            // Create Stat box
            StatBox newStatBox = new StatBox(this, i);
            statBoxes.add(newStatBox);

            // Add Stat box to tilePane
            tilePane.getChildren().add(newStatBox.getPane());
            tilePane.setAlignment(javafx.geometry.Pos.CENTER);
            tilePane.setStyle("-fx-hgap: 5; -fx-vgap: 5");
        }
    }

    public int getNextStat(int currentStat){
        int nextStat = (currentStat+1)%panel3Data.getNumStats();
        while (isAlreadySelected(nextStat))
        {
            nextStat = (nextStat+1)%panel3Data.getNumStats();
        }
        System.out.println(nextStat);
        return nextStat;
    }

    public int getPrevStat(int currentStat){
        int prevStat = (currentStat-1)%panel3Data.getNumStats();
        if (prevStat<0) {
            prevStat = panel3Data.getNumStats()-1; 
        }
        while (isAlreadySelected(prevStat))
        {
            prevStat = (prevStat-1)%panel3Data.getNumStats();
            if (prevStat<0) { 
                prevStat = panel3Data.getNumStats()-1; 
            }
        }
        System.out.println(prevStat);
        return prevStat;
    }

    private boolean isAlreadySelected(int stat){
        boolean isAlreadySelected = false;
        for (int i = 0; i < statBoxes.size(); i++){
            if (statBoxes.get(i).getCurrentStat() == stat){
                isAlreadySelected = true;
                break;
            }
        }
        return isAlreadySelected;
    }

    public Scene getScene()
    {
        return panel3;
    }

    public Button getBackButton()
    {
        return backButton;
    }

    public Button getForwardButton()
    {
        return forwardButton;
    }

    public String getStatDescription(int stat)
    {
        return panel3Data.getStatDescription(stat);
    }

    public String getStatValue(int stat)
    {
        return panel3Data.getStatValue(stat);
    }
}
