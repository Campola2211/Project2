package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Launches the GUI
 *
 */
public class Main extends Application {
    /**
     * Sets up GUI to be launched
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Bike Part Distributor");
        primaryStage.setScene(new Scene(root, 600, 900));
        primaryStage.show();
    }

    /**
     * Saves all files after closing
     */
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        allwhs.saveAll();
        // Save file
    }


    public static AllWarehouse allwhs;

    /**
     * Restores databases before launching the GUI
     *
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //creates the object of allwhs with the mainwh since mainwh always has to exist
        allwhs = new AllWarehouse("mainwh","mainwh.txt");
        allwhs.restoreDatabase();
        launch(args);
    }
}
