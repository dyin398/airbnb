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
import javafx.stage.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.AbstractChronology;
import java.time.chrono.IsoChronology;

import java.lang.Object;
import java.net.URL;
import java.io.*;
import java.util.*;

/**
 * This is the GUI of the first panel - the welcome page of the application.
 *
 * @author (AirYeet)
 * @version (a version number or a date)
 */

public class Panel2
{
    // GUI fields
    private ChoiceBox<String> price_from = new ChoiceBox();
    private ChoiceBox<String> price_to = new ChoiceBox();
    private Label price_from_label = new Label("Price from:");
    private Label price_to_label = new Label("Price to:");
    private Label priceError = new Label("Please enter a valid price range!");
    private Label tierLabel = new Label("Number of properties available:");
    private Label tier1available = new Label("0-299");
    private Label tier2available = new Label("300-599");
    private Label tier3available = new Label("600-899");
    private Label tier4available = new Label("900+");
    private Label date_from_label = new Label("Date From:");
    private Label date_to_label = new Label("Date To:");
    private Button backButton = new Button("  <  ");
    private Button forwardButton = new Button("  >  ");
    private Button tier1, tier2, tier3, tier4;
    private DatePicker datePickerin = new DatePicker();
    private DatePicker datePickerout = new DatePicker();
    private Scene panel2;
    private AnchorPane root = new AnchorPane();
    private Stage stage;

    // List of borough abbreviations (used as keys for hashmap)
    private List<String> boroughList = Arrays.asList("WSTM", "TOWH", "KENS", "CAMD", "ISLI", "WAND", "CITY",
            "GWCH", "HAMM", "BREN", "HACK", "NEWH", "EALI", "BARN", "HRGY", "BARK",
            "BEXL", "HILL", "HRRW", "WALT", "ENFI", "HOUN", "RICH", "MERT", "LAMB",
            "REDB", "HAVE", "STHW", "LEWS", "KING", "SUTT", "CROY", "BROM");
    private Map<String, String> boroughMap = new HashMap<String, String>()
        {
            {
                put("WSTM", "Westminster");
                put("TOWH", "Tower Hamlets");
                put("KENS", "Kensington and Chelsea");
                put("CAMD", "Camden");
                put("ISLI", "Islington");
                put("WAND", "Wandsworth");
                put("CITY", "City of London");
                put("GWCH", "Greenwich");
                put("HAMM", "Hammersmith and Fulham");
                put("BREN", "Brent");
                put("HACK", "Hackney");
                put("NEWH", "Newham");
                put("EALI", "Eealing");
                put("BARN", "Barnet");
                put("HRGY", "Haringey");
                put("BARK", "Barking and Dagenham");
                put("BEXL", "Bexley");
                put("HILL", "Hillingdon");
                put("HRRW", "Harrow");
                put("WALT", "Waltham Forest");
                put("ENFI", "Enfield");
                put("HOUN", "Hounslow");
                put("RICH", "Richmond upon Thames");
                put("MERT", "Merton");
                put("LAMB", "Lambeth");
                put("REDB", "Redbridge");
                put("HAVE", "Havering");
                put("STHW", "Southwark");
                put("LEWS", "Lewisham");
                put("KING", "Kingston upon Thames");
                put("SUTT", "Sutton");
                put("CROY", "Croydon");
                put("BROM", "Bromley");
            }
        };

    // array list of x coordinates for each button
    private ArrayList<Double> layoutXArray = new ArrayList<Double>();
    // array list of y coordinates
    private ArrayList<Double> layoutYArray = new ArrayList<Double>();
    // data loader to load data for displaying
    private AirbnbDataLoader loader = new AirbnbDataLoader();
    // array list of all listings
    private ArrayList<AirbnbListing> allListings;
    // array list of listings narrowed down by selected price range
    private ArrayList<AirbnbListing> filteredListings = new ArrayList<AirbnbListing>();
    // ints to store min price and max price selected
    private int fromPrice, toPrice;
    // the number of listings per borough after price range is selected
    private HashMap<String, Integer> counts;
    // hash map of listings for each borough after price range is selected
    private HashMap<String, ArrayList<AirbnbListing>> listings;
    // array list of boroughs belonging to each tier. the higher the tier the more listings that borough has
    private ArrayList<String> tier1Boroughs, tier2Boroughs, tier3Boroughs, tier4Boroughs;

