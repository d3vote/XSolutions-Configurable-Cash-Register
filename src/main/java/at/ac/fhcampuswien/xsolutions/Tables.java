package at.ac.fhcampuswien.xsolutions;

public class Tables {
    private static int count;
    public static String[] tablesArray = new String[count];

    public Tables(){
        count++;
        Bills bill = new Bills();

        System.out.println("Table: " + getTableNumberAsInt());
        bill.getBill();
    }

    private static int getTableNumberAsInt(){
        return count;
    }

    public static String getTableNumberAsString(){
        return "Table: " + getTableNumberAsInt();
    }


    public static int getCount() {
        return count;
    }

}