public abstract class LibraryItem{ //abstract class
    private String title;
    private String id;
    private String status;
    private String type;
    
    public LibraryItem(String title, String id, String status){
        this.title = title;
        this.id = id;
        this.status = status;
    }
    
    public String getTitle(){return title;}
    
    public String getId(){return id;}
    
    public String getStatus(){return status;}
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getType(){return type;}
    
    public void onBorrow(String member){
        setStatus("BORROWED");
        logTransaction(member, "borrowed");
    }
    
    public void onReturn(String member){
        setStatus("AVAILABLE");
        logTransaction(member, "returned");
    }
    
    private void logTransaction(String member, String action){
        System.out.println("Member ID: "+member+" "+action+" ItemID: "+id);
    }
        
    public abstract double calculateLateFee(long dayLate);
}