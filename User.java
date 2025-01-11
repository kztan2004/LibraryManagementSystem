public class User{
    private String id, name, role;
    
    public User(String id, String name){
        this.id = id;
        this.name = name;
    }
    
    public String getId(){return id;}
    
    public String getName(){return name;}
    
    public void setRole(String role){
        this.role = role;
    }
    
    public String getRole(){return role;}
}