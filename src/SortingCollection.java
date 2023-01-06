
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author lamhuynh
 */
public class SortingCollection extends Application {
    
    static File file = new File("Hotels.txt");
    static ArrayList<Hotel> hotels = new ArrayList<Hotel>();
    //Initialize list view to hold an array of hotel names
    ListView<String> nameList = new ListView<String>();
    
    //Method to check if a string can be converted to a number
    public static boolean isNumeric(String str) { 
        try {  
          Integer.parseInt(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }
    
    //Sort the list in star and followed by name order
    static Comparator<Hotel> NATURAL_ORDERING = new Comparator<Hotel>() {
        public int compare(Hotel hotel1, Hotel hotel2) {
            int value = hotel2.getStar().compareTo(hotel1.getStar());
            if (value == 0) {
                return hotel1.getName().compareTo(hotel2.getName());
            } else {
                return value;
            }
        }
    };
    
    //Sort the list in the lowest price order
    static Comparator<Hotel> LOWEST_PRICE = new Comparator<Hotel>() {
        public int compare(Hotel hotel1, Hotel hotel2) {
            return hotel1.getPrice().compareTo(hotel2.getPrice());
        }
    };
    
    //Main method to execute the application
    public static void main(String[] args) {
        
        System.out.println("The file exists? " + file.exists());
        //Read the hotels.txt file
        try(BufferedReader input = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = input.readLine()) != null) {
                String[] tokens = line.split(" ");
                for (int i = 0; i < tokens.length; i++) {
                    //check if the token is a number
                    if (isNumeric(tokens[i])) {
                        //if it is a number, we concatenate all the tokens before that and store in the hotelName variable
                        String hotelName = "";
                        for (int j = 0; j < i; j++) {
                            hotelName += tokens[j] + " ";
                        }
                        //Add each hotel object to the array list named hotels
                        hotels.add(new Hotel(hotelName, Integer.parseInt(tokens[i]), Double.parseDouble(tokens[i+2])));
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        //Launch the application
        launch();
    }
    
    //Method to render the whole arraylist in the list view
    public void renderListView() {
        ArrayList<String> onlyName = new ArrayList<>();
        //Convert to an array list that holds only hotel name
        for (Hotel x : hotels) {
            onlyName.add(x.getName());
        }
        //Add the array list to the list view
        nameList.getItems().setAll(onlyName);
    }
    
    //Method to start the application
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hotel Sort Engine");
        //Inititalize the list view
        renderListView();
        Text text = new Text("List of Hotels");
        //Button to sort in natural ordering
        Button naturalOrdering = new Button("Natural Ordering");
        //Button to sort in lowest price ordering
        Button lowestPrice = new Button("Lowest Price");
        //Button to save the list view in the .txt file
        Button saveButton = new Button("Save");
        GridPane grid = new GridPane();
        VBox vBox = new VBox(20, naturalOrdering, lowestPrice, saveButton);
        vBox.setPadding(new Insets(40));
        grid.add(text, 1, 1);
        grid.add(nameList, 1, 2);
        grid.add(vBox, 2, 2);
        Scene scene = new Scene(grid,450,450);
        
        naturalOrdering.setOnAction(e -> { 
            Collections.sort(hotels, NATURAL_ORDERING);
            //Re-render the list view
            renderListView();
        });
        
        lowestPrice.setOnAction(e -> { 
            Collections.sort(hotels, LOWEST_PRICE);
            //Re-render the list view
            renderListView();
            
        });
        
        saveButton.setOnAction(e -> { 
            File file = new File("sortingfile.txt");
            try(FileWriter output = new FileWriter(file, false)) {
                output.write("");
                //Write the sorted list to the sortingfile.txt file
                List<String> sortedList = nameList.getItems();
                for (String element : sortedList) {
                    output.append(element + "\n");
                }
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
            }
            
        });
        
        stage.setScene(scene);
        stage.show();
    }

}