    public Panel2() {
        // choice boxes
        price_from.getItems().addAll("0", "25", "50", "75", "100", "125", "150", "175", "200", "225","250");
        // set a default value 
        price_from.setValue("0");

        price_to.getItems().addAll("0", "25", "50", "75", "100", "125", "150", "175", "200", "225","250");
        // set a default value 
        price_to.setValue("0");

        // creating the legend for the map colours
        tier1 = new Button("    ");
        tier2 = new Button("    ");
        tier3 = new Button("    ");
        tier4 = new Button("    ");

        // Format map key buttons
        tier1.getStyleClass().add("map-button");
        tier1.getStylesheets().add("map.css");
        tier1.getStyleClass().add("tier-1");
        tier2.getStyleClass().add("map-button");
        tier2.getStylesheets().add("map.css");
        tier2.getStyleClass().add("tier-2");
        tier3.getStyleClass().add("map-button");
        tier3.getStylesheets().add("map.css");
        tier3.getStyleClass().add("tier-3");
        tier4.getStyleClass().add("map-button");
        tier4.getStylesheets().add("map.css");
        tier4.getStyleClass().add("tier-4");

        priceError.setVisible(false); // Start with price error message hidden

        /// Position Elements
        root.getChildren().addAll(price_from, price_to, price_from_label, price_to_label, backButton, 
            forwardButton, priceError, tier1, tier2, tier3, tier4, tierLabel, tier1available,
            tier2available, tier3available, tier4available, datePickerin, datePickerout,
            date_from_label, date_to_label);
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
        root.setLeftAnchor(priceError, 480.0);
        root.setTopAnchor(priceError, 37.0);
        root.setTopAnchor(tier1, 290.0);
        root.setLeftAnchor(tier1, 565.0);
        root.setTopAnchor(tier2, 330.0);
        root.setLeftAnchor(tier2, 565.0);
        root.setTopAnchor(tier3, 370.0);
        root.setLeftAnchor(tier3, 565.0);
        root.setTopAnchor(tier4, 410.0);
        root.setLeftAnchor(tier4, 565.0);
        root.setTopAnchor(tierLabel, 260.0);
        root.setLeftAnchor(tierLabel, 565.0);
        root.setTopAnchor(tier1available, 295.0);
        root.setLeftAnchor(tier1available, 610.0);
        root.setTopAnchor(tier2available, 335.0);
        root.setLeftAnchor(tier2available, 610.0);
        root.setTopAnchor(tier3available, 375.0);
        root.setLeftAnchor(tier3available, 610.0);
        root.setTopAnchor(tier4available, 415.0);
        root.setLeftAnchor(tier4available, 610.0);
        root.setTopAnchor(datePickerin, 70.0);
        root.setLeftAnchor(datePickerin, 565.0);
        root.setTopAnchor(datePickerout, 115.0);
        root.setLeftAnchor(datePickerout, 565.0);
        root.setTopAnchor(date_from_label, 52.0);
        root.setLeftAnchor(date_from_label, 565.0);
        root.setTopAnchor(date_to_label, 97.0);
        root.setLeftAnchor(date_to_label, 565.0);

        // add X and Y coordinates to their respective array lists
        List<Double> layoutXList = Arrays.asList(231.0, 297.0, 165.0, 198.0, 264.0, 199.0, 265.0, 331.0, 133.0, 133.0, 330.0,
                363.0, 100.0, 165.0, 230.0, 429.0, 395.0, 34.0, 67.0, 296.0, 262.0, 67.0, 100.0, 165.0, 231.0,
                395.0, 461.0, 297.0, 363.0, 131.0, 197.0, 263.0, 329.0);
        layoutXArray.addAll(layoutXList);
        List<Double> layoutYList = Arrays.asList(212.0, 212.0, 212.0, 155.0, 155.0, 269.0, 269.0, 269.0, 269.0, 155.0, 155.0,
                212.0, 212.0, 98.0, 98.0, 212.0, 269.0, 212.0, 155.0, 98.0, 41.0, 269.0, 324.0, 324.0, 324.0,
                155.0, 155.0, 324.0, 324.0, 380.0, 380.0, 380.0, 380.0);
        layoutYArray.addAll(layoutYList);

        // place boroughs in each tier
        allocateTiers();

        // Create map of boroughs
        createButtons();

        root.setId("anchorPane");

        //JavaFX must have a Scene inside a Stage
        panel2 = new Scene(root, 800, 500);
    }

