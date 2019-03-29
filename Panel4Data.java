
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.chart.*;
import java.time.*;
import java.time.format.*;
import java.lang.String;

/**
 * This class produces the displayed data of each Airbnb listing needed for panel 4.
 */

public class Panel4Data
{
    private AirbnbListing listing;
    private String name;
    private String roomType;
    private int price;
    private int minNights;
    private int numOfReviews;
    private String lastReview;
    private double reviewsPerMonth;
    private int hostListingsCount;
    private int availability365;

    private final double MAXRPM = 16.87;
    private final int MAXAVAIL = 365;

    // upon construction, this class takes in an AirbnbListing
    public Panel4Data(AirbnbListing listing)
    {
        this.listing = listing;
        this.name = listing.getName();
        this.roomType = listing.getRoom_type();
        this.price = listing.getPrice();
        this.minNights = listing.getMinimumNights();
        this.numOfReviews = listing.getNumberOfReviews();
        this.lastReview = listing.getLastReview();
        this.reviewsPerMonth = listing.getReviewsPerMonth();
        this.hostListingsCount = listing.getCalculatedHostListingsCount();
        this.availability365 = listing.getAvailability365();
    }

    public Label getPropertyName()
    {
        Label label = new Label("name");
        return label;
    }

    public ImageView getRoomType()
    {
        Image house = new Image("house.png"); // Icon made by Vectors Market from www.flaticon.com
        ImageView ivHouse = new ImageView();
        ivHouse.setImage(house);

        Image room = new Image("room.png"); // Icon made by Freepik from www.flaticon.com
        ImageView ivRoom = new ImageView();
        ivRoom.setImage(room);

        if(roomType.equals("Entire home/apt")) {
            return ivHouse;
        }
        else {
            return ivRoom;
        }
    }

    public Label getMinNights()
    {
        Label label = new Label(String.valueOf(minNights));
        return label;
    }

    public Label getNumberOfReviews()
    {
        Label label = new Label(String.valueOf(numOfReviews));
        return label;
    }

    public Label getLastReview()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        LocalDate LRlocaldate = LocalDate.parse(lastReview, formatter);
        LocalDate now = LocalDate.now();

        Period period = Period.between(LRlocaldate, now);

        Label label = new Label(getPeriodString(period));

        return label;
    }

    public PieChart getReviewsPerMonth()
    {
        PieChart.Data valueSlice = new PieChart.Data("", reviewsPerMonth);
        PieChart.Data remainderSlice = new PieChart.Data("", MAXRPM - reviewsPerMonth);

        PieChart pc = new PieChart();
        pc.setStartAngle(90);
        pc.getData().addAll(valueSlice, remainderSlice);

        return pc;
    }

    public Label getHostListingsCount()
    {
        Label label = new Label(String.valueOf(hostListingsCount));
        return label;
    }

    public PieChart getAnnualAvailability()
    {
        PieChart.Data valueSlice = new PieChart.Data("", availability365);
        PieChart.Data remainderSlice = new PieChart.Data("", MAXAVAIL - availability365);

        PieChart pc = new PieChart();
        pc.setStartAngle(90);
        pc.getData().addAll(valueSlice, remainderSlice);

        return pc;
    }

    private String getPeriodString(Period period)
    {
        int intDays = period.getDays();
        int intMonths = period.getMonths();
        int intYears = period.getYears();
        String days, months, years;

        if(intDays > 0) {
            days = String.valueOf(intDays) + " days ";
        }
        else {
            days = "";
        }

        if(intMonths > 0) {
            months = String.valueOf(intMonths) + " months ";
        }
        else {
            months = "";
        }

        if(intYears > 0) {
            years = String.valueOf(intYears) + " years ";
        }
        else {
            years = "";
        }

        return years + months + days;
    }

}
