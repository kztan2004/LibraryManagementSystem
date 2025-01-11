import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LibrarySystem{
    public ArrayList<LibraryItem> itemList;
    public ArrayList<Loan> loanList;
    public ArrayList<User> memberList;
    public Date currentDate;
    SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
    
    public LibrarySystem() {
        try{
            currentDate = sdf.parse("1-1-2025");
        }catch(Exception e){
            System.out.println(e);
        }
        itemList = new ArrayList<LibraryItem>();
        loanList = new ArrayList<Loan>();
        memberList = new ArrayList<User>();
    }
    
    public long dayLate(String inputDate){
        try{
            Date date = sdf.parse(inputDate);
            long differenceInMilliSeconds = currentDate.getTime() - date.getTime();
            long differenceInDays = differenceInMilliSeconds / (1000 * 60 * 60 * 24);
            return differenceInDays;
        }catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }
    
    public void dateIncrement(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        currentDate = calendar.getTime();
    }
    
    public String getDate(){return sdf.format(currentDate);}
}