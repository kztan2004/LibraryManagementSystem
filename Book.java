public class Book extends LibraryItem{
    public Book(String title, String id, String status){
        super(title, id, status);
        setType("Book");
    }
    
    @Override
    public double calculateLateFee(long dayLate){
        return dayLate * 0.2;
    }
}