    /**
     * places every borough into a tier. the higher the tier the more number of listings that borough has
     */
    private void allocateTiers()
    {
        constructFields(); // Reset variables that must be reset between each search
        filterListings(); // Filter listings between price range, store result in filteredListings

        separateByBorough(); // Sort listings by borough into counts hashmap

        // for every count, decide its tier and add the respective borough to the tier
        Iterator iterator = boroughMap.keySet().iterator();
        while (iterator.hasNext())
        {
            String currentKey = (String)iterator.next();
            int count = counts.get(currentKey);
            // tier 4: listing count of 900 and above
            if(count >= 900) {
                tier4Boroughs.add(currentKey);
            }
            // tier 3: listing count of 600 to 899
            else if(count >= 600) {
                tier3Boroughs.add(currentKey);
            }
            // tier 2: listing count of 300 to 599
            else if(count >= 300) {
                tier2Boroughs.add(currentKey);
            }
            // tier 1: listing count of 0 to 299
            else if(count >= 0) {
                tier1Boroughs.add(currentKey);
            }
        }
    }

    /**
     * constructors for fields defined above
     */
    private void constructFields() 
    {
        // Empty borough tier arrays
        tier1Boroughs = new ArrayList<String>();
        tier2Boroughs = new ArrayList<String>();
        tier3Boroughs = new ArrayList<String>();
        tier4Boroughs = new ArrayList<String>();

        // Clear previously found listings
        filteredListings = new ArrayList<AirbnbListing>();

        // Reset Hashmaps
        counts = new HashMap<String, Integer>();
        listings = new HashMap<String, ArrayList<AirbnbListing>>();
        Iterator iterator = boroughMap.keySet().iterator();
        while (iterator.hasNext())
        {
            String currentKey = (String)iterator.next();
            counts.put(currentKey, 0);
            listings.put(currentKey, new ArrayList<AirbnbListing>());
        }
    }

    private void createButtons()
    {
        Iterator iterator = boroughMap.keySet().iterator();
        while (iterator.hasNext()) {
            String currentKey = (String)iterator.next();
            Button button = new Button();
            String currentNbhd = boroughMap.get(currentKey);
            ArrayList<AirbnbListing> currentBucket = listings.get(currentKey);

            button.setLayoutX(layoutXArray.get(boroughList.indexOf(currentKey)));
            button.setLayoutY(layoutYArray.get(boroughList.indexOf(currentKey)));
            button.setMnemonicParsing(false);
            button.setPrefHeight(74.0);
            button.setPrefWidth(67.0);
            button.getStyleClass().clear();
            button.getStyleClass().add("map-button");
            button.getStylesheets().clear();
            button.getStylesheets().add("map.css");
            button.setText(currentKey);
            button.setOnAction(value -> 
            {
                openListings(currentBucket, currentNbhd);
            });

            // add style classes according to the borough's tier so that css will colour it accordingly
            String currentBorough = button.getText();
            if(tier1Boroughs.contains(currentBorough)) {
                //Tier 1
                button.getStyleClass().add("tier-1");
            }
            else if(tier2Boroughs.contains(currentBorough)) {
                //Tier 2
                button.getStyleClass().add("tier-2");
            }
            else if(tier3Boroughs.contains(currentBorough)) {
                //Tier 3
                button.getStyleClass().add("tier-3");
            }
            else if(tier4Boroughs.contains(currentBorough)) {
                //Tier 4
                button.getStyleClass().add("tier-4");
            }

            // add button to root
            root.getChildren().add(button);
        }
    }

