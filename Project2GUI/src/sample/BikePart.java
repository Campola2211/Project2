package sample;

import java.util.Scanner;

/**
 * Stores characteristics of a bike part.
 *
 *
 *
 * @author Nicholas Campola
 */
public class BikePart {

    private String partName;
    private int partNumber;
    private float listPrice;
    private float salesPrice;
    private boolean onSale;
    private int quantity;


    /**
     * Accesses the setter methods to store each variable in the class.
     *
     * @param partName  is the name of the bike part
     * @param partNumber is the serial number of the bike part
     * @param listPrice  is the original price of the part
     * @param salesPrice  is the discounted price of the part
     * @param onSale is the determinate of whether the part is on sale or not
     * @param quantity is the number of the same part currently available
     */
    BikePart(String partName, int partNumber, float listPrice, float salesPrice, boolean onSale, int quantity){
        setPartName(partName);
        setPartNumber(partNumber);
        setListPrice(listPrice);
        setSalesPrice(salesPrice);
        setOnSale(onSale);
        setQuantity(quantity);
    }
    /**
     * Prompts the user to input each variable than access the setter methods to store each variable.
     * @param in is a Scanner that the user writes to
     */
    BikePart(Scanner in){
        System.out.println("Enter Part Name: ");
        String partName = in.next();
        setPartName(partName);

        System.out.println("Enter Part Number: ");
        int partNumber = in.nextInt();
        setPartNumber(partNumber);

        System.out.println("Enter List Price: ");
        float listPrice = in.nextFloat();
        setListPrice(listPrice);

        System.out.println("Enter Sales Price: ");
        float salesPrice = in.nextFloat();
        setSalesPrice(salesPrice);

        System.out.println("The item is on sale.(true/false): ");
        boolean onSale = in.nextBoolean();
        setOnSale(onSale);

        System.out.println("Enter Quantity: ");
        int quantity = in.nextInt();
        setQuantity(quantity);
    }


    /**
     * Prints out all variables within BikePart in a formated print style.
     *
     */
    public String printBikeParts(){

        if(onSale == true){

            return(partName + ", " + partNumber + ", " + listPrice + ", " + salesPrice + ", true, " + quantity);

        }
        else{
            return(partName + ", " + partNumber + ", " + listPrice + ", " + salesPrice + ", false, " + quantity);

        }

    }

    /**
     * Sets the partName variable.
     *
     * @param partNameInput  is the argument used to set partName
     */
    public void setPartName(String partNameInput){

        partName = partNameInput;

    }
    /**
     * Sets the partNumber variable.
     *
     * @param partNumberInput  is the argument used to set partNumber
     */
    public void setPartNumber(int partNumberInput){

        partNumber = partNumberInput;

    }
    /**
     * Sets the listPrice variable.
     *
     * @param listPriceInput  is the argument used to set listPrice
     */
    public void setListPrice(float listPriceInput){

        listPrice = listPriceInput;

    }
    /**
     * Sets the salesPrice variable.
     *
     * @param salesPriceInput  is the argument used to set salesPrice
     */
    public void setSalesPrice(float salesPriceInput){

        salesPrice = salesPriceInput;


    }
    /**
     * Sets the onSale variable.
     *
     * @param onSaleInput  is the argument used to set onSale
     */
    public void setOnSale(boolean onSaleInput){

        onSale = onSaleInput;

    }
    /**
     * Sets the quantity variable.
     *
     * @param quantityInput  is the argument used to set quantity
     */
    public void setQuantity(int quantityInput){
        quantity = quantityInput;
    }
    /**
     * Gets the partName variable.
     *
     * @returns partName  is the name of the bike part
     */
    public String getPartName(){

        return partName;

    }

    /**
     * Gets the partNumber variable.
     *
     * @returns partNumber is the serial number of the bike part
     */
    public int getPartNumber(){

        return partNumber;


    }

    /**
     * Gets the listPrice variable.
     *
     * @returns listPrice is the original price of the part
     */
    public float getListPrice (){

        return listPrice;

    }
    /**
     * Gets the salesPrice variable.
     *
     *@returns salesPrice is the discounted price of the part
     */
    public float getSalesPrice (){

        return salesPrice;

    }


    /**
     * Gets the getOnSale variable.
     *
     *@returns OnSale is the determinate of whether the part is on sale or not
     */
    public boolean getOnSale (){

        return onSale;

    }
    /**
     * Gets the quantity variable.
     *
     * @returns quantity is the number of the same part currently available
     */
    public int getQuantity (){

        return quantity;

    }





}