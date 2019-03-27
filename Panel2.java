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
    private ChoiceBox<String> price_from = new ChoiceBox();
    private ChoiceBox<String> price_to = new ChoiceBox();
    private Label price_from_label = new Label("Price from:");
    private Label price_to_label = new Label("Price to:");
    private Button backButton = new Button("  <  ");
    private Button forwardButton = new Button("  >  ");
    private Scene panel2;
    private AnchorPane root = new AnchorPane();
    private Stage stage;
    
    // array list of borough abbreviations
    private ArrayList<String> boroughArray = new ArrayList<String>();
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
    private int wstmCount, towhCount, kensCount, camdCount, isliCount, wandCount, cityCount, gwchCount, 
    hammCount, brenCount, hackCount, newhCount, ealiCount, barnCount, hrgyCount,
    barkCount, bexlCount, hillCount, hrrwCount, waltCount, enfiCount, hounCount, richCount,
    mertCount, lambCount, redbCount, haveCount, sthwCount, lewsCount, kingCount, suttCount,
    croyCount, bromCount;
    // array list of listings for each borough after price range is selected
    private ArrayList<AirbnbListing> wstmListings, towhListings, kensListings, camdListings, isliListings, 
    wandListings, cityListings, gwchListings, hammListings, brenListings,
    hackListings, newhListings, ealiListings, barnListings, hrgyListings, barkListings,
    bexlListings, hillListings, hrrwListings, waltListings, enfiListings, hounListings,
    richListings, mertListings, lambListings, redbListings, haveListings, sthwListings,
    lewsListings, kingListings, suttListings, croyListings, bromListings;
    // array list of boroughs belonging to each tier. the higher the tier the more listings that borough has
    private ArrayList<String> tier1Boroughs, tier2Boroughs, tier3Boroughs, tier4Boroughs;

    
    
    public Panel2()
    {
        price_from.getItems().addAll("0", "25", "50", "75", "100", "125", "150", "175", "200", "225","250");
        // set a default value 
        price_from.setValue("0");
        
        price_to.getItems().addAll("0", "25", "50", "75", "100", "125", "150", "175", "200", "225","250");
        // set a default value 
        price_to.setValue("0");
        
        root.getChildren().addAll(price_from, price_to, price_from_label, 
                           price_to_label, backButton, forwardButton);
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
        
        List<String> boroughList = Arrays.asList("WSTM", "TOWH", "KENS", "CAMD", "ISLI", "WAND", "CITY",
        "GWCH", "HAMM", "BREN", "HACK", "NEWH", "EALI", "BARN", "HRGY", "BARK",
        "BEXL", "HILL", "HRRW", "WALT", "ENFI", "HOUN", "RICH", "MERT", "LAMB",
        "REDB", "HAVE", "STHW", "LEWS", "KING", "SUTT", "CROY", "BROM");
        boroughArray.addAll(boroughList);
        List<Double> layoutXList = Arrays.asList(341.0, 407.0, 275.0, 308.0, 374.0, 309.0, 375.0, 441.0, 243.0, 243.0, 440.0,
        473.0, 210.0, 275.0, 340.0, 539.0, 505.0, 144.0, 177.0, 406.0, 372.0, 177.0, 210.0, 275.0, 341.0,
        505.0, 571.0, 407.0, 473.0, 241.0, 307.0, 373.0, 439.0);
        layoutXArray.addAll(layoutXList);
        List<Double> layoutYList = Arrays.asList(212.0, 212.0, 212.0, 155.0, 155.0, 269.0, 269.0, 269.0, 269.0, 155.0, 155.0,
        212.0, 212.0, 98.0, 98.0, 212.0, 269.0, 212.0, 155.0, 98.0, 41.0, 269.0, 324.0, 324.0, 324.0,
        155.0, 155.0, 324.0, 324.0, 380.0, 380.0, 380.0, 380.0);
        layoutYArray.addAll(layoutYList);
        
        // instantiate the remaining fields 
        constructFields();
        
        // place boroughs in each tier
        allocateTiers();
        
        createButtons();
        
        //forwardButton.setOnAction(e -> CurrentPanel.forwardButtonPressed());
        //backButton.setOnAction(e -> CurrentPanel.backButtonPressed());
        root.setId("anchorPane");

        //JavaFX must have a Scene inside a Stage
        panel2 = new Scene(root, 800, 500);
    }
    
    /**
     * places every borough into a tier. the higher the tier the more number of listings that borough has
     */
    private void allocateTiers()
    {

        filterListings();
        
        separateByBorough(); 
        
        // add the listings count per borough to an arraylist for iteration later
        List<Integer> countList = Arrays.asList(wstmCount, towhCount, kensCount, camdCount, isliCount, wandCount, cityCount,
        gwchCount, hammCount, brenCount, hackCount, newhCount, ealiCount, barnCount, hrgyCount,
        barkCount, bexlCount, hillCount, hrrwCount, waltCount, enfiCount, hounCount, richCount,
        mertCount, lambCount, redbCount, haveCount, sthwCount, lewsCount, kingCount, suttCount,
        croyCount, bromCount);
        ArrayList<Integer> countArray = new ArrayList<Integer>();
        countArray.addAll(countList);
        
        // for every count, decide its tier and add the respective borough to the tier
        for(int i = 0; i < countArray.size(); i++) {
            int count = countArray.get(i);
            // tier 4: listing count of 900 and above
            if(count >= 900) {
                tier4Boroughs.add(boroughArray.get(i));
            }
            // tier 3: listing count of 600 to 899
            else if(count >= 600) {
                tier3Boroughs.add(boroughArray.get(i));
            }
            // tier 2: listing count of 300 to 599
            else if(count >= 300) {
                tier2Boroughs.add(boroughArray.get(i));
            }
            // tier 1: listing count of 0 to 299
            else if(count >= 0) {
                tier1Boroughs.add(boroughArray.get(i));
            }
        }
    }
    
    /**
     * constructors for fields defined above
     */
    private void constructFields() 
    {
        wstmListings = new ArrayList<AirbnbListing>();
        towhListings = new ArrayList<AirbnbListing>();
        kensListings = new ArrayList<AirbnbListing>();
        camdListings = new ArrayList<AirbnbListing>();
        isliListings = new ArrayList<AirbnbListing>();
        wandListings = new ArrayList<AirbnbListing>();
        cityListings = new ArrayList<AirbnbListing>();
        gwchListings = new ArrayList<AirbnbListing>();
        hammListings = new ArrayList<AirbnbListing>();
        brenListings = new ArrayList<AirbnbListing>();
        hackListings = new ArrayList<AirbnbListing>();
        newhListings = new ArrayList<AirbnbListing>();
        ealiListings = new ArrayList<AirbnbListing>();
        barnListings = new ArrayList<AirbnbListing>();
        hrgyListings = new ArrayList<AirbnbListing>();
        barkListings = new ArrayList<AirbnbListing>();
        bexlListings = new ArrayList<AirbnbListing>();
        hillListings = new ArrayList<AirbnbListing>();
        hrrwListings = new ArrayList<AirbnbListing>();
        waltListings = new ArrayList<AirbnbListing>();
        enfiListings = new ArrayList<AirbnbListing>();
        hounListings = new ArrayList<AirbnbListing>();
        richListings = new ArrayList<AirbnbListing>();
        mertListings = new ArrayList<AirbnbListing>();
        lambListings = new ArrayList<AirbnbListing>();
        redbListings = new ArrayList<AirbnbListing>();
        haveListings = new ArrayList<AirbnbListing>();
        sthwListings = new ArrayList<AirbnbListing>();
        lewsListings = new ArrayList<AirbnbListing>();
        kingListings = new ArrayList<AirbnbListing>();
        suttListings = new ArrayList<AirbnbListing>();
        croyListings = new ArrayList<AirbnbListing>();
        bromListings = new ArrayList<AirbnbListing>();
        
        tier1Boroughs = new ArrayList<String>();
        tier2Boroughs = new ArrayList<String>();
        tier3Boroughs = new ArrayList<String>();
        tier4Boroughs = new ArrayList<String>();
    }
    
    private void createButtons()
    {
        for(int i=0; i<boroughArray.size(); i++) {
            Button button = new Button();
            button.setLayoutX(layoutXArray.get(i));
            button.setLayoutY(layoutYArray.get(i));
            button.setMnemonicParsing(false);
            button.setPrefHeight(74.0);
            button.setPrefWidth(67.0);
            button.getStyleClass().clear();
            button.getStyleClass().add("map-button");
            button.getStylesheets().clear();
            button.getStylesheets().add("map.css");
            button.setText(boroughArray.get(i));
            
            // add style classes according to the borough's tier so that css will colour it accordingly
            String currentBorough = button.getText();
            if(tier1Boroughs.contains(currentBorough)) {
              button.getStyleClass().add("tier-1");
            }
            else if(tier2Boroughs.contains(currentBorough)) {
              button.getStyleClass().add("tier-2");
            }
            else if(tier3Boroughs.contains(currentBorough)) {
              button.getStyleClass().add("tier-3");
            }
            else if(tier4Boroughs.contains(currentBorough)) {
              button.getStyleClass().add("tier-4");
            }
            
            // add button to root
            root.getChildren().add(button);
        }
    }
    
    private void separateByBorough()
    {
    
        // for every narrowed down listing, add it to array lists according to borough and count the 
        // number of listings per borough
        for(AirbnbListing listing: filteredListings) {
            String nbhd = listing.getNeighbourhood();
            switch (nbhd) {
                case "Westminster":
                    wstmCount++;
                    wstmListings.add(listing);
                    break;
                case "Tower Hamlets":
                    towhCount++;
                    towhListings.add(listing);
                    break;
                case "Kensington and Chelsea":
                    kensCount++;
                    kensListings.add(listing);
                    break;
                case "Camden":
                    camdCount++;
                    camdListings.add(listing);
                    break;
                case "Islington":
                    isliCount++;
                    isliListings.add(listing);
                    break;
                case "Wandsworth":
                    wandCount++;
                    wandListings.add(listing);
                    break;
                case "City of London":
                    cityCount++;
                    cityListings.add(listing);
                    break;
                case "Greenwich":
                    gwchCount++;
                    gwchListings.add(listing);
                    break;
                case "Hammersmith and Fulham":
                    hammCount++;
                    hammListings.add(listing);
                    break;
                case "Brent":
                    brenCount++;
                    brenListings.add(listing);
                    break;
                case "Hackney":
                    hackCount++;
                    hackListings.add(listing);
                    break;
                case "Newham":
                    newhCount++;
                    newhListings.add(listing);
                    break;
                case "Ealing":
                    ealiCount++;
                    ealiListings.add(listing);
                    break;
                case "Barnet":
                    barnCount++;
                    barnListings.add(listing);
                    break;
                case "Haringey":
                    hrgyCount++;
                    hrgyListings.add(listing);
                    break;
                case "Barking and Dagenham":
                    barkCount++;
                    barkListings.add(listing);
                    break;
                case "Bexley":
                    bexlCount++;
                    bexlListings.add(listing);
                    break;
                case "Hillingdon":
                    hillCount++;
                    hillListings.add(listing);
                    break;
                case "Harrow":
                    hrrwCount++;
                    hrrwListings.add(listing);
                    break;
                case "Waltham Forest":
                    waltCount++;
                    waltListings.add(listing);
                    break;
                case "Enfield":
                    enfiCount++;
                    enfiListings.add(listing);
                    break;
                case "Hounslow":
                    hounCount++;
                    hounListings.add(listing);
                    break;
                case "Richmond upon Thames":
                    richCount++;
                    richListings.add(listing);
                    break;
                case "Merton":
                    mertCount++;
                    mertListings.add(listing);
                    break;
                case "Lambeth":
                    lambCount++;
                    lambListings.add(listing);
                    break;
                case "Redbridge":
                    redbCount++;
                    redbListings.add(listing);
                    break;
                case "Havering":
                    haveCount++;
                    haveListings.add(listing);
                    break;
                case "Southwark":
                    sthwCount++;
                    sthwListings.add(listing);
                    break;
                case "Lewisham":
                    lewsCount++;
                    lewsListings.add(listing);
                    break;
                case "Kingston upon Thames":
                    kingCount++;
                    kingListings.add(listing);
                    break;
                case "Sutton":
                    suttCount++;
                    suttListings.add(listing);
                    break;
                case "Croydon":
                    croyCount++;
                    croyListings.add(listing);
                    break;
                case "Bromley":
                    bromCount++;
                    bromListings.add(listing);
                    break;
            }
        }
    }
    
    private void filterListings() 
    {
        // for every listing, add it to narrowed down array list if it is within price range
        for(AirbnbListing listing: loader.load()) {
            int price = listing.getPrice();
            if(price >= fromPrice && price <= toPrice) {
                filteredListings.add(listing);
            }
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
    
    public Scene getScene()
    {
        return panel2;
    }

    public void setPriceFrom(String newPrice)
    {
        price_from.setValue(newPrice);
    }
    
    public void setPriceTo(String newPrice)
    {
        price_to.setValue(newPrice);
    }
    
    public void updateToMap(String newPrice)
    {
        toPrice = Integer.parseInt(newPrice);
        if (toPrice > fromPrice) {
            allocateTiers();
            createButtons();
        }
    }
    
    public void updateFromMap(String newPrice)
    {
        fromPrice = Integer.parseInt(newPrice);
        if (toPrice > fromPrice) {
            allocateTiers();
            createButtons();
        }
    }
}
