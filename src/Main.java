//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        Bank bank=new Bank();
        System.out.println("Welcome to World Bank. ");
        System.out.println();
        System.out.println("1.Open new Account. \n2.Login to your account.\n3.Reset account password(if forgotten).");

        System.out.print("Please select the operation you want to perform(1-3): ");
        int enteredvalue= scan.nextInt();
        if(enteredvalue==1){
            Bank.openAccount();
        }
        else if(enteredvalue==2){
            Bank.Login();
        } else if (enteredvalue==3) {
            Bank.forgotPassword();
        }
        else{
            System.out.println("Please enter value from 1-3");
        }
    }
}