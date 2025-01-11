public class DVD extends LibraryItem{
    public DVD(String title, String id, String status){
        super(title, id, status);
        setType("DVD");
    }
    
    @Override
    public double calculateLateFee(long dayLate){
        return dayLate * 0.6;
    }
}