    private void separateByBorough()
    {
        // Count number of listings that are within the price range for each borough
        for(AirbnbListing listing: filteredListings) {
            // Loop through listings within price range
            String nbhd = listing.getNeighbourhood();

            // Find which borough the listing is in
            // This method has time complexity O(n boroughs) but given small number of boroughs,
            //  this method is sufficient
            // Could speed up at cost of space complexity but not necessary in this application
            Iterator iterator = boroughMap.keySet().iterator();
            while (iterator.hasNext())
            {
                String currKey = (String)iterator.next();
                if (nbhd.equals(boroughMap.get(currKey)))
                { 
                    counts.put(currKey, counts.get(currKey)+1);
                    listings.get(currKey).add(listing);
                    break;
                }
            }
        }
    }

    private void filterListings() 
    {
        // Filter contents of all listings to be within price range
        // Store output in filteredListings
        for(AirbnbListing listing: loader.load()) {
            int price = listing.getPrice();
            if(price >= fromPrice && price <= toPrice) {
                filteredListings.add(listing);
            }
        }
    }

    /**
     * The borough button has been clicked.
     */
    private void openListings(ArrayList<AirbnbListing> boroughBucket, String borough)
    {
        BoroughWindow popup = new BoroughWindow();
        BorderPane root = popup.getBorderPane();
        ChoiceBox<String> choiceBox = popup.getChoiceBox();
        TilePane tilePane = popup.getTilePane();
        // set choicebox items: sort by
        choiceBox.getItems().addAll("Host name", "Price", "Number of reviews");
        
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
        (ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
        {
            ArrayList<AirbnbListing> newBucket = boroughBucket;
            sortListings(newValue, newBucket);
            tilePane.getChildren().clear();
            addBoxes(tilePane, newBucket);
        }
        );
        
        choiceBox.setValue("Price");
        
        addBoxes(tilePane, boroughBucket);

        // new scene
        Scene scene2 = new Scene(root);
        // New window (stage)
        Stage newWindow = new Stage();
        newWindow.setTitle(borough);
        newWindow.setScene(scene2);
        // specify modality for new window
        // when new window displays you cant interact w parent window
        newWindow.initModality(Modality.WINDOW_MODAL);
        // specify parent window
        newWindow.initOwner(stage);
        newWindow.show();
    }
    
    /**
     * Sort the provided array list of listings based on the sorting method specified.
     */
    private void sortListings(String sortBy, ArrayList<AirbnbListing> bucket)
    {
        switch(sortBy) {
            case "Host name":
                Collections.sort(bucket, (AirbnbListing l1, AirbnbListing l2)->  
                l1.getHost_name().toLowerCase().compareTo(l2.getHost_name().toLowerCase()));
                break;
            case "Price":
                Collections.sort(bucket, (AirbnbListing l1, AirbnbListing l2) -> l1.getPrice()-l2.getPrice());
                break;
            case "Number of reviews":
                Collections.sort(bucket, (AirbnbListing l1, AirbnbListing l2) -> l1.getNumberOfReviews()-l2.getNumberOfReviews());
                break;
        }
    }
    
    /**
     * Add listings from provided array list to tile pane
     */
    private void addBoxes(TilePane pane, ArrayList<AirbnbListing> bucket)
    {
        // create a ListingBox for each listing in that borough and display vbox on tile pane
        for(AirbnbListing listing: bucket) {
            ListingBox listingBox = new ListingBox(listing, stage);
            VBox vbox = listingBox.getVBox();
            pane.getChildren().add(vbox);
        }
    }
    
    /**
     * public methods
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
        return panel2;
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

    public void updateToMap(String newPrice)
    {
        // Update map when price upper bound changed
        // This function is attached to a listener in PanelController
        toPrice = Integer.parseInt(newPrice);
        if (toPrice > fromPrice) { // If price range is valid
            allocateTiers(); // Recalculate map
            createButtons();
        }
    }

    public void updateFromMap(String newPrice)
    {
        // Update map when price lower bound changed
        // This function is attached to a listener in PanelController
        fromPrice = Integer.parseInt(newPrice);
        if (toPrice > fromPrice) { // If price range is valid
            allocateTiers(); // Recalculate map
            createButtons();
        }
    }
}
