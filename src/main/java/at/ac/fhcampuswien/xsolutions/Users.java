package at.ac.fhcampuswien.xsolutions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Users {
    private String name;
    private boolean adminRights;
    private int permissionsLevel;
    private static int id = 0;
    static File usersListPath = new File("src/main/java/usersList.json");
    public static List<Users> usersList = new ArrayList<>();    // UserList

    public Users(){

    }

    public Users(String name, boolean adminRights, int permissionsLevel){
        id++;
        this.name = name;
        this.adminRights = adminRights;
        this.permissionsLevel = permissionsLevel;
        usersList.add(this);        //Adding new User to UsersList
        userToJson();               //Converting it to JSON
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

    //Converting UsersList Objects to JSON
    public static void userToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            java.nio.file.Path path = Paths.get("src/main/java/usersList.json");
            objectMapper.writeValue(path.toFile(), usersList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void JSONtoUsersList() throws IOException {
        if (usersList != null){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            usersList = objectMapper.readValue(usersListPath, typeFactory.constructCollectionType(List.class, Users.class));
        }
    }


}