package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Responds to user input on the GUI
 *
 *
 */
public class Controller {

    @FXML
    private Tab addVanTab;

    @FXML
    private TextField enterVan;

    @FXML
    private Button vanButton;

    @FXML
    private TextArea displayVan;

    @FXML
    private Tab readTab;

    @FXML
    private TextArea displayRead;

    @FXML
    private TextField fileNameRead;

    @FXML
    private TextField wareHouseRead;

    @FXML
    private Button readButton;

    @FXML
    private Tab displayTab;

    @FXML
    private Button displayButton;

    @FXML
    private TextField bikePartDisplay;

    @FXML
    private TextArea displayDisplay;

    @FXML
    private TextField displayWarehouse;

    @FXML
    private Tab transferTab;

    @FXML
    private TextField transferFileField;

    @FXML
    private Button transferButton;

    @FXML
    private TextArea transferDisplay;

    @FXML
    private Tab sellTab;

    @FXML
    private TextField sellField;

    @FXML
    private TextField sellWarehouse;

    @FXML
    private TextArea displaySell;

    @FXML
    private Button sellButton;

    @FXML
    private Tab sortTab;

    @FXML
    private Button sortButton;

    @FXML
    private TextField sortOption;

    @FXML
    private TextField warehouseToSort;

    @FXML
    private TextField alphaOrNumeric;

    @FXML
    private TextArea displaySort;

    @FXML
    private TextField partNameField;

    @FXML
    private TextField partNumberField;

    @FXML
    private TextField listPriceField;

    @FXML
    private TextField salesPriceField;

    @FXML
    private TextField saleField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField warehouseEnterPart;

    @FXML
    private Button enterEnterButton;

    @FXML
    private TextArea enterDisplay;

    /**
     * Adds a new van to the warehouses if it does not already exist
     *
     * @param event
     * @throws IOException
     */

    @FXML
    void addVanEnter(ActionEvent event) throws IOException {
        String vanName = enterVan.getText();
        //if the warehouse does not exist, create the warehouse
        if (Main.allwhs.find(vanName) == -1) {
            Main.allwhs.addWarehouse(vanName, (vanName + ".txt"));
            displayVan.setText(vanName + " added \n");
            //shows the user that the warehouse was added with these parts if a warehouse document already exists
            for (int i = 0; i < Main.allwhs.getWarehouse(vanName).bikeParts.size(); i++) {
                displayVan.appendText(Main.allwhs.getWarehouse(vanName).bikeParts.get(i).printBikeParts());
                displayVan.appendText("\n");
            }
        }
        //otherwise, inform user that warehouse already exists
        else{
            displayVan.appendText("This warehouse already exists \n");
        }
    }

    /**
     * Displays a part in the specified warehouse
     *
     *
     * @param event
     */

    @FXML
    void displayEnterButton(ActionEvent event) {
        //if the warehouse exists look for the displayed part
        if (Main.allwhs.find(displayWarehouse.getText()) != -1) {
            Warehouse w = Main.allwhs.getWarehouse(displayWarehouse.getText());
            displayDisplay.appendText(w.displayPart(bikePartDisplay.getText()) + "\n");
        }
        else{
            displayDisplay.appendText("This warehouse does not exist \n");
        }

    }

    /**
     * Reads a text document and inputs all bikeparts into a specific warehouse
     *
     *
     * @param event
     */

    @FXML
    void readEnterButton(ActionEvent event) {
        String vanName = wareHouseRead.getText();
        String inventory = fileNameRead.getText();
        // if the warehouse exists read the file for the warehouse
        if(Main.allwhs.find(vanName)!=-1) {
            Main.allwhs.getWarehouse(vanName).readFile(inventory);
            displayRead.setText("Reading to " + vanName + "\n");
            // shows all parts that are now in the warehouse
            for (int i = 0; i < Main.allwhs.getWarehouse(vanName).bikeParts.size(); i++) {
                displayRead.appendText(Main.allwhs.getWarehouse(vanName).bikeParts.get(i).printBikeParts());
                displayRead.appendText("\n");
            }
        }
        else{
            displayRead.appendText("This warehouse does not exist yet");
        }
    }

    /**
     * Reads a text document and transfers bikeparts from one warehouse to another
     *
     *
     * @param event
     */
    @FXML
    void transferEnter(ActionEvent event) {
        transferDisplay.setText(Main.allwhs.transfer(transferFileField.getText()));
    }

    /**
     * removes a bike part from a specific warehouse
     *
     * @param event
     */
    @FXML
    void sellEnter(ActionEvent event) {
        // if the warehouse exists, sell the part
        if (Main.allwhs.find(sellWarehouse.getText()) != -1) {
            displaySell.appendText(Main.allwhs.getWarehouse(sellWarehouse.getText()).sellPart(Integer.parseInt(sellField.getText())) + "\n");
        }
        else{
            displaySell.appendText("This warehouse does not exist \n");
        }
    }

    /**
     * Sorts one or all warehouse numerically or alphabetically
     *
     * @param event
     */
    @FXML
    void sortEnter(ActionEvent event) {
        //sort only one warehouse
        if (sortOption.getText().equals("One")) {
            String vanName = warehouseToSort.getText();
            if (Main.allwhs.find(vanName) == -1) {
                displaySort.setText("This warehouse does not exist");
            } else {
                Main.allwhs.getWarehouse(vanName).sort(Integer.parseInt(alphaOrNumeric.getText()));
                Warehouse w = Main.allwhs.getWarehouse(vanName);
                displaySort.setText(vanName + "\n");
                for (int i = 0; i < w.bikeParts.size(); i++) {
                    displaySort.appendText(Main.allwhs.getWarehouse(vanName).bikeParts.get(i).printBikeParts());
                    displaySort.appendText("\n");
                }
            }
            //sort all warehouses
        } else if (sortOption.getText().equals("All")) {
            Main.allwhs.sortAll(Integer.parseInt(alphaOrNumeric.getText()));
            displaySort.setText("");
            // nested loop to sort through all warehouses and their bike parts
            for (int j = 0; j < Main.allwhs.sizeArray(); j++) {
                displaySort.appendText(Main.allwhs.getWarehouses().get(j).getVanName() + "\n");
                for (int i = 0; i < Main.allwhs.getWarehouses().get(j).bikeParts.size(); i++) {
                    displaySort.appendText(Main.allwhs.getWarehouses().get(j).bikeParts.get(i).printBikeParts());
                    displaySort.appendText("\n");
                }
            }


        }

    }


    /**
     * Manually enter a bike part into a specific warehouse
     *
     * @param event
     */
    @FXML
    void enterPartButton(ActionEvent event) {
            String partName = partNameField.getText();



            int partNumber = Integer.parseInt(partNumberField.getText());



            float listPrice = Float.parseFloat(listPriceField.getText());


            float salesPrice = Float.parseFloat(salesPriceField.getText());


            boolean onSale = Boolean.parseBoolean(saleField.getText());

            int quantity = Integer.parseInt(quantityField.getText());

            String vanName = warehouseEnterPart.getText();


            if(Main.allwhs.find(vanName)!=-1) {
                Main.allwhs.getWarehouse(vanName).enterPart(partName, partNumber, listPrice, salesPrice, onSale, quantity);
                enterDisplay.appendText(partName + " successfully added to " + vanName + "\n");
            }
            else{
                enterDisplay.appendText("This warehouse does not exist \n");

            }

    }

}
