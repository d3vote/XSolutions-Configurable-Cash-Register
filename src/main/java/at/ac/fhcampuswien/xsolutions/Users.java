package at.ac.fhcampuswien.xsolutions;

public class Users {
    private String name;
    private boolean adminRights;
    private int permissionsLevel;
    private static int id = 0;

    public Users(){
        super();
    }
    public Users(String name, boolean adminRights, int permissionsLevel){
        id++;
        this.name = name;
        this.adminRights = adminRights;
        this.permissionsLevel = permissionsLevel;;
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