package at.ac.fhcampuswien.xsolutions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Tables{
    private int tableNumber;
    private static int count;
    private String serverName;
    private boolean serverSet = false;

    public Tables(){
        count++;
        this.tableNumber = count;

        new Receipt(this);
    }

    public static void setTablesCount(int count) {
        Tables.count = count;
    }

    public static int getCount(){
        return count;
    }

    public String getTableNumberAsString(){
        return "Tisch " + tableNumber;
    }

    public String getServersName() {
        return serverName;
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