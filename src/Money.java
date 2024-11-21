import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Money {
    public static void deposit(int accnumber){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the amount you want to deposit: ");
        int amount=sc.nextInt();
        sc.nextLine();
        String url="jdbc:mysql://localhost/BankInfo";
        String username="root";
        String password="ramkrishnayadav";
        //getting connected with database:
        try {
            Connection connect = DriverManager.getConnection(url, username, password);
            String query="select AccountBalance from myBank where AccountNumber=?";
            PreparedStatement stmt=connect.prepareStatement(query);
            stmt.setInt(1,accnumber);
            ResultSet data=stmt.executeQuery();
            if(data.next()){
                int currentbalance=data.getInt("AccountBalance");
                int updatedAmount=currentbalance+amount;
                String newquery="UPDATE myBank SET AccountBalance=? where AccountNumber=?";
                PreparedStatement pstmt=connect.prepareStatement(newquery);
                pstmt.setInt(1,updatedAmount);
                pstmt.setInt(2,accnumber);
                pstmt.executeUpdate();
                System.out.println("Amount Credited.Your new balance is "+updatedAmount);
            }
            connect.close();
            stmt.close();
            sc.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    public static  void withdraw(int accnumber){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the amount you want to withdraw: ");
        int amount=sc.nextInt();
        sc.nextLine();
        String url="jdbc:mysql://localhost/BankInfo";
        String username="root";
        String password="ramkrishnayadav";
        try{
            //Connecting with database
            Connection connect=DriverManager.getConnection(url,username,password);
            String query="select AccountBalance from myBank where AccountNumber=?";
            PreparedStatement stmt= connect.prepareStatement(query);
            stmt.setInt(1,accnumber);
            ResultSet data=stmt.executeQuery();
            while(data.next()){
                int currentBalance=data.getInt("AccountBalance");
                if(currentBalance>=amount){
                    int updatedbalance=currentBalance-amount;
                    String newquery="update myBank set AccountBalance=? where AccountNumber=?";
                    PreparedStatement pstmt=connect.prepareStatement(newquery);
                    pstmt.setInt(1,updatedbalance);
                    pstmt.setInt(2,accnumber);
                    pstmt.executeUpdate();
                    System.out.println("Amount "+amount+" withdraw successful.Your new account balance is "+updatedbalance);
                    transactions tran=new transactions();
                    tran.performTransactions(accnumber);
                }
                else{
                    System.out.println("Please withdraw amount less than your account balance.");
                }


            }
            connect.close();
            stmt.close();

        } catch (Exception e) {
           e.printStackTrace();
        }

    }
    public static void Transfer(int accnumber){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter AccountNumber where you want to transfer money: ");
        int receiveraccount=sc.nextInt();
        System.out.println("Enter amount you want to transfer: ");
        int sendingamount=sc.nextInt();
        sc.nextLine();

        String url="jdbc:mysql://localhost/BankInfo";
        String username="root";
        String password="ramkrishnayadav";

        int senderAccountBalance=0;
        int receiverAccountBalance=0;
        int count=0;

        try{
            Connection conn=DriverManager.getConnection(url,username,password);
            //checking if account number is correct or not:
            String query = "SELECT COUNT(*) FROM myBank WHERE AccountNumber = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, receiveraccount);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                count=rs.getInt(1);
            }

            //query1 gets accountbalance of senderaccount
            String query1="Select AccountBalance from myBank where AccountNumber=?";
            PreparedStatement stmt1=conn.prepareStatement(query1);
            stmt1.setInt(1,accnumber);
            ResultSet data1=stmt1.executeQuery();
            if(data1.next()){
                senderAccountBalance=data1.getInt("AccountBalance");
            }

            //query2 gets accountbalance of receiver account:
            String query2="select AccountBalance from myBank where AccountNumber=?";
            PreparedStatement stmt2=conn.prepareStatement(query2);
            stmt2.setInt(1,receiveraccount);
            ResultSet data2=stmt2.executeQuery();

            if(data2.next()){
                receiverAccountBalance=data2.getInt("AccountBalance");
            }
            //checking if sender has amount in his account :
            if(senderAccountBalance>=sendingamount&&count>0) {

                    int updatedReceiverBalance = receiverAccountBalance + sendingamount;
                    int updatedSenderBalance = senderAccountBalance - sendingamount;

                    //updating accountbalance in the database:
                    String query3 = "update myBank set AccountBalance=? where AccountNumber=?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query3);
                    pstmt1.setInt(1, updatedSenderBalance);
                    pstmt1.setInt(2, accnumber);

                    String query4 = "update myBank set AccountBalance=? where AccountNumber=?";
                    PreparedStatement pstmt2 = conn.prepareStatement(query4);
                    pstmt2.setInt(1, updatedReceiverBalance);
                    pstmt2.setInt(2, receiveraccount);
                    pstmt1.executeUpdate();
                    pstmt2.executeUpdate();
                    System.out.println("Transfer Successful.");

                }

            else {
                System.out.println("Your sending amount should be less than or equal to your accountbalance or you should enter valid account number.");
            }


        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
