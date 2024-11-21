

import java.sql.*;
import java.util.Scanner;

public class Bank {
    public static void openAccount() {
        //Getting input from user
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Name :");
        String name = sc.nextLine();
        System.out.println("Enter your YearofBirth: ");
        String YearofBirth = sc.nextLine();
        System.out.println("Enter your address: ");
        String Address = sc.nextLine();
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        System.out.println("Enter your account password: ");
        String pass = sc.nextLine();
        //url,username and password for database login :
        String url = "jdbc:mysql://localhost/BankInfo";
        String username = "root";
        String password = "ramkrishnayadav";


        try {
            //Connecting with datatbase:

            Connection connect = DriverManager.getConnection(url, username, password);

            //creating statement inorder to pass Query

            String query = "INSERT INTO myBank(Name,YearofBirth,Address,AccountBalance,email,password) VALUES(?,?,?,?,?,?);";
            PreparedStatement stmt = connect.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, YearofBirth);
            stmt.setString(3, Address);
            stmt.setInt(4, 0);
            stmt.setString(5, email);
            stmt.setString(6, pass);
            stmt.executeUpdate();
            System.out.println("Account created successfully.");

            String getquery="SELECT AccountNumber,password from myBank where name=?;";
            PreparedStatement stat=connect.prepareStatement(getquery);
            stat.setString(1,name);
            ResultSet data=stat.executeQuery();
            while(data.next()){
                System.out.println();
                System.out.println("Your account number is : "+data.getString("AccountNumber"));
                System.out.println("Your account password is: "+data.getString("password"));
            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();


        }
    }
    public static void Login(){
        String url="jdbc:mysql://localhost/BankInfo";
        String username="root";
        String password="ramkrishnayadav";
        Scanner sc=new Scanner(System.in);
        System.out.print("ENTER YOUR ACCOUNT NUMBER TO PERFORM TRANSACTIONS: ");
        int accnumber=sc.nextInt();
        sc.nextLine();
        try{
            //Getting connected with database:
            Connection connect=DriverManager.getConnection(url,username,password);
            String query="select password from myBank where AccountNumber=?;";
            PreparedStatement stmt=connect.prepareStatement(query);
            stmt.setInt(1,accnumber);
            ResultSet data= stmt.executeQuery();

            if(data.next()) {
                System.out.print("Enter your password to login to your account: ");
                String enteredpassword = sc.nextLine();
                String accpassword=data.getString("password");
                if(enteredpassword.equals(accpassword)){
                    transactions BankInfo=new transactions();
                    BankInfo.performTransactions(accnumber);
                }
                else{
                    System.out.println("Incorrect password.Please try again.");
                }
            }
            else{
                System.out.println("Enter valid account number.");
                Login();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void forgotPassword(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your account number: ");
        int accnum=sc.nextInt();
        String url="jdbc:mysql://localhost/BankInfo";
        String username="root";
        String password="ramkrishnayadav";
        try {

            Connection conn = DriverManager.getConnection(url, username, password);
            String query="select email,YearofBirth,password from myBank where AccountNumber=?";
            PreparedStatement stmt=conn.prepareStatement(query);
            stmt.setInt(1,accnum);
            ResultSet data=stmt.executeQuery();
            while(data.next()){
                String email=data.getString("email");
                String YearofBirth=data.getString("YearofBirth");
                String pass=data.getString("password");
                System.out.println("You need to verify yourself to get password.");
                System.out.println("Enter your email: ");
                String gotemail=sc.next();
                System.out.println("Enter year of birth: ");
                String gotyear=sc.next();
                if(gotemail.equals(email)&&YearofBirth.equals(gotyear) ){
                    System.out.println("Verification Successful.\nYour password is: "+pass);
                }
                else{
                    System.out.println("Please provide correct credentials.");
                }

            }
            conn.close();
            stmt.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
