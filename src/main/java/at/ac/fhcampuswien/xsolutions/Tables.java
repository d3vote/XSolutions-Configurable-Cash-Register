package at.ac.fhcampuswien.xsolutions;

public class Tables {
    private static int tableNumber;
    public Tables(){
        tableNumber++;
        Bills bill = new Bills();
        System.out.println("Table: " + getTableNumber());
        bill.getBill();
    }

    public int getTableNumber(){
        return tableNumber;
    }

}