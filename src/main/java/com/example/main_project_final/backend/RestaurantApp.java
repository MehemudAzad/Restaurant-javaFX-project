package com.example.main_project_final.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RestaurantApp {

    private static final String INPUT_FILE_NAME = "restaurant.txt";
    private static final String INPUT_FILE_NAME2 = "menu.txt";

    /*------------------------
        DISPLAY FUNCTIONS
     -------------------------*/

    public static void displayFoodDetails(Food f){
        System.out.println("Restaurant ID: " + f.getRestaurantId());
        System.out.println("Food Name: " + f.getName());
        System.out.println("Price: " + f.getPrice());
        System.out.println("Category: " + f.getCategory());
        System.out.println("");
    }
    public static void displayRestaurantDetails(Restaurant r){
        System.out.println("Restaurant ID: " + r.getId());
        System.out.println("Restaurant Name: " + r.getName());
        System.out.println("Score: " + r.getScore());
        System.out.println("Price: " + r.getPrice());
        System.out.println("ZipCode: " + r.getZipcode());

        System.out.println("Categories: ");
        for (String category : r.getCategories()){
            System.out.println("\t\t" + category);
        }
        System.out.println("");
    }
    /*------------------------
        MAIN
     -------------------------*/
    public static void main(String[] args) throws IOException {
        //initialize
        RestaurantManager restaurantManager = new RestaurantManager();
        //declare scanner
        Scanner scanner = new Scanner(System.in);
        //declare reader
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));

        //input for restaurants
        while (true) {
            String line = br.readLine();
            if (line == null) break;

            String [] array = line.split(",", -1);
            if(array.length == 8){
                Restaurant r = new Restaurant(Integer.parseInt(array[0]), array[1], Double.parseDouble(array[2]), array[3], array[4], array[5], array[6], array[7]);
                restaurantManager.addRestaurant(r);
            }else if(array.length == 7){
                Restaurant r = new Restaurant(Integer.parseInt(array[0]), array[1], Double.parseDouble(array[2]), array[3], array[4], array[5], array[6]);
                restaurantManager.addRestaurant(r);
            }else if(array.length == 6){
                Restaurant r = new Restaurant(Integer.parseInt(array[0]), array[1], Double.parseDouble(array[2]), array[3], array[4], array[5]);
                restaurantManager.addRestaurant(r);
            }
        }
        br.close();

        //input for foods
        br = new BufferedReader(new FileReader(INPUT_FILE_NAME2));

        while (true) {
            String line = br.readLine();
            if (line == null) break;

            String[] array = line.split(",", -1);

            int restaurantId = Integer.parseInt(array[0]);
            String category = array[1];
            String foodName = array[2];


            if(array.length > 4){
                for(int i = 3; i < array.length - 1; i++){
                    foodName = foodName.concat("," + array[i]);
                }
            }
            double price = Double.parseDouble(array[array.length - 1]);
            Food f = new Food(restaurantId, category, foodName, price);

            for(int i = 0 ; i<restaurantManager.getRestaurants().size(); i++){
                if(restaurantManager.getRestaurants().get(i).getId() == Integer.parseInt(array[0])){
                    restaurantManager.getRestaurants().get(i).addMenuItems(f);
                }

            }

        }

        br.close();

        int choice;
        int option;
        //MAIN MENU
        do{
            System.out.println("Main menu: ");
            System.out.println("1) Search Restaurants");
            System.out.println("2) Search Food Items");
            System.out.println("3) Add Restaurant");
            System.out.println("4) Add Food Item to the Menu");
            System.out.println("5) Exit System");

            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> {
                    //search by restaurants
                    do {
                        String name;
                        String price;
                        String zipcode;
                        String category;
                        double lower, upper;
                        List<Restaurant> results;

                        System.out.println("Restaurant Searching Options:");
                        System.out.println("1) By Name");
                        System.out.println("2) By Score");
                        System.out.println("3) By Category");
                        System.out.println("4) By Price");
                        System.out.println("5) By Zip Code");
                        System.out.println("6) Different Category Wise List of Restaurants");
                        System.out.println("7) Back to Main Menu");


                        option = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (option) {
                            //name :
                            case 1 -> {
                                //name
                                System.out.println("Enter restaurant name: ");
                                name = scanner.nextLine();
                                Restaurant rest = restaurantManager.searchRestaurantByName(name);
                                //print it
                                displayRestaurantDetails(rest);
                            }
                            case 2 -> {
                                //score
                                System.out.println("Enter the lower bound: ");
                                lower = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Enter the upper bound: ");
                                upper = scanner.nextDouble();
                                scanner.nextLine();
                                results = restaurantManager.searchRestaurantsByScore(lower, upper);
                                //print it
                                if (results.isEmpty()) {
                                    System.out.println("No results found for the score range.");
                                } else {
                                    System.out.println("Restaurants within the score range are: ");
                                    for (Restaurant result : results) {
                                        displayRestaurantDetails(result);
                                    }
                                }
                            }
                            case 3 -> {
                                //category
                                System.out.println("Enter the category: ");
                                category = scanner.nextLine();
                                results = restaurantManager.searchRestaurantsByCategory(category);
                                //print it
                                if (results.isEmpty()) {
                                    System.out.println("No results found for '" + category+  "'.");
                                } else {
                                    System.out.println("Search results for '" + category + "':");
                                    for (Restaurant result : results) {
                                        displayRestaurantDetails(result);
                                    }
                                }
                            }
                            case 4 -> {
                                //by price
                                System.out.println("Enter the price: ");
                                price = scanner.nextLine();
                                results = restaurantManager.searchRestaurantsByPrice(price);
                                //print it
                                if (results.isEmpty()) {
                                    System.out.println("No results found for price '" + price + "'.");
                                } else {
                                    System.out.println("Search results for price '" + price + "':");
                                    for (Restaurant result : results) {
                                        displayRestaurantDetails(result);
                                    }
                                }
                            }
                            case 5 -> {
                                //by zipcode;
                                System.out.println("Enter the zipcode: ");
                                zipcode = scanner.nextLine();
                                results = restaurantManager.searchRestaurantsByZipcode(zipcode);
                                //print it
                                if (results.isEmpty()) {
                                    System.out.println("No results found for zipcode '" + zipcode + "'.");
                                } else {
                                    System.out.println("Search results for zipcode '" + zipcode + "':");
                                    for (Restaurant result : results) {
                                        displayRestaurantDetails(result);
                                    }
                                }
                            }
                            case 6 -> {
                                //different category wise
                                System.out.println("Displaying restaurants category wise: ");
                                restaurantManager.displayCategoryWiseNames();
                            }
                            case 7 ->
                                //back to main menu
                                    System.out.println("Going back to Main Menu.");
                        }
                    } while (option != 7);
                }
                //break statement for case 1 outer
                case 2 -> {
                    //search food items
                    do {
                        String foodName;
                        String resName;
                        String foodCategory;
                        double upperBoundPrice;
                        double lowerBoundPrice;
                        List<Food> results;

                        /*Food Item Searching Options:
                        1) By Name
                        2) By Name in a Given Restaurant
                        3) By Category
                        4) By Category in a Given Restaurant
                        5) By Price Range
                        6) By Price Range in a Given Restaurant
                        7) Costliest Food Item(s) on the Menu in a Given Restaurant
                        8) List of Restaurants and Total Food Item on the Menu
                        9) Back to Main Menu*/

                        System.out.println("Food Item Searching Options:");
                        System.out.println("1) By Name");
                        System.out.println("2) By Name in a Given Restaurant");
                        System.out.println("3) By Category");
                        System.out.println("4) By Category in a Given Restaurant");
                        System.out.println("5) By Price Range");
                        System.out.println("6) By Price Range in a Given Restaurant");
                        System.out.println("7) Costliest Food Item(s) on the Menu in a Given Restaurant");
                        System.out.println("8) List of Restaurants and Total Food Item on the Menu");
                        System.out.println("9) Back to Main Menu");

                        option = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (option) {//search food items by
                            case 1 -> {
                                //name of food
                                System.out.println("Enter name of food: ");
                                foodName = scanner.nextLine();
                                results = restaurantManager.searchFoodItemsByName(foodName);
                                if (results.isEmpty()) {
                                    System.out.println("No results found for '" + foodName + "'.");
                                } else {
                                    System.out.println("Search results for '" + foodName + "':");
                                    for (Food result : results) {
                                        displayFoodDetails(result);
                                    }
                                }
                            }
                            case 2 -> {
                                //name of a given restaurant and food
                                System.out.println("Enter name of food: ");
                                foodName = scanner.nextLine();
                                System.out.println("Enter name of restaurant: ");
                                resName = scanner.nextLine();
                                results = restaurantManager.searchFoodItemsByNameAndRest(foodName, resName);
                                //print
                                if (results.isEmpty()) {
                                    System.out.println("No such food item with this name on the menu of this restaurant");
                                } else {
                                    System.out.println("Search results for '" + foodName +"' in '"+resName +"':");
                                    for (Food result : results) {
                                        displayFoodDetails(result);
                                    }
                                }
                            }
                            case 3 -> {
                                //category of food
                                System.out.println("Enter name of category: ");
                                foodCategory = scanner.nextLine();
                                results = restaurantManager.searchFoodItemsByCategory(foodCategory);
                                //print
                                if (results.isEmpty()) {
                                    System.out.println("No such food item with this category");
                                } else {
                                    System.out.println("Search results for '" + foodCategory +"':");
                                    for (Food result : results) {
                                        displayFoodDetails(result);
                                    }
                                }
                            }
                            case 4 -> {
                                //category in a given restaurant
                                System.out.println("Enter name of category: ");
                                foodCategory = scanner.nextLine();
                                System.out.println("Enter name of restaurant: ");
                                resName = scanner.nextLine();
                                results = restaurantManager.searchFoodItemsByCategoryAndRest(foodCategory, resName);
                                //print
                                if (results.isEmpty()) {
                                    System.out.println("“No such food item with this category on the menu of this restaurant");
                                } else {
                                    System.out.println("Search results for '" + foodCategory +"':");
                                    for (Food result : results) {
                                        displayFoodDetails(result);
                                    }
                                }
                            }
                            case 5 -> {
                                //by price range of food
                                System.out.println("Enter lower bound  of price : ");
                                lowerBoundPrice = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Enter upper bound of price : ");
                                upperBoundPrice = scanner.nextDouble();
                                scanner.nextLine();

                                results = restaurantManager.searchFoodItemsByPrice(lowerBoundPrice, upperBoundPrice);
                                //print
                                if (results.isEmpty()) {
                                    System.out.println("No such food item with this price range.");
                                } else {
                                    System.out.println("Search results for the price range'" + lowerBoundPrice + "-" +upperBoundPrice +"':");
                                    for (Food result : results) {
                                        displayFoodDetails(result);
                                    }
                                }
                            }
                            case 6 -> {
                                //by price range in a given restaurant
                                System.out.println("Enter lower bound  of price : ");
                                lowerBoundPrice = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Enter upper bound of price : ");
                                upperBoundPrice = scanner.nextDouble();;
                                scanner.nextLine();
                                System.out.println("Enter name of restaurant: ");
                                resName = scanner.nextLine();
                                results = restaurantManager.searchFoodItemsByPriceAndRestName(lowerBoundPrice, upperBoundPrice, resName);
                                //print
                                if (results.isEmpty()) {
                                    System.out.println("No such food item with this price range on the menu of this restaurant");
                                } else {
                                    System.out.println("Search results for this price range in '" + resName +"':");
                                    for (Food result : results) {
                                        displayFoodDetails(result);
                                    }
                                }
                            }
                            case 7 -> {
                                //costliest foodItems on the menu in a given restaurant
                                System.out.println("Enter the name of Restaurant: ");
                                resName = scanner.nextLine();
                                results = restaurantManager.displayCostliestFoodItems(resName);

                                if (results.isEmpty()) {
                                    System.out.println("No such food item with this price range on the menu of this restaurant");
                                } else {
                                    System.out.println("The costliest food items in '" + resName +"' is/are:");
                                    for (Food result : results) {
                                        displayFoodDetails(result);
                                    }
                                }
                            }
                            case 8 ->{
                                //list of restaurants and their total food Items
                                System.out.println("The total food items on the menu for every restaurant");
                                restaurantManager.displayTotalNumberOfFoodItems();
                            }
                            case 9 ->
                                //return to main menu
                                    System.out.println("Going back to main menu.");
                        }
                    } while (option != 9);
                }
                case 3 -> {
                    //add restaurants
                    String resName;
                    double score;
                    String foodZipcode;
                    String price;
                    String category1, category2, category3;
//                    int id,string Name,double Score,string Price,string ZipCode,Category1,Category2,Category3
                    Restaurant r = null;
                    System.out.println("Enter the name of restaurant: ");
                    resName = scanner.nextLine();
                    //check if restaurant already exist
                    int resCheck =restaurantManager.getRestIdByName(resName);
                    if(resCheck != -1){
                        System.out.println("Restaurant already exists");
                        break;
                    }

                    System.out.println("Enter the score of restaurant: ");
                    score = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter the price of restaurant: ");
                    price = scanner.nextLine();
                    System.out.println("Enter the zipcode of restaurant: ");
                    foodZipcode = scanner.nextLine();
                    //take the number of categories and use if else to handle cases
                    int cnt;
                    System.out.println("Enter the number of categories: ");
                    cnt = scanner.nextInt();
                    scanner.nextLine();

                    if (cnt == 3) {
                        System.out.println("Enter the name of category1: ");
                        category1 = scanner.nextLine();
                        System.out.println("Enter the name of category2: ");
                        category2 = scanner.nextLine();
                        System.out.println("Enter the name of category3: ");
                        category3 = scanner.nextLine();

                        r = new Restaurant(restaurantManager.restaurantAdded + 1, resName, score, price, foodZipcode, category1, category2, category3);

                    } else if (cnt == 2) {
                        System.out.println("Enter the name of category1: ");
                        category1 = scanner.nextLine();
                        System.out.println("Enter the name of category2: ");
                        category2 = scanner.nextLine();

                        r = new Restaurant(restaurantManager.restaurantAdded + 1, resName, score, price, foodZipcode, category1, category2);

                    } else if (cnt == 1) {
                        System.out.println("Enter the name of category1: ");
                        category1 = scanner.nextLine();

                        r = new Restaurant(restaurantManager.restaurantAdded + 1, resName, score, price, foodZipcode, category1);

                    }
                    restaurantManager.addRestaurant(r);
                    System.out.println("Restaurant added successfully");
                }
                case 4 -> {
                    //add food item
                    int id;
                    String foodName;
                    String categoryName;
                    double foodPrice;
                    //RestaurantId,Category,Name,Price
                    Food f = null;
                    System.out.println("Enter the id of restaurant: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    //check if restaurant exists
                    boolean restCheck = restaurantManager.restaurantExistsById(id);
                    if(!restCheck){
                        System.out.println("Restaurant doesn't exist");
                        break;
                    }

                    System.out.println("Enter the name of food item: ");
                    foodName = scanner.nextLine();
                    //check if food already exists in that restaurant
                    boolean foodNameCheck = restaurantManager.foodExistsInRestaurant(id, foodName);
                    if(foodNameCheck){
                        System.out.println("Food item already exists in the restaurant");
                        break;
                    }

                    System.out.println("Enter the name of category: ");
                    categoryName = scanner.nextLine();
                    System.out.println("Enter the price of food item: ");
                    foodPrice = Double.parseDouble(scanner.nextLine());
                    f = new Food(id, categoryName, foodName, foodPrice);
                    restaurantManager.addFood(f);
                    System.out.println("Food item added successfully");
                }
                case 5 -> {
                    System.out.println("Exiting system.");
                    restaurantManager.writeRestaurantsToFile(INPUT_FILE_NAME);
                    restaurantManager.writeFoodsToFile(INPUT_FILE_NAME2);
                }

            }

        }while(choice!=5);

        scanner.close();
    }
}
