package at.ac.fhcampuswien.xsolutions;

import java.util.ArrayList;
import java.util.List;

import static at.ac.fhcampuswien.xsolutions.App.userToJson;

public class Users {
    private String name;
    private boolean adminRights;
    private int permissionsLevel;
    private static int id = 0;

    public static List<Users> usersList = new ArrayList<>(); // UserList

    public Users(){
        super();
    }

    public Users(String name, boolean adminRights, int permissionsLevel){
        id++;
        this.name = name;
        this.adminRights = adminRights;
        this.permissionsLevel = permissionsLevel;

        usersList.add(this); //Adding new User to UsersList
        userToJson(); //Converting it to JSON
    }

    public static List<Users> getUsersList(){
        return usersList;
    }

    public String getName() {
        return name;
    }

    public boolean isAdminRights() {
        return adminRights;
    }

    public int getPermissionsLevel() {
        return permissionsLevel;
    }
    public int getId() {
        return id;
    }


}