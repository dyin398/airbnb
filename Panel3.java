import java.util.*;

/**
 * Write a description of class Panel3 here.
 *
 * @author (AirYeet)
 * @version (a version number or a date)
 */
public class Panel3
{
    private ArrayList<AirbnbListing> allTheProperties;
    private ArrayList<AirbnbListing> listOfProperties;
    private ArrayList<String> statsToDisplay;
    private ArrayList<String> allTheStats;
    private int totalCountProperties=0;
    private double reviewsPerProperty=0;
    private int notPrivateRooms=0;
    private String boroughWithMostexpensiveProperty;
    AirbnbListing expensiveProperty = null;
    private int highestHostListing = 0;
    private int maxEntireHomePrice = 0;
    private String maxEntireHomePropertyId = null;
    AirbnbDataLoader propertyList;
    private String currentNeighbourhood;
    private String boroughWithMaxReviewsPerMonth = "";

    // the constructor of panel 3
    /**
     * This is the constructor of Panel 3 and it initliases all the array lists and fields.
     * It also loads the CSV file to be read using the AirbnbLoader.
     */
    public Panel3()
    {
        listOfProperties = new ArrayList<>();
        statsToDisplay = new ArrayList<>();
        allTheStats = new ArrayList<>();
        propertyList = new AirbnbDataLoader();
        allTheProperties =propertyList.load();
    }

    /**
     * Creates all the info needed for the stats to be shown.
     * @param String of values that is shown.
     */
    private void initiateList()
    {
        // stats to show on screen
        allTheStats.add("Number of Properties:");
        allTheStats.add("Avg reviews per property:");
        allTheStats.add("Properties which are only homes and apartments:");
        allTheStats.add("Most expensive borough:");
        allTheStats.add("Borough with the most expensive property:");
        allTheStats.add("Most expensive entire home:");
        allTheStats.add("Number of properties with a host listing above 50:");
        allTheStats.add("Higest available borough:");

        // stats not shown at the start
        statsToDisplay.add("Borough with the most expensive property:");
        statsToDisplay.add("Most expensive entire home:");
        statsToDisplay.add("Number of properties with a host listing above 50:");
        statsToDisplay.add("Higest available borough:");

        // default stats
        statsToDisplay.add("Number of Properties:");
        statsToDisplay.add("Avg reviews per property:");
        statsToDisplay.add("Properties which are only homes and apartments:");
        statsToDisplay.add("Most expensive borough:"); 
    }

    /**
     * Gets the number of available properties.
     * @return the available properties as a int value.
     */
    public int countProperties(){
        long numberOfReviews=0;
        int minNights;
        long maxPrice=0;
        notPrivateRooms = 0;
        highestHostListing = 0;
        String privateRoom="Private room";
        String entireHome = "Entire home/apt";
        maxEntireHomePrice = 0;
        for(AirbnbListing property : allTheProperties){
            //available properties
            if(property.getAvailability365()>0){
                totalCountProperties++;
            }
            //count the total number of reviews for all properties
            numberOfReviews+=property.getNumberOfReviews();    

            //count not private rooms
            if(!property.getRoom_type().equals(privateRoom))
            {
                notPrivateRooms++;
            }

            long currentAmount = property.getMinimumNights()*property.getPrice();
            if(currentAmount>maxPrice){
                maxPrice=currentAmount;
                expensiveProperty=property;
            }

            //highest host listing
            //returns the no. of properties with host listing count over 50.
            if(property.getCalculatedHostListingsCount() >50){
                highestHostListing++;
            }

            // Find the most expensive property that is an entire home and save its name
            if((property.getRoom_type()).equals(entireHome)){ // If entire home
                int currentEntireHomePrice = property.getPrice();
                if(currentEntireHomePrice>maxEntireHomePrice) // If most expensive seen so far
                {
                    maxEntireHomePrice=currentEntireHomePrice;
                    maxEntireHomePropertyId=property.getId(); 
                    // write a method to get this
                }
            } 
            
        ArrayList<String> allBoroughs = new ArrayList<>();
        HashMap<String,Integer> pricesOfBorough = new HashMap<>();
        HashMap<String,Integer> noOfPropertiesPerBorough = new HashMap<>();
        String mostExpensiveBorough = "";
        int mostExpensiveBoroughAvgPrice = 0;
       }
        // review per property is calculated by dividing the total no of reviews by the property size.
        reviewsPerProperty=numberOfReviews/(allTheProperties.size());
        //gets the neighbourhood of the most expensive property.
        boroughWithMostexpensiveProperty = expensiveProperty.getNeighbourhood();

        return totalCountProperties;
    }

