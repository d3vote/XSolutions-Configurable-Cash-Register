package at.ac.fhcampuswien.xsolutions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class User {
    public String name;

    public String password;

    public String userName;
    public boolean isAdmin;
    static File usersListPath = new File("src/main/java/usersList.json");
    public static List<User> usersList = new ArrayList<>();    // UserList

    public User(){

    }

    public User(String name, boolean isAdmin, String userName, String password) throws IOException {
        this.name = name;
        this.isAdmin = isAdmin;
        this.userName = userName;
        this.password = password;
        intializeUsers();
    }

    public User(String name, boolean isAdmin, String userName) throws IOException {
        this.name = name;
        this.isAdmin = isAdmin;
        this.userName = userName;
        this.password = "admin";
        intializeUsers();
    }

    private void intializeUsers() throws IOException{
        usersList.add(this);        //Adding new User to UsersList
        userToJson();               //Converting it to JSON
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public static List<User> getUsersList(){
        return usersList;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
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
            usersList = objectMapper.readValue(usersListPath, typeFactory.constructCollectionType(List.class, User.class));
        }
    }


}