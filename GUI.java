import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private boolean memberMode = true;
    private JFrame frame;
    private DefaultTableModel ItemTableModel, MemberTableModel, LoanTableModel;
    private JButton addItemBtn, remItemBtn, addMemberBtn, remMemberBtn, borrowBtn, returnBtn, dateBtn;
    private JPanel panel1, panel2, panel3;
    private LibrarySystem librarySystem;
    private int itemTableNum = 0;
    private int memberTableNum = 0;
    private int loanTableNum = 0;
    private String[][] itemInit = {{"001", "Harry Potter", "Book"}, {"002", "The Lord of the Rings", "Book"}, {"003", "The Alchemist", "Magazine"}, {"004", "The Da Vinci Code", "DVD"}};
    private String[][] memberInit = {{"001", "Zach", "Librarian"}, {"002", "Charles", "Member"}, {"003", "Joey", "Member"}, {"004", "Owen", "Member"}};

    public GUI() {
        int choice = JOptionPane.showOptionDialog(
            null,
            "Select Role:",
            "Library Management System",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            new Object[]{"Member ", "Librarian"},
            ""
        );

        if (choice == 0) {
            memberMode = true;
        } else {
            memberMode = false;
        }

        frame = new JFrame();
        if (memberMode) {
            frame.setTitle("Library Management System - Member");
            frame.setLayout(new GridLayout(2, 2, 0, 9));
            frame.setSize(900, 300);
        } else {
            frame.setTitle("Library Management System - Librarian");
            frame.setLayout(new GridLayout(3, 2, 0, 9));
            frame.setSize(1100, 400);
        }

        librarySystem = new LibrarySystem();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        view();
    }

    private void init() {
        for (String[] items : itemInit) {
            LibraryItem item = null;
            switch (items[2]) {
                case "Book":
                    item = new Book(items[1], items[0], "AVAILABLE");
                    break;
                case "Magazine":
                    item = new Magazine(items[1], items[0], "AVAILABLE");
                    break;
                case "DVD":
                    item = new DVD(items[1], items[0], "AVAILABLE");
                    break;
                default:
                    break;
            }
            librarySystem.itemList.add(item);
            ItemTableModel.addRow(new Object[]{item.getId(), item.getTitle(), item.getType(), item.getStatus()});
            itemTableNum++;
        }

        for (String[] members : memberInit) {
            User user = null;
            switch (members[2]) {
                case "Member":
                    user = new Member(members[0], members[1]);
                    break;
                case "Librarian":
                    user = new Librarian(members[0], members[1]);
                    break;
                default:
                    break;
            }
            librarySystem.memberList.add(user);
            MemberTableModel.addRow(new Object[]{user.getId(), user.getName(), user.getRole()});
            memberTableNum++;
        }
    }

    private void view() {
        ItemTableModel = new DefaultTableModel(new String[]{"Item ID", "Title", "Type", "Status"}, 0);
        JTable ItemTable = new JTable(ItemTableModel);
        MemberTableModel = new DefaultTableModel(new String[]{"User ID", "User Name", "User Role"}, 0);
        JTable MemberTable = new JTable(MemberTableModel);
        LoanTableModel = new DefaultTableModel(new String[]{"Loan ID", "Member ID", "Item 1 ID", "Item 2 ID", "Borrow Date", "Due Date"}, 0);
        JTable LoanTable = new JTable(LoanTableModel);

        addItemBtn = new JButton("Add Item");
        remItemBtn = new JButton("Remove Item");
        addMemberBtn = new JButton("Add Member");
        remMemberBtn = new JButton("Remove Member");
        borrowBtn = new JButton("Borrow Item");
        returnBtn = new JButton("Return Item");
        dateBtn = new JButton(">>>");

        JLabel dateLabel = new JLabel("Date: "+librarySystem.getDate(), SwingConstants.CENTER);
        dateLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        init();
        
        addItemBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean status = true;
                JTextField idField = new JTextField(10);
                JTextField titleField = new JTextField(10);
                idField.setText("");
                titleField.setText("");
                JRadioButton bookRadio = new JRadioButton("Book");
                JRadioButton magazineRadio = new JRadioButton("Magazine");
                JRadioButton dvdRadio = new JRadioButton("DVD");
                ButtonGroup group = new ButtonGroup();
                group.add(bookRadio);
                group.add(magazineRadio);
                group.add(dvdRadio);
                bookRadio.setSelected(true);
                
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(5, 2));
                panel.add(new JLabel("Item ID   :"));
                panel.add(idField);
                panel.add(new JLabel("Item Title:"));
                panel.add(titleField);
                panel.add(new JLabel("Item Type:"));
                panel.add(bookRadio);
                panel.add(new JLabel(""));
                panel.add(magazineRadio);
                panel.add(new JLabel(""));
                panel.add(dvdRadio);

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        panel,
                        "Add Item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                
                if (result == JOptionPane.OK_OPTION) {
                    String id = idField.getText();
                    String name = titleField.getText();
                    for(LibraryItem i : librarySystem.itemList){
                        if(i.getId().equals(idField.getText())){
                            status = false;
                            break;
                        }
                    }
                    if(status && !(idField.getText().trim().isEmpty()) && !(titleField.getText().trim().isEmpty())){
                        LibraryItem item = null;
                        if (bookRadio.isSelected()) {
                            item = new Book(name, id, "AVAILABLE");
                        } else if (magazineRadio.isSelected()) {
                            item = new Magazine(name, id, "AVAILABLE");
                        } else if (dvdRadio.isSelected()) {
                            item = new DVD(name, id, "AVAILABLE");
                        }
                        librarySystem.itemList.add(item);
                        ItemTableModel.addRow(new Object[]{item.getId(), item.getTitle(), item.getType(), item.getStatus()});
                        itemTableNum++;
                        JOptionPane.showMessageDialog(frame, "Item ID: " + id + " Item Name: " + name + " is added");
                        System.out.println("Item ID: " + id + " Item Name: " + name + " is added");
                    }else{
                        JOptionPane.showMessageDialog(frame, "Item Adding Failed!");
                        System.out.println("Item Adding Failed!");
                    }
                }
            }
        });
        
        remItemBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean status = false;
                JTextField idField = new JTextField(10);
                idField.setText("");
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(1, 2));
                panel.add(new JLabel("Item ID:"));
                panel.add(idField);

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        panel,
                        "Remove Item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                
                if (result == JOptionPane.OK_OPTION) {
                    String id = idField.getText();
                    for(LibraryItem i : librarySystem.itemList){
                        if(i.getId().equals(id)){
                            librarySystem.itemList.remove(i);
                            status = true;
                            break;
                        }
                    }
                    for(int i = 0; i < itemTableNum; i++){
                        if(ItemTableModel.getValueAt(i, 0).equals(id)){
                            ItemTableModel.removeRow(i);
                            itemTableNum--;
                            status = true;
                            break;
                        }
                    }
                    if (status){
                        JOptionPane.showMessageDialog(frame, "Item ID: " + id + " removed successful");
                        System.out.println("Item ID: " + id + " removed successful");
                    }else{
                        JOptionPane.showMessageDialog(frame, "Item Remove Failed!");
                        System.out.println("Item Remove Failed!");
                    }
                }
            }
        });
        
        addMemberBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean status = true;
                JTextField idField = new JTextField(10);
                JTextField titleField = new JTextField(10);
                idField.setText("");
                titleField.setText("");
                JRadioButton memberRadio = new JRadioButton("Member");
                JRadioButton librarianRadio = new JRadioButton("Librarian");
                ButtonGroup group = new ButtonGroup();
                group.add(memberRadio);
                group.add(librarianRadio);
                memberRadio.setSelected(true);
                
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(4, 2));
                panel.add(new JLabel("User ID  :"));
                panel.add(idField);
                panel.add(new JLabel("User Name:"));
                panel.add(titleField);
                panel.add(new JLabel("User Role:"));
                panel.add(memberRadio);
                panel.add(new JLabel(""));
                panel.add(librarianRadio);
                
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        panel,
                        "Add Member",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                
                if (result == JOptionPane.OK_OPTION) {
                    String id = idField.getText();
                    String name = titleField.getText();
                    for(User i : librarySystem.memberList){
                        if(i.getId().equals(idField.getText())){
                            status = false;
                            break;
                        }
                    }
                    if(status && !(idField.getText().trim().isEmpty()) && !(titleField.getText().trim().isEmpty())){
                        User user = null;
                        if (memberRadio.isSelected()) {
                            user = new Member(id, name);
                        } else if (librarianRadio.isSelected()) {
                            user = new Librarian(id, name);
                        }
                        librarySystem.memberList.add(user);
                        MemberTableModel.addRow(new Object[]{user.getId(), user.getName(), user.getRole()});
                        memberTableNum++;
                        JOptionPane.showMessageDialog(frame, "Member ID: " + id + " Member Name: " + name + " is added");
                        System.out.println("Member ID: " + id + " Member Name: " + name + " is added");
                    }else{
                        JOptionPane.showMessageDialog(frame, "Member Adding Failed!");
                        System.out.println("Member Adding Failed!");
                    }
                }
            }
        });
        
        remMemberBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean status = false;
                JTextField idField = new JTextField(10);
                idField.setText("");
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(1, 2));
                panel.add(new JLabel("User ID:"));
                panel.add(idField);

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        panel,
                        "Remove Member",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                
                if (result == JOptionPane.OK_OPTION) {
                    String id = idField.getText();
                    for(User i : librarySystem.memberList){
                        if(i.getId().equals(id)){
                            librarySystem.memberList.remove(i);
                            status = true;
                            break;
                        }
                    }
                    for(int i = 0; i < memberTableNum; i++){
                        if(MemberTableModel.getValueAt(i, 0).equals(id)){
                            MemberTableModel.removeRow(i);
                            memberTableNum--;
                            status = true;
                            break;
                        }
                    }
                    if (status){
                        JOptionPane.showMessageDialog(frame, "Member ID: " + id + " removed successful");
                        System.out.println("Member ID: " + id + " removed successful");
                    }else{
                        JOptionPane.showMessageDialog(frame, "Member Remove Failed!");
                        System.out.println("Member Remove Failed!");
                    }
                }
            }
        });
        
        borrowBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean status = true;
                boolean memberStatus = false;
                boolean item1Status = false;
                boolean item2Status = false;
                JTextField loanField = new JTextField(10);
                JTextField memberField = new JTextField(10);
                JTextField item1Field = new JTextField(10);
                JTextField item2Field = new JTextField(10);
                loanField.setText("");
                memberField.setText("");
                item1Field.setText("");
                item2Field.setText("");
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(4, 2));
                panel.add(new JLabel("Loan ID:"));
                panel.add(loanField);
                panel.add(new JLabel("Member ID:"));
                panel.add(memberField);
                panel.add(new JLabel("Item 1 ID:"));
                panel.add(item1Field);
                panel.add(new JLabel("Item 2 ID:"));
                panel.add(item2Field);
                
                
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        panel,
                        "Borrow Item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                
                if (result == JOptionPane.OK_OPTION) {
                    String loanId = loanField.getText();
                    String memberId = memberField.getText();
                    String item1Id = item1Field.getText();
                    String item2Id = item2Field.getText();
                    for(Loan i : librarySystem.loanList){
                        if(i.getLoanId().equals(loanId)){
                            status = false;
                            break;
                        }
                    }
                    for(LibraryItem i : librarySystem.itemList){
                        if(i.getId().equals(item1Id)){
                            if(i.getStatus().equals("AVAILABLE")){
                                item1Status = true;
                            }
                            break;
                        }
                    }
                    for(LibraryItem i : librarySystem.itemList){
                        if(i.getId().equals(item2Id)){
                            if(i.getStatus().equals("AVAILABLE")){
                                item2Status = true;
                            }
                            break;
                        }
                    }
                    for(User i : librarySystem.memberList){
                        if(i.getId().equals(memberId)){
                            memberStatus = true;
                            break;
                        }
                    }
                    if(item1Id.equals(item2Id)){
                        status = false;
                    }
                    if(status && (item1Status || item2Status) && memberStatus && !(loanField.getText().trim().isEmpty())){
                        if(item1Status){
                            for(LibraryItem i : librarySystem.itemList){
                                if(i.getId().equals(item1Id)){
                                    i.onBorrow(memberId);
                                    break;
                                }
                            }
                            for(int i = 0; i < itemTableNum; i++){
                                if(ItemTableModel.getValueAt(i, 0).equals(item1Id)){
                                    ItemTableModel.setValueAt("BORROWED", i, 3);
                                    break;
                                }
                            }
                        }
                        if(item2Status){
                            for(LibraryItem i : librarySystem.itemList){
                                if(i.getId().equals(item2Id)){
                                    i.onBorrow(memberId);
                                    break;
                                }
                            }
                            for(int i = 0; i < itemTableNum; i++){
                                if(ItemTableModel.getValueAt(i, 0).equals(item2Id)){
                                    ItemTableModel.setValueAt("BORROWED", i, 3);
                                    break;
                                }
                            }
                        }
                        Loan loan = new Loan(loanId, item1Id, item2Id, memberId, librarySystem.getDate());
                        librarySystem.loanList.add(loan);
                        LoanTableModel.addRow(new Object[]{loan.getLoanId(), loan.getMemberId(), loan.getItem1Id(), loan.getItem2Id(), loan.getBorrowDate(), loan.getReturnDate()});
                        loanTableNum++;
                        if(item1Status && item2Status){
                            JOptionPane.showMessageDialog(frame, "Member ID: "+memberId+" successfully borrowed Item ID: "+item1Id +" & "+ item2Id);
                            System.out.println("Member ID: "+memberId+" successfully borrowed Item ID: "+item1Id +" & "+ item2Id);
                        }else if(item1Status){
                            JOptionPane.showMessageDialog(frame, "Member ID: "+memberId+" successfully borrowed Item ID: "+item1Id);
                            System.out.println("Member ID: "+memberId+" successfully borrowed Item ID: "+item1Id);
                        }else if(item2Status){
                            JOptionPane.showMessageDialog(frame, "Member ID: "+memberId+" successfully borrowed Item ID: "+item2Id);
                            System.out.println("Member ID: "+memberId+" successfully borrowed Item ID: "+item2Id);
                        }
                    }else{
                        JOptionPane.showMessageDialog(frame, "Item Borrow Failed!");
                        System.out.println("Item Borrow Failed!");
                    }
                }
            }
        });
        
        returnBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean status = false;
                boolean item1Status = false;
                boolean item2Status = false;
                long dayLate = 0;
                double totalLateFee = 0;
                String memberId = "";
                String item1Id = "";
                String item2Id = "";
                JTextField idField = new JTextField(10);
                idField.setText("");
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(1, 2));
                panel.add(new JLabel("Loan ID:"));
                panel.add(idField);

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        panel,
                        "Return Item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                
                if (result == JOptionPane.OK_OPTION) {
                    String id = idField.getText();
                    for(Loan i : librarySystem.loanList){
                        if(i.getLoanId().equals(id)){
                            dayLate = librarySystem.dayLate(i.getReturnDate());
                            memberId = i.getMemberId();
                            item1Id = i.getItem1Id();
                            item2Id = i.getItem2Id();
                            item1Status = item1Id != "";
                            item2Status = item2Id != "";
                            librarySystem.loanList.remove(i);
                            status = true;
                            break;
                        }
                    }
                    if (status){
                        for(int i = 0; i < loanTableNum; i++){
                            if(LoanTableModel.getValueAt(i, 0).equals(id)){
                                LoanTableModel.removeRow(i);
                                loanTableNum--;
                                break;
                            }
                        }
                        if(item1Status){
                            for(LibraryItem i : librarySystem.itemList){
                                if(i.getId().equals(item1Id)){
                                    i.onReturn(memberId);
                                    if(dayLate > 0){
                                        totalLateFee += i.calculateLateFee(dayLate);
                                    }
                                    break;
                                }
                            }
                            for(int i = 0; i < itemTableNum; i++){
                                if(ItemTableModel.getValueAt(i, 0).equals(item1Id)){
                                    ItemTableModel.setValueAt("AVAILABLE", i, 3);
                                    break;
                                }
                            }
                        }
                        if(item2Status){
                            for(LibraryItem i : librarySystem.itemList){
                                if(i.getId().equals(item2Id)){
                                    i.onReturn(memberId);
                                    if(dayLate > 0){
                                        totalLateFee += i.calculateLateFee(dayLate);
                                    }
                                    break;
                                }
                            }
                            for(int i = 0; i < itemTableNum; i++){
                                if(ItemTableModel.getValueAt(i, 0).equals(item2Id)){
                                    ItemTableModel.setValueAt("AVAILABLE", i, 3);
                                    break;
                                }
                            }
                        }
                        if(dayLate > 0){
                            JOptionPane.showMessageDialog(frame, "Late Fee: $"+String.format("%.2f",totalLateFee));
                            System.out.println("Late Fee: $"+String.format("%.2f",totalLateFee));
                        }
                        if(item1Status && item2Status){
                            JOptionPane.showMessageDialog(frame, "Member ID: "+memberId+" successfully returned Item ID: "+item1Id +" & "+ item2Id);
                            System.out.println("Member ID: "+memberId+" successfully returned Item ID: "+item1Id +" & "+ item2Id);
                        }else if(item1Status){
                            JOptionPane.showMessageDialog(frame, "Member ID: "+memberId+" successfully returned Item ID: "+item1Id);
                            System.out.println("Member ID: "+memberId+" successfully returned Item ID: "+item1Id);
                        }else if(item2Status){
                            JOptionPane.showMessageDialog(frame, "Member ID: "+memberId+" successfully returned Item ID: "+item2Id);
                            System.out.println("Member ID: "+memberId+" successfully returned Item ID: "+item2Id);
                        }
                    }else{
                        JOptionPane.showMessageDialog(frame, "Return Failed!");
                        System.out.println("Return Failed!");
                    }
                }
            }
        });
        
        dateBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                librarySystem.dateIncrement();
                dateLabel.setText("Date: "+librarySystem.getDate());
            }
        });

        panel1 = new JPanel(new GridLayout(1, 4));
        panel2 = new JPanel(new GridLayout(1, 2));
        panel3 = new JPanel(new GridLayout(2, 1));

        panel1.add(addItemBtn);
        panel1.add(remItemBtn);
        panel1.add(addMemberBtn);
        panel1.add(remMemberBtn);
        panel2.add(borrowBtn);
        panel2.add(returnBtn);
        panel3.add(dateLabel);
        panel3.add(dateBtn);

        frame.add(new JScrollPane(ItemTable));
        frame.add(new JScrollPane(MemberTable));

        if (!memberMode) {
            frame.add(panel1);
        }

        frame.add(panel2);

        if (!memberMode) {
            frame.add(panel3);
        }

        frame.add(new JScrollPane(LoanTable));

        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        new GUI();
    }
}
