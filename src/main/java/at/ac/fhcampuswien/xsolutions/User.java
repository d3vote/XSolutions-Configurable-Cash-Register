package at.ac.fhcampuswien.xsolutions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static at.ac.fhcampuswien.xsolutions.Configurator.getUsersListPath;

public class User {
    public String name;
    public String password;
    public String userName;
    public boolean isAdmin;
    public static List<User> usersList = new ArrayList<>();    // UserList

    public User(){
        super();
    }

    /**
     * Constructor for a User object.
     *
     * @param name    Full Name of the user
     * @param isAdmin        Is the user admin?
     * @param userName       Login "username" credentials
     * @param password       Login "password" credentials
     */
    public User(String name, boolean isAdmin, String userName, String password) throws IOException {
        this.name = name;
        this.isAdmin = isAdmin;
        this.userName = userName;
        this.password = password;
        initializeUsers();
    }

    public User(String name, boolean isAdmin, String userName) throws IOException {
        this.name = name;
        this.isAdmin = isAdmin;
        this.userName = userName;
        this.password = "admin";
        initializeUsers();
    }

    /**
     * User object is being added to a usersList within running Code,
     * but also saved in a JSON file.
     */
    private void initializeUsers() throws IOException{
        usersList.add(this);        //Adding new User to UsersList
        userToJson();               //Converting it to JSON
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setFullName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * userList (List of all parsed users) is being put in a single JSON File.
     */
    public static void userToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            java.nio.file.Path path = Paths.get(getUsersListPath().toURI());
            objectMapper.writeValue(path.toFile(), usersList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * userList is being recreated from a JSON File.
     */
    public static void JSONtoUsersList() throws IOException {
        if (usersList != null){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            usersList = objectMapper.readValue(getUsersListPath(), typeFactory.constructCollectionType(List.class, User.class));
        }
    }
}