    /**
     * Gets the name of the most expensive borough.
     * @return the most expensive borough.
     */
    public String getMostExpensiveBorough(){
        ArrayList<String> allBoroughs = new ArrayList<>();
        HashMap<String,Integer> pricesOfBorough = new HashMap<>();
        HashMap<String,Integer> noOfPropertiesPerBorough = new HashMap<>();
        String mostExpensiveBorough = "";
        int mostExpensiveBoroughAvgPrice = 0;
        // adds the neighbourhood to those boroughs without one after looping through all the properties.
        for (AirbnbListing listing : allTheProperties) {
            if (!allBoroughs.contains(listing.getNeighbourhood())) {
                allBoroughs.add(listing.getNeighbourhood());
            }
        }

        for (String borough : allBoroughs) {
            pricesOfBorough.put(borough,0);
            noOfPropertiesPerBorough.put(borough,0);
        }
        // loops through the second hashmap to check for a match.
        for (AirbnbListing listing : allTheProperties) {
            for (HashMap.Entry<String, Integer> entry : noOfPropertiesPerBorough.entrySet()) {
                Integer value = entry.getValue();
                if (entry.getKey().equals(listing.getNeighbourhood())) {
                    entry.setValue(value+1);
                }
            }
            // finds the overall price by multiplying the no of nights with the price.
            for (HashMap.Entry<String, Integer> entry : pricesOfBorough.entrySet()) {
                Integer value = entry.getValue();
                if (entry.getKey().equals(listing.getNeighbourhood())) {
                    entry.setValue(value+(listing.getPrice() * listing.getMinimumNights()));
                }
            }
        }
        // loops through the first hashmap.
        for (HashMap.Entry<String, Integer> entry : pricesOfBorough.entrySet()) {
            Integer value = entry.getValue();
            String key = entry.getKey();
            for (HashMap.Entry<String, Integer> entryTwo : noOfPropertiesPerBorough.entrySet()) {
                Integer valueTwo = entryTwo.getValue();
                // checks if the two keys match, if they do then the if statement is executed.
                if (key.equals(entryTwo.getKey())) {
                    entry.setValue(value / valueTwo);
                }
            }
            if (entry.getValue() > mostExpensiveBoroughAvgPrice) {
                mostExpensiveBoroughAvgPrice = entry.getValue();
                mostExpensiveBorough = entry.getKey();
            }
        }
        return mostExpensiveBorough;
    }

    /**
     * Gets the name of the borough that has the maximum average reviews per month.
     * @return the name of the borough with the maximum average reviews pers month.
     */
    public String boroughWithMaxReviewsPerMonth()
    {
        HashMap<String,Double> overallReviews = new HashMap<>();
        HashMap<String, Integer> noOfPropertiesInBorough = new HashMap<>();
        int maxAvgPrice = 0;
        double currentReview;
        double existingReview;
        double avgReview;
        int existingCount;
        double maxAvgReview = 0;
        double sumOfReviews;
        int countOfProperties;
        for(AirbnbListing property : allTheProperties){
            currentNeighbourhood = property.getNeighbourhood();
            currentReview = property.getReviewsPerMonth();
            
            // Sum up reviews per month in each borough
            // For each property, check if record has been made in map
            // If record has been made, update it to add extra reviews
            // If record hasn't been made, create field and set its value
            if (overallReviews.containsKey(currentNeighbourhood))
            {
                existingReview = overallReviews.get(currentNeighbourhood);
                existingReview = existingReview + currentReview;
                overallReviews.replace(currentNeighbourhood, existingReview);
            }
            else{
                overallReviews.put(currentNeighbourhood, currentReview);
            }
            // this checks whether the record is there or not, if it is not then it is added.
            if (noOfPropertiesInBorough.containsKey(currentNeighbourhood))
            {
                noOfPropertiesInBorough.put(currentNeighbourhood, noOfPropertiesInBorough.get(currentNeighbourhood)+1);
            }
            else
            {
                noOfPropertiesInBorough.put(currentNeighbourhood, 1);
            }
        }
        // loops through the hashmap of overall reviews
        for (HashMap.Entry<String, Double> entry : overallReviews.entrySet()){
            currentNeighbourhood = entry.getKey();
            sumOfReviews = entry.getValue();
            countOfProperties = noOfPropertiesInBorough.get(currentNeighbourhood);
            avgReview = sumOfReviews/countOfProperties;
            if(avgReview > maxAvgReview ){
                maxAvgReview = avgReview;
                boroughWithMaxReviewsPerMonth = currentNeighbourhood;
            }
        }
        return boroughWithMaxReviewsPerMonth;
    }

    /**
     * Gets the number of reviews per property.
     * @return the number of reviews as a double.
     */
    public double getreviewsPerProperty()
    {
        return reviewsPerProperty;
    }

    /**
     * Gets the number of properties that are not private rooms.
     * @return the number of properties which are not private rooms.
     */
    public int getnotPrivateRooms()
    {
        return notPrivateRooms;
    }

    /**
     * Gets the name of the borough that has the most expensive property.
     * @return the name of the borough with the most expensive property in it.
     */
    public String getboroughWithMostexpensiveProperty()
    {
        return boroughWithMostexpensiveProperty;
    }

    /**
     * Gets the number of properties that have a host listing above 50.
     * @return the number of properties with a host listing above 50.
     */
    public int gethighestHostListing()
    {
        return highestHostListing;
    }

    /**
     * Gets the ID of the most expensive entire home.
     * @return the ID of the most expensive entire home.
     */
    public String getMostExpensiveEntireHomePropertyId()
    {
        return maxEntireHomePropertyId;
    }

}