package com.ibm.bankwallet;

import java.util.List;
import java.util.Scanner;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibm.wallet.beans.Transactions;
import com.ibm.wallet.dao.WalletDao;
import com.ibm.wallet.dao.WalletDaoImpl;

public class App 
{	private static Scanner sc=new Scanner(System.in);
static ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
static WalletDao wallDao=context.getBean("walletDao",WalletDaoImpl.class);
    public static void main( String[] args )
    {
       while(true){
    	   System.out.println("1.Create a new account");
    	   System.out.println("2.Withdraw");
    	   System.out.println("3.Deposit");
    	   System.out.println("4.Transfer");
    	   System.out.println("5.Transaction History");
    	   System.out.println("6.Exit Menu");
    	   int ch=sc.nextInt();
    	   switch(ch){
    	   case 1:createAccount(); break;
    	   case 2:withDraw(); break;
    	   case 3:deposit(); break;
    	   case 4:transfer();break;
    	   case 5:transactions();break;
    	   case 6:System.exit(0);
    	   default:System.out.println("Select a valid option");
    	   }
    	   
       }
    }
	private static void transfer() {
		// TODO Auto-generated method stub
		
	}
	private static void transactions() {
		System.out.println("Enter the accountId");
		sc.nextLine();
		String userId=sc.nextLine();
		
		List<Transactions> transactions;
		try {
			transactions = wallDao.trasaction(userId);
			if(transactions.isEmpty()){
				System.out.println("No Transactions made");
			}
			else{
			System.out.println("Transaction History of UserId:"+userId);
			for(Transactions transaction:transactions){
				System.out.println(transaction);
			}
		}
		}catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	
	private static void deposit() {
		System.out.println("Enter the accountId");
		sc.nextLine();
		String userId=sc.nextLine();
		System.out.println("Enter the amout to be deposited");
		double amount=sc.nextDouble();
		double balance;
		try {
			balance = wallDao.deposit(amount, userId);
			System.out.println(balance+" is the current balance.");
		} catch (AccountNotFoundException e) {
		System.err.println(e.getMessage());
		}
		
		
	}
	private static void withDraw() {
		System.out.println("Enter the accountId");
		sc.nextLine();
		String userId=sc.nextLine();
		System.out.println("Enter the amout to be withdrawn");
		double amount=sc.nextDouble();
		double balance;
		try {
			balance = wallDao.withdraw(amount, userId);
			System.out.println(balance+" is the current balance.");
		} catch (AccountNotFoundException e) {
				System.err.println(e.getMessage());
			
		}
		
	}
	private static void createAccount() {
		System.out.println("Enter the name:");
		sc.nextLine();
		String userName=sc.nextLine();
		System.out.println("Enter the balance");
		double balance=sc.nextDouble();
		
		String res=wallDao.createAccount(userName, balance);
		System.out.println("Account Created with UserId:"+res);
		
	}
}
