public class Member extends User{
    public Member(String id, String name){
        super(id, name);
        setRole("Member");
    }
}
