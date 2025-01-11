import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Loan{
    private String loanId;
    private String item1Id;
    private String item2Id;
    private String memberId;
    private String borrowDate;
    private String returnDate;
    
    public Loan(String loanId, String item1Id, String item2Id, String memberId, String borrowDate){
        this.loanId = loanId;
        this.item1Id = item1Id;
        this.item2Id = item2Id;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.returnDate = setReturnDate();
    }
    
    public String getLoanId(){return loanId;}
    
    public String getItem1Id(){return item1Id;}
    
    public String getItem2Id(){return item2Id;}
    
    public String getMemberId(){return memberId;}
    
    public String getBorrowDate(){return borrowDate;}
    
    public String getReturnDate(){return returnDate;}
    
    private String setReturnDate(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
            Date date = sdf.parse(borrowDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            return sdf.format(calendar.getTime());
        }catch(Exception e){
            System.out.println(e);
        }
        return "error";
    }
}