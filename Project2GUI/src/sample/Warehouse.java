package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Stores warehouse data into an ArrayList of BikePart and allows the user to manipulate the ArrayList before writing to the Output File upon termination.
 *
 * @author Nicholas Campola
 */
public class Warehouse {

    protected ArrayList<BikePart> bikeParts;
    private String name;

    Warehouse(String vanName, String Filename) throws IOException {
        name = vanName;
        bikeParts = new ArrayList<BikePart>();
        readFile(Filename);
    }

    public String getVanName() {
        return name;
    }

    /**
     * Finds the index of a specific bike part.
     *
     * @param looksFor is the bike part in question
     * @returns counter is the index of the bike part
     */
    public int indexFinder(BikePart looksFor) {
        int counter = 0;
        for (BikePart b : bikeParts) {
            if (b.getPartName().equals(looksFor.getPartName()) && (b.getPartNumber()) == (looksFor.getPartNumber())) {
                return counter;
            } else {
                counter++;
            }
        }
        return counter;
    }

    public int findPart(String name) {
        int counter = 0;
        for (BikePart b : bikeParts) {
            if (b.getPartName().equals(name)) {
                //System.out.println( name + " part found");
                //System.out.println(counter);
                return counter;
            } else {
                counter++;
            }
        }
        //System.out.println(name + "part not found");
        //System.out.println(counter);
        return counter;

    }

    /**
     * Finds a part in the ArrayList or if the part does not exist based on part name and number.
     *
     * @param looksFor is the bike part in question
     * @returns boolean is an unassigned boolean that determines whether the part exists or not in the ArrayList
     */
    public boolean partFound(BikePart looksFor) {
        for (BikePart b : bikeParts) {
            if (b.getPartName().equals(looksFor.getPartName()) && (b.getPartNumber()) == (looksFor.getPartNumber())) {

                return true;
            }
        }

        return false;
    }

    /**
     * Sells a part in the array list if the bike part exists, then decrements the quantity if the quantity is not already zero.
     *
     * @param partNumber is the number of the bike part
     */
    public String sellPart(int partNumber) {
        for (BikePart bp : bikeParts) {
            if (partNumber == bp.getPartNumber()) {
                //System.out.println(partNumber + " " + bp.getPartNumber());
                if (bp.getQuantity() > 0) {
                    bp.setQuantity(bp.getQuantity() - 1);
                    if (bp.getOnSale() == true) {
                        return bp.getPartName() + " " + bp.getSalesPrice() + " On Sale " + "\n" + "Sold on " + LocalDateTime.now();
                    } else {
                        return bp.getPartName() + " " + bp.getSalesPrice() + " Not On Sale " + "\n" + "Sold on " + LocalDateTime.now();
                }
                } else {
                    return "This item is currently sold out";
                }
            }

        }
        return "This part does not exist in the current database";
    }

    /**
     * Prints out a specific bike part along with its price based on if it is on sale.
     */
    public String displayPart(String partName) {
        for (BikePart bp : bikeParts) {
            if (partName.compareTo(bp.getPartName()) == 0) {
                if (bp.getOnSale() == true) {
                    //System.out.printf("%s, %.2f", bp.getPartName(),bp.getSalesPrice());
                    //System.out.println("");
                    return bp.getPartName() + " " + bp.getSalesPrice() + " " + bp.getQuantity();
                } else {

                    //System.out.printf("%s, %.2f", bp.getPartName(), bp.getListPrice());
                    //System.out.println("");
                    return bp.getPartName() + " " + bp.getListPrice() + " " + bp.getQuantity();
                }
            }
        }
        return "This part is not in our current inventory.";
    }

