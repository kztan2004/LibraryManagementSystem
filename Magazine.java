public class Magazine extends LibraryItem{
    public Magazine(String title, String id, String status){
        super(title, id, status);
        setType("Magazine");
    }
    
    @Override
    public double calculateLateFee(long dayLate){
        return dayLate * 0.4;
    }
}
