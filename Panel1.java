import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;  
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.geometry.Insets;
import javafx.fxml.*;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.*;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.AbstractChronology;
import java.time.chrono.IsoChronology;

import java.lang.Object;
import java.net.URL;

/**
 * This is the GUI of the first panel - the welcome page of the application.
 *
 * @author (AirYeet)
 * @version (a version number or a date)
 */

public class Panel1
{
    private ChoiceBox<String> price_from = new ChoiceBox();
    private ChoiceBox<String> price_to = new ChoiceBox();
    private Label price_from_label = new Label("Price from:");
    private Label price_to_label = new Label("Price to:");
    private Label priceError = new Label("Invalid date or price range!");
    private Label date_from_label = new Label("Date From:");
    private Label date_to_label = new Label("Date To:");
    private Button backButton = new Button("  <  ");
    private Button forwardButton = new Button("  >  ");
    private DatePicker datePickerin = new DatePicker();
    private DatePicker datePickerout = new DatePicker();
    private TextField line1, line2, line3;
    private Scene panel1;
    private AnchorPane root = new AnchorPane();
    
    public Panel1()
    {
        price_from.getItems().addAll("0", "25", "50", "75", "100", "125", "150", "175", "200", "225","250");
        // set a default value 
        price_from.setValue("0");
        
        price_to.getItems().addAll("0", "25", "50", "75", "100", "125", "150", "175", "200", "225","250");
        // set a default value 
        price_to.setValue("0");
        
        backButton.setDisable(true);
        forwardButton.setDisable(true);
        
        line1 = new TextField("This is an application for London Property MarketPlace.");
        line2 = new TextField("Select the Price Range you desire for the property.");
        line3 = new TextField("Each property shown can be rented and each borough can have one or many properties.");
        line1.setEditable(false);
        line1.setPrefWidth(340);
        line2.setEditable(false);
        line2.setPrefWidth(315);
        line3.setEditable(false);
        line3.setPrefWidth(535);
        
        price_from_label.setFont(new Font("Helvetica", 14));
        price_to_label.setFont(new Font("Helvetica", 14));
        priceError.setFont(new Font("Helvetica", 14));
        date_from_label.setFont(new Font("Helvetica", 14));
        date_to_label.setFont(new Font("Helvetica", 14));
        line1.setFont(new Font("Helvetica", 13));
        line2.setFont(new Font("Helvetica", 13));
        line3.setFont(new Font("Helvetica", 13));
        
        root.getChildren().addAll(price_from, price_to, price_from_label, 
                                    price_to_label, backButton, forwardButton, 
                                    priceError, line1, line2, line3, datePickerin,
                                    datePickerout, date_from_label, date_to_label);
        root.setLeftAnchor(price_from, 565.0);
        root.setTopAnchor(price_from, 10.0);
        root.setRightAnchor(price_to, 20.0);
        root.setTopAnchor(price_to, 10.0);
        root.setLeftAnchor(price_from_label, 480.0);
        root.setTopAnchor(price_from_label, 15.0);
        root.setRightAnchor(price_to_label, 100.0);
        root.setTopAnchor(price_to_label, 15.0);
        root.setBottomAnchor(backButton, 15.0);
        root.setLeftAnchor(backButton, 50.0);
        root.setBottomAnchor(forwardButton, 15.0);
        root.setRightAnchor(forwardButton, 50.0);
        root.setLeftAnchor(line1, 100.0);
        root.setTopAnchor(line1, 180.0);
        root.setLeftAnchor(line2, 100.0);
        root.setTopAnchor(line2, 230.0);
        root.setLeftAnchor(line3, 100.0);
        root.setTopAnchor(line3, 280.0);
        root.setLeftAnchor(priceError, 480.0);
        root.setTopAnchor(priceError, 37.0);
        root.setTopAnchor(datePickerin, 20.0);
        root.setLeftAnchor(datePickerin, 10.0);
        root.setTopAnchor(datePickerout, 20.0);
        root.setLeftAnchor(datePickerout, 220.0);
        root.setTopAnchor(date_from_label, 2.0);
        root.setLeftAnchor(date_from_label, 10.0);
        root.setTopAnchor(date_to_label, 2.0);
        root.setLeftAnchor(date_to_label, 220.0);
        
        panel1 = new Scene(root, 800, 500);
        
        panel1.getStylesheets().add("background.css");
    }
    
    /**
     * To get the value of the selected method 
     */
    public int getFromPrice()
    {
        int price = Integer.parseInt(price_from.getValue());
        return price;
    }
    
    public int getToPrice()
    {
        int price = Integer.parseInt(price_to.getValue());
        return price;
    }
    
    public ChoiceBox<String> getPriceFromButton()
    {
        return price_from;
    }
    
    public ChoiceBox<String> getPriceToButton()
    {
        return price_to;
    }
    
    public Button getBackButton()
    {
        return backButton;
    }
    
    public Button getForwardButton()
    {
        return forwardButton;
    }
    
    public Label getErrorMessage()
    {
        return priceError;
    }
    
    public Scene getScene()
    {
        return panel1;
    }
    
    public DatePicker getPickerIn()
    {
        return datePickerin;
    }
    
    public DatePicker getPickerOut()
    {
        return datePickerout;
    }

    public void setPriceFrom(String newPrice)
    {
        price_from.setValue(newPrice);
    }
    
    public void setPriceTo(String newPrice)
    {
        price_to.setValue(newPrice);
    }
    
    public void setDateIn(LocalDate newDate)
    {
        datePickerin.setValue(newDate);
    }
    
    public void setDateOut(LocalDate newDate)
    {
        datePickerout.setValue(newDate);
    }
}

