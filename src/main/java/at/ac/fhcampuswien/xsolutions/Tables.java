package at.ac.fhcampuswien.xsolutions;


public class Tables{
    private int tableNumber;
    private static int count;
    private String serverName;
    private boolean serverSet = false;
    private int visitors;

    private boolean visitorsSet = false;

    /** Constructor for a Table object. <p>
     * static counter is being increased by 1 <p>
     * non-static counter = static counter <p>
     * Receipt created <p>
     */
    public Tables(){
        count++;
        this.tableNumber = count;

        new Receipt(this);
    }

    public static void setTablesCount(int count) {
        Tables.count = count;
    }

    public boolean isVisitorsSet() {
        return visitorsSet;
    }

    public void setVisitorsSet(boolean visitorsSet) {
        this.visitorsSet = visitorsSet;
    }

    public int getTableName(){
        return tableNumber;
    }

    public String getTableNumberAsString(){
        return "Tisch: " + tableNumber;
    }

    public String getServersName() {
        return serverName;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
        visitorsSet = true;
    }

    public void resetServer() {
        serverName = null;
        serverSet = false;
    }

    public void setServerName(String serverName) {
        if (serverName != null && !serverSet){
            serverSet = true;
            this.serverName = serverName;
        }
    }



}