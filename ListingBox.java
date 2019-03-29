import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.stage.*;

/**
 * This class represents the box of a listing that is displayed in the popup window
 * when a borough is clicked. This class also includes the additional popup that shows
 * the full information of a listing when the box is clicked.
 */

public class ListingBox
{
    private Stage stage;
    private VBox vbox;
    private AirbnbListing listing;
    private String name;
    private String hostName;
    private String neighbourhood;
    private String roomType;
    private int price;
    private int minNights;
    private int numOfReviews;
    private String lastReview;
    private double reviewsPerMonth;
    private int hostListingsCount;
    private int availability365;
    private Panel2 panel2;
    private Button compareButton = new Button("Compare");

    // upon construction, this class takes in an AirbnbListing
    public ListingBox(AirbnbListing listing, Stage stage, Panel2 panel2)
    {
        this.stage = stage;
        this.listing = listing;
        this.name = listing.getName();
        this.hostName = listing.getHost_name();
        this.neighbourhood = listing.getNeighbourhood();
        this.roomType = listing.getRoom_type();
        this.price = listing.getPrice();
        this.minNights = listing.getMinimumNights();
        this.numOfReviews = listing.getNumberOfReviews();
        this.lastReview = listing.getLastReview();
        this.reviewsPerMonth = listing.getReviewsPerMonth();
        this.hostListingsCount = listing.getCalculatedHostListingsCount();
        this.availability365 = listing.getAvailability365();

        this.panel2 = panel2;

        makeVBox();
    }

    /**
     * Creates a VBox that displays room type, listing name. host name, price and number of reviews.
     * There is also a button "View" that opens a new window upon clicking that shows full information.
     */
    private void makeVBox()
    {
        vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        // display information
        Label roomTypeL = new Label("Room type: " + roomType);
        roomTypeL.setWrapText(true);
        Label nameL = new Label("Name: " + name);
        nameL.setWrapText(true);
        Label hostNameL = new Label("Host name: " + hostName);
        hostNameL.setWrapText(true);
        Label priceL = new Label("Price: £" + String.valueOf(price));
        priceL.setWrapText(true);
        Label numOfReviewsL = new Label("Number of reviews: " +
                String.valueOf(numOfReviews));
        numOfReviewsL.setWrapText(true);

        // button that opens new window showing full information upon clicking
        Button viewButton = new Button("View");
        viewButton.setOnAction(value ->
            {
                openDescription();
            }
        );
        // button that lets you add the listing to the comparator panel
        compareButton.setOnAction(value ->
            {
                toggleCompare();
            }
        );

        HBox hbox = new HBox(viewButton, compareButton);

        vbox.getChildren().addAll(roomTypeL, nameL, hostNameL, priceL, numOfReviewsL, hbox);

    }

    /**
     * Open new window displaying all information
     */
    private void openDescription()
    {
        VBox root = new VBox();

        root.setPadding(new Insets(10, 10, 10, 10));
        root.setSpacing(10);

        // display information
        Label nameL = new Label("Name: " + name);
        nameL.setWrapText(true);
        Label hostNameL = new Label("Host name: " + hostName);
        hostNameL.setWrapText(true);
        Label roomTypeL = new Label("Room type: " + roomType);
        roomTypeL.setWrapText(true);
        Label priceL = new Label("Price: £" + String.valueOf(price));
        priceL.setWrapText(true);
        Label minL = new Label("Minimum nights: " + String.valueOf(minNights));
        minL.setWrapText(true);
        Label numOfReviewsL = new Label("Number of reviews: " +
                String.valueOf(numOfReviews));
        Label lastL = new Label("Last review: " + lastReview);
        lastL.setWrapText(true);
        Label rpmL = new Label("Reviews per month: " + String.valueOf(reviewsPerMonth));
        rpmL.setWrapText(true);
        Label hlcL = new Label("Host listings count: " +
                String.valueOf(hostListingsCount));
        hlcL.setWrapText(true);

        root.getChildren().addAll(nameL, hostNameL, roomTypeL, priceL, minL, numOfReviewsL, lastL, rpmL, hlcL);

        // new scene
        Scene scene2 = new Scene(root);
        // New window (stage)
        Stage newWindow = new Stage();
        newWindow.setTitle(name);
        newWindow.setScene(scene2);
        // specify modality for new window
        // when new window displays you cant interact w parent window
        newWindow.initModality(Modality.WINDOW_MODAL);
        // specify parent window
        newWindow.initOwner(stage);
        newWindow.show();
    }

    private boolean addToCompare()
    {
        // This function adds this listing box to be compared in panel4
        // Works by sending box through panel2, panelControl to panel4
        return panel2.addToCompare(listing);
    }

    private boolean removeFromCompare()
    {
        // This function adds this listing box to be compared in panel4
        // Works by sending box through panel2, panelControl to panel4
        return panel2.removeFromCompare(listing);
    }

    private void toggleCompare()
    {
        // Listener calls this function
        if (removeFromCompare()) // If true then listing was being compared, and is now removed
        {
            compareButton.setText("Compare");
        }
        else if (addToCompare())// If false then listing wasn't being compared, so add listing to be compared
        {
            compareButton.setText("Remove");
        }
    }

    /**
     * public get methods
     */

    public VBox getVBox()
    {
        return vbox;
    }

    public String getName() {
        return name;
    }

    public String getHostName() {
        return hostName;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getPrice() {
        return price;
    }

    public int getMinNights() {
        return minNights;
    }

    public int getNumOfReviews() {
        return numOfReviews;
    }

    public String getLastReview() {
        return lastReview;
    }

    public double getReviewsPerMonth() {
        return reviewsPerMonth;
    }

    public int getHostListingsCount() {
        return hostListingsCount;
    }

    public int getAvailability365() {
        return availability365;
    }
}
