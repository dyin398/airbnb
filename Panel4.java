import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.Parent;
import javafx.geometry.Insets;
import javafx.fxml.*;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.chart.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.AbstractChronology;
import java.time.chrono.IsoChronology;
import java.time.Period;

import java.lang.Object;
import java.net.URL;
import java.io.*;
import java.util.*;

/**
 * Write a description of class Panel4 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Panel4
{
    private GridPane grid = new GridPane();
    private ScrollPane gridScroll = new ScrollPane();
    private BorderPane root = new BorderPane();
    private AnchorPane borderAnchor = new AnchorPane();
    private Button backButton = new Button("  <  ");
    private Button forwardButton = new Button("  >  ");
    private List<AirbnbListing> propertyIDs = new ArrayList<>();
    private Scene panel4;
    private Label propertyName;
    private ImageView roomType;
    private BarChart price;
    private Label minNights;
    private Label numberOfReviews;
    private Label lastReview;
    private PieChart reviewsPerMonth;
    private Label hostListingCount;
    private PieChart annualAvailability;
    private Label nameLabel, roomTypeLabel, priceLabel, minNightsLabel,
            numberOfReviewsLabel, lastReviewLabel,reviewsPerMonthLabel, 
            hostListingCountLabel, annualAvailabilityLabel;

    /**
     * Constructor for objects of class Panel4
     */
    public Panel4()
    {
        //create grid labels
        nameLabel = new Label("PropertyName");
        roomTypeLabel = new Label("Room Type");
        priceLabel = new Label("Price");
        minNightsLabel = new Label("Minimum Nights");
        numberOfReviewsLabel = new Label("No. of Reviews");
        lastReviewLabel = new Label("Last Review");
        reviewsPerMonthLabel = new Label("Reviews/Month");
        hostListingCountLabel = new Label("Host Listings");
        annualAvailabilityLabel = new Label("Annual Availability");

        //fill grid with row names:
        grid.add(nameLabel, 0, 0);
        grid.add(roomTypeLabel, 0, 1);
        grid.add(priceLabel, 0, 2);
        grid.add(minNightsLabel, 0, 3);
        grid.add(numberOfReviewsLabel, 0, 4);
        grid.add(lastReviewLabel, 0, 5);
        grid.add(reviewsPerMonthLabel, 0, 6);
        grid.add(hostListingCountLabel, 0, 7);
        grid.add(annualAvailabilityLabel, 0, 8);

        //fill grid with data
        //fillGrid();

        //edit grid padding and gaps
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(15);

        //fill gridScroll with panel and program scroll bars
        gridScroll.setContent(grid);
        gridScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        gridScroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        //arrange GUI for botton bar
        borderAnchor.setMinSize(800, 55);
        borderAnchor.getChildren().addAll(backButton, forwardButton);
        borderAnchor.setBottomAnchor(backButton, 15.0);
        borderAnchor.setLeftAnchor(backButton, 50.0);
        borderAnchor.setBottomAnchor(forwardButton, 15.0);
        borderAnchor.setRightAnchor(forwardButton, 50.0);

        root.setCenter(gridScroll);
        root.setBottom(borderAnchor);
        panel4 = new Scene(root, 800, 500);
    }

    private void fillGrid()
    {
        for (int i = 0 ; i < propertyIDs.size() ; i++) {
            Panel4Data data = new Panel4Data(propertyIDs.get(i));
            assignParameters(data);

        }
    }

    private void assignParameters(Panel4Data data)
    {
        propertyName = new Label(data.getPropertyName());
        roomType = data.getRoomType();
        price = data.getPrice();
        minNights = data.getMinNights();
        numberOfReviews = data.getNumberOfReviews();
        lastReview = data.getLatReview();
        reviewsPerMonth = data.getReviewsPerMonth();
        hostListingCount = data.getHostListingCount();
        annualAvailability = data.getAnnualAvailability();
    }

    public boolean AddToCompare(AirbnbListing boxToCompare)
    {
        if (propertyIDs.size() <= 4) {
            // Add listing box to be compared
            propertyIDs.add(boxToCompare);
            return true; // Return true if succeeds, return false if comparing too many boxes
        }
        else {
            return false;
        }
    }
    
    public boolean RemoveFromCompare(AirbnbListing boxToCompare)
    {
        // Add listing box to be compared
        propertyIDs.remove(boxToCompare);
        return true; // Return true if succeeds, return false if comparing too many boxes
    }

    public Scene getScene()
    {
        return panel4;
    }
}
