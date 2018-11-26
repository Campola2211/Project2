package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AllWarehouse extends Warehouse{

    private ArrayList<Warehouse> Warehouses = new ArrayList<Warehouse>();

    /**
     * Constructor for AllWarehouses
     *
     * @param vanName the specific name of each Warehouse
     * @param fileName the txt document with all bikeParts written inside
     *
     * @throws IOException
     */
    AllWarehouse(String vanName, String fileName) throws IOException {
        super(vanName,fileName);
        Warehouses.add(new Warehouse(vanName,fileName));
    }

    /**
     * Gets the Warehouses array
     *
     * @return the Warehouses array
     */
    public ArrayList<Warehouse> getWarehouses(){
        return Warehouses;
    }

    public Warehouse getWarehouse(String name){
        int index = find(name);
        return Warehouses.get(index);
    }

    public void addWarehouse(String vanName,String fileName) throws IOException{
        Warehouses.add(new Warehouse(vanName,fileName));
    }
    /**
     * Finds a warehouse within the array by using the name attribute of the warehouse
     *
     * @param name is the name of the warehouse
     * @return counter is the index of the warehouse
     */
    public int find(String name) {
        int counter = 0;
        for (Warehouse w : Warehouses) {
            if (w.getVanName().equals(name)) {
                return counter;
            } else {
                counter++;
            }
        }
        //if the warehouse does not exist it returns -1
        return -1;
    }
    /**
     * Finds a BikePart within the BikePart array inside a Warehouse
     *
     *
     * @param wareHouseIndex is the warehouse that is being looked inside
     * @return counter is the index of the bike part
     */

    public int indexFinder(String name, int wareHouseIndex){
        int counter = 0;


        for(BikePart b: Warehouses.get(wareHouseIndex).bikeParts){
            if(b.getPartName().equals(name)){
                return counter;
            }
            else{
                counter++;
            }
        }
        return -1;
    }
    /**
     * Moves BikeParts from one warehouse to another
     *
     * @param fileName is a txt file that the method reads from
     * @return troubleshoot is the log of all activity that occurred during the method
     */
    public String transfer(String fileName) {
        FileInputStream file;
        Scanner in;
        String troubleshoot = "";

        try {
            file = new FileInputStream(fileName);

            in = new Scanner(file);
            troubleshoot += "File successfully accessed.\n";
        } catch (FileNotFoundException e) {
            //if the file does not exist the system notifies the user and returns them to the main menu
            return "This file does not exist in the current directory Sending back to menu screen";



        }

        String firstLine = in.nextLine();

        String[] transfers = firstLine.split(",");

        String source = transfers[0];
        String recipient = transfers[1];

        //finds the index for both warehouses within the warehouses array, if one is not found it will be assigned -1
        int sourceIndex = find(source);
        int recipientIndex = find(recipient);

        if(sourceIndex == -1 || recipientIndex == -1){
            return "One of the warehouses does not exist";
        }


        //while the file has a line to read serves as a loop to get all items in the list
        while (in.hasNextLine()) {


            String line = in.nextLine();

            String[] pv = line.split(",");

            if (pv.length > 1) {
                //Separates each line into an array for the format of a BikePart
                String partName = pv[0];
                int quantity = Integer.parseInt(pv[1]);

                troubleshoot += partName + " has  " + quantity +"\n";

                //if the part does not exist, it can not be transferred
                if(indexFinder(partName, sourceIndex) == -1) {
                    troubleshoot += partName + " is not within the source's inventory\n";
                }
                else{
                    //if the quantity is smaller than the request it can not be done for that part
                    if(Warehouses.get(sourceIndex).bikeParts.get(indexFinder(partName, sourceIndex)).getQuantity() - quantity < 0){
                        troubleshoot +="Transaction for " + partName + " cannot be performed because the actual quantity does not match the requested quantity \n";
                    }
                    else {
                        //if the part is within the recipient warehouse
                        if(indexFinder(partName, recipientIndex) != -1) {
                            Warehouses.get(sourceIndex).bikeParts.get(indexFinder(partName, sourceIndex)).setQuantity(Warehouses.get(sourceIndex).bikeParts.get((indexFinder(partName, sourceIndex))).getQuantity() - quantity);
                            Warehouses.get(recipientIndex).bikeParts.get((indexFinder(partName, recipientIndex))).setQuantity(quantity + Warehouses.get(recipientIndex).bikeParts.get(indexFinder(partName, recipientIndex)).getQuantity());
                        }
                        else{
                            Warehouses.get(sourceIndex).bikeParts.get(indexFinder(partName, sourceIndex)).setQuantity(Warehouses.get(sourceIndex).bikeParts.get(findPart(partName)).getQuantity() - quantity);
                            Warehouses.get(recipientIndex).bikeParts.add(Warehouses.get(sourceIndex).bikeParts.get(indexFinder(partName, sourceIndex)));
                            Warehouses.get(recipientIndex).bikeParts.get(indexFinder(partName, recipientIndex)).setQuantity(quantity);
                        }
                    }

                }
            }


        }
        return troubleshoot;
    }

    /**
     * sorts all warehouses within the warehouse database
     *
     * @param type is the choice of numeric or alphabetical sort
     */
    public void sortAll(int type){

        for(Warehouse w: Warehouses){
            System.out.println(w.getVanName());
            w.sort(type);
        }
    }

    /**
     * saves all warehouse inventories to separate txt documents for the next session
     */
    public void saveFiles() {
        for (Warehouse w : Warehouses) {
            String file = w.getVanName() + ".txt";
            //Once the user quits the program, the ArrayList is saved to the output file
            try {
                PrintWriter writer = new PrintWriter(new FileOutputStream(file, false));
                for (BikePart bike : w.bikeParts) {
                    writer.printf("%s,%d,%.2f,%.2f,", bike.getPartName(), bike.getPartNumber(), bike.getListPrice(), bike.getSalesPrice());
                    if (bike.getOnSale() == false) {
                        writer.print("false,");
                    } else {
                        writer.print("true,");
                    }
                    writer.println(bike.getQuantity());
                }
                writer.close();
            } catch (IOException e) {
                //System.out.println("file error!");
                e.printStackTrace();
            }
        }
    }

    /**
     * saves all warehouse names in one txt document to know which warehouses existed in the next session
     */
    public void saveInventory(){

            try {
                PrintWriter writer = new PrintWriter(new FileOutputStream("allWarehouses.txt", false));
                //prints the name of each warehouse to a txt file
                for(Warehouse w: Warehouses) {
                    String warehouseName = w.getVanName();
                    writer.print(warehouseName + ",");
                }
                    writer.close();
            } catch (IOException e) {
                //System.out.println("file error!");
                e.printStackTrace();
            }
        }

    /**
     * Calls the txt document that was created from a previous session to restore all previous warehouses
     *
     * @throws IOException
     */
    public void restoreDatabase() throws IOException {
        String data = "allWarehouses.txt";

        FileInputStream file;
        Scanner in;
        //reads the file created from the saveInventory function
        try {
            file = new FileInputStream(data);

            in = new Scanner(file);
            //System.out.println("File successfully accessed.");

            String line = in.nextLine();

            String[] warehouseList = line.split(",");

            System.out.println(warehouseList);

            //allwhs = new AllWarehouse(warehouseList[0], warehouseList[0] + ".txt");

            for (int i = 1; i < warehouseList.length; i++) {

                addWarehouse(warehouseList[i], (warehouseList[i] + ".txt"));
            }
        } catch (FileNotFoundException e) {
            //if the file does not exist the system returns them to the main menu
            //allwhs = new AllWarehouse("main", "main.txt");

            //allwhs.addWarehouse("main", "main.txt");
            return;

        }

    }

    /**
     * Calls saveFiles and saveInventory in one function
     */
    public void saveAll(){

        saveFiles();
        saveInventory();


    }

    /**
     * Measures the size of the array
     *
     * @return size is the size of the array of Warehouses
     */
    public int sizeArray(){
        int size = Warehouses.size();
        return size;
    }

}





