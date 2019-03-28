import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.*;
import javafx.geometry.*;
import javafx.fxml.*;
import java.net.*;
import java.io.*;

/**
 * 
 */
public class BoroughWindow
{
    private BorderPane borderPane;
    private ChoiceBox<String> choiceBox;
    private TilePane tilePane;

    public BoroughWindow()
    {
        borderPane = new BorderPane();
        
        // create elements of border top
        HBox sortBar = new HBox();
        sortBar.setAlignment(Pos.CENTER_RIGHT);
        Label label = new Label("Sort by: ");
        choiceBox = new ChoiceBox();
        borderPane.setAlignment(choiceBox, Pos.CENTER_RIGHT);
        
        sortBar.getChildren().addAll(label, choiceBox);
        borderPane.setTop(sortBar);
        
        // create elements of border center
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        borderPane.setAlignment(scrollPane, Pos.CENTER);
        tilePane = new TilePane();
        tilePane.setHgap(15);
        tilePane.setVgap(15);
        tilePane.setPadding(new Insets(10, 20, 10, 10));
        scrollPane.setContent(tilePane);
        borderPane.setCenter(scrollPane);
    }
    
    /**
     * 
     */
    public BorderPane getBorderPane()
    {
        
            //choiceBox.getItems().addAll("Host name", "Price", "Number of reviews");

            // choiceBox.getSelectionModel().selectedItemProperty().addListener
        
        return borderPane;
    }

    /**
     * 
     */
    public TilePane getTilePane() 
    {
        return tilePane;
    }
    
    /**
     * 
     */
    public ChoiceBox<String> getChoiceBox() 
    {
        return choiceBox;
    }
}
