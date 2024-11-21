import java.sql.*;
import java.util.Scanner;

public class transactions {
    public static void performTransactions(int accnumber){
        System.out.println("Login successful!!!");
        String url="jdbc:mysql://localhost/BankInfo";
        String username="root";
        String password="ramkrishnayadav";

        //Getiing connected with database:
        try {
            Connection connect = DriverManager.getConnection(url, username, password);
            //PREPARING STATEMENT TO PASS QUERY:
            String query="Select Name,Address,AccountBalance,email from myBank where AccountNumber=?";
            PreparedStatement stmt=connect.prepareStatement(query);
            stmt.setInt(1,accnumber);
            ResultSet Info=stmt.executeQuery();
            while(Info.next()) {
                System.out.println("Account Name    :" + Info.getString("Name"));
                System.out.println("Address:        :" + Info.getString("Address"));
                System.out.println("Email id        :" + Info.getString("email"));
                System.out.println("Account Balance :" + Info.getString("AccountBalance"));
                Scanner scan = new Scanner(System.in);
                System.out.println("Select the operation you want to perform:");
                System.out.print("1.WithdrawMoney\n2.Deposit Money\n3.Transfer Money\npress any key other key to logout.");
                int enteredvalue = scan.nextInt();
                scan.nextLine();
                Money bank = new Money();
                if (enteredvalue == 1) {

                    bank.withdraw(accnumber);
                } else if (enteredvalue==2) {
                    bank.deposit(accnumber);
                } else if (enteredvalue==3) {
                    bank.Transfer(accnumber);
                }
                else{
                    System.out.println("Please login again to perform transactions.");
                    Bank banklogin=new Bank();
                    banklogin.Login();
                }

            }


        } catch(Exception e){
            e.printStackTrace();
        }


    }

}