    /**
     * Inserts a file to a scanner and puts all the information into the bikeParts ArrayList if the file is not empty.
     *
     * @param fileName is the name of the file that needs to be read to
     */
    public void readFile(String fileName) {
        FileInputStream file;
        Scanner in;

        try {
            file = new FileInputStream(fileName);

            in = new Scanner(file);
            //System.out.println("File successfully accessed.");
        } catch (FileNotFoundException e) {
            //if the file does not exist the system notifies the user and returns them to the main menu
            //System.out.println("This file does not exist in the current directory");
            //System.out.println("Sending back to menu screen");
            return;

        }


        //while the file has a line to read serves as a loop to get all items in the list
        while (in.hasNextLine()) {


            String line = in.nextLine();

            String[] pv = line.split(",");

            if (pv.length > 1) {
                //Separates each line into an array for the format of a BikePart
                String partName = pv[0];
                int partNumber = Integer.parseInt(pv[1]);
                float listPrice = Float.parseFloat(pv[2]);
                float salesPrice = Float.parseFloat(pv[3]);
                boolean onSale = Boolean.parseBoolean(pv[4]);
                int quantity = Integer.parseInt(pv[5]);


                BikePart part = new BikePart(partName, partNumber, listPrice, salesPrice, onSale, quantity);

                if (!partFound(part)) {
                    //if the part does not exist within the ArrayList it is added
                    bikeParts.add(part);
                } else {
                    //updates the bike part's prices, sale status, and adds quantity
                    int index = indexFinder(part);
                    bikeParts.get(index).setListPrice(part.getListPrice());
                    bikeParts.get(index).setSalesPrice(part.getSalesPrice());
                    bikeParts.get(index).setOnSale(part.getOnSale());
                    bikeParts.get(index).setQuantity(part.getQuantity() + bikeParts.get(index).getQuantity());
                }


            }
        }

    }

    /**
     * Adds a new bike part to the ArrayList or updates the bike part if there is already one within the ArrayList.
     *
     * @param partName  is the name of the bike part
     * @param partNumber is the serial number of the bike part
     * @param listPrice  is the original price of the part
     * @param salesPrice  is the discounted price of the part
     * @param onSale is the determinate of whether the part is on sale or not
     * @param quantity is the number of the same part currently available
     */
    public void enterPart(String partName, int partNumber,float listPrice,float salesPrice,boolean onSale,int quantity) {

        //Creates a new BikePart with the users input
        BikePart part = new BikePart(partName, partNumber, listPrice, salesPrice, onSale, quantity);

        if (!partFound(part)) {
            //if the part does not exist within the ArrayList it is added
            bikeParts.add(part);
        } else {
            //updates the bike part's prices, sale status, and adds quantity
            int index = indexFinder(part);
            bikeParts.get(index).setListPrice(part.getListPrice());
            bikeParts.get(index).setSalesPrice(part.getSalesPrice());
            bikeParts.get(index).setOnSale(part.getOnSale());
            bikeParts.get(index).setQuantity(part.getQuantity() + bikeParts.get(index).getQuantity());
        }


    }

    /**
     * Sorts the ArrayList in alphabetical or numerical order.
     *
     * @param type is the determinate of whether the sort is numerical or alphabetical
     */
    public void sort(int type) {


        if (bikeParts.size() > 1 ) {
            BikePart temp = bikeParts.get(0);
            //Numerical sorting
            //System.out.println(bikeParts.size());
            if (type == 0) {
                for (int decrementor = 0; decrementor < bikeParts.size(); decrementor++) {
                    for (int counter = 0; counter < bikeParts.size() - 1 - decrementor; counter++) {
                        if (bikeParts.get(counter).getPartNumber() > bikeParts.get(counter + 1).getPartNumber()) {
                            temp = bikeParts.get(counter);
                            bikeParts.set(counter, bikeParts.get(counter + 1));
                            bikeParts.set(counter + 1, temp);
                        }
                    }
                }
            }
            //Alphabetical sorting
            else {
                for (int decrementor = 0; decrementor < bikeParts.size(); decrementor++) {
                    for (int counter = 0; counter < bikeParts.size() - 1 - decrementor; counter++) {
                        if (bikeParts.get(counter).getPartName().compareToIgnoreCase(bikeParts.get(counter + 1).getPartName()) > 0) {
                            temp = bikeParts.get(counter);
                            bikeParts.set(counter, bikeParts.get(counter + 1));
                            bikeParts.set(counter + 1, temp);
                        }
                    }
                }

            }


            //prints out the ArrayList to show the new organization
            for (BikePart np : bikeParts) {
                np.printBikeParts();
            }

        }
    }


}





