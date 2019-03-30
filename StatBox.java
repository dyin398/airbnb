import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * Write a description of class StatBox here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatBox
{
    // instance variables - replace the example below with your own
    private Pane myPane = new Pane();
    private static final int PREF_SIZE[] = { 350, 200 };
    private Panel3 panel3;

    // Button variables
    private Button prevStatButton = new Button("<");
    private Button nextStatButton = new Button(">");

    // Info VBox
    private GridPane infoBox = new GridPane();
    private Label selectedStat = new Label("Selected Stat Label Placeholder");
    private Label statValue = new Label("Statistic Placeholder");

    private int currentStat;

    /**
     * Constructor for objects of class StatBox
     */
    public StatBox(Panel3 panel3, int currentStat)
    {
        // initialise instance variables
        this.panel3 = panel3;
        this.currentStat = currentStat;

        myPane.setPrefSize(StatBox.PREF_SIZE[0], StatBox.PREF_SIZE[1]);
        myPane.setStyle("-fx-background-color: whitesmoke; -fx-background-radius: 25;" + 
            "-fx-border-color: black; -fx-border-radius:25");
        addObjects();
        addButtonActionListeners();
        myPane.getChildren().addAll(prevStatButton, nextStatButton, infoBox);
        updateInfoDisplay();
    }

    private void addObjects()
    {
        // Add prev stat button
        prevStatButton.relocate( 25, 25);
        prevStatButton.setPrefSize( 50 , 150 );
        prevStatButton.setStyle("-fx-background-radius: 25");

        // Add next stat button
        nextStatButton.relocate( 275 , 25);
        nextStatButton.setPrefSize( 50 , 150 );
        nextStatButton.setStyle("-fx-background-radius: 25");
        
        // Add central info
        infoBox.relocate( 100, 0 );
        infoBox.setPrefSize(150, 200 );
        
        selectedStat.setWrapText(true);
        selectedStat.setPrefWidth(150);
        selectedStat.setMaxWidth(150);
        selectedStat.setMaxHeight(100);
        selectedStat.setTextAlignment(TextAlignment.CENTER);
        selectedStat.setAlignment(javafx.geometry.Pos.CENTER);
        selectedStat.setStyle("-fx-font-weight: bolder; -fx-font-size: 16");

        statValue.setWrapText(true);
        statValue.setPrefWidth(150);
        statValue.setMaxWidth(150);
        statValue.setMaxHeight(100);
        statValue.setTextAlignment(TextAlignment.CENTER);
        statValue.setAlignment(javafx.geometry.Pos.CENTER);
        statValue.setStyle("-fx-font-weight: bold; -fx-font-size: 16");

        infoBox.add(selectedStat, 0, 0);
        infoBox.add(statValue, 0, 1);

        infoBox.setStyle("-fx-alignment: center; -fx-vgap: 20;");
    }

    private void addButtonActionListeners()
    {
        nextStatButton.setOnAction(value -> { nextStat(); });
        prevStatButton.setOnAction(value -> { prevStat(); });
    }

    private void updateInfoDisplay(){
        selectedStat.setText(panel3.getStatDescription(currentStat));
        statValue.setText(panel3.getStatValue(currentStat));
    }

    private void nextStat(){
        currentStat = panel3.getNextStat(currentStat);
        updateInfoDisplay();
    }

    private void prevStat(){
        currentStat = panel3.getPrevStat(currentStat);
        updateInfoDisplay();
    }

    public int getCurrentStat(){
        return currentStat;
    }

    public Pane getPane(){
        return myPane;
    }
}
