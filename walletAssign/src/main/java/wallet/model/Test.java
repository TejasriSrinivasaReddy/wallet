package wallet.model;

import java.util.List;

import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {

private static ApplicationContext context;
	
	private static AccountDao accountDao;
	
	private static Scanner in=new Scanner(System.in);
	public static void main(String[] args) {
		context=new ClassPathXmlApplicationContext("applicationContext.xml");
		accountDao=(AccountDao)context.getBean("accountDao");
		while(true) {
			System.out.println("---WELCOME---");
			System.out.println("List of operations");
			System.out.println("1.Create Account");
			System.out.println("2.Get Account By Id");
			System.out.println("3.Withdraw");
			System.out.println("4.Deposit");
			System.out.println("5.Amount Transfer");
			System.out.println("6.Transaction history");
			System.out.println("0.Exit");
			System.out.println("Please enter your choice");
			int choice=in.nextInt();
			switch(choice) {
			
				case 1:createAccount();break;
				case 2:getAccountById();break;
				case 3:withdrawAmount();break;
				case 4:depositAmount();break;
				case 5:fundTransfer();break;
				case 6:transactions();break;
				case 0:System.exit(0);
				default:System.out.println("Invalid choice");
			}
		}
	}
	
	
	private static void transactions() {
		System.out.println("Enter the accountId");
		in.nextLine();
		int userId=in.nextInt();
		
		List<Transaction> transactions;
		
			transactions = accountDao.transaction(userId);
			if(transactions.isEmpty()){
				System.out.println("No Transactions made");
			}
			else{
			System.out.println("Transaction History of UserId:"+userId);
			for(Transaction transaction:transactions){
				System.out.println(transaction);
			}
		}
		
	}
		

	private static void fundTransfer() {
		System.out.println("Enter your id");
		int id=in.nextInt();
		System.out.println("Enter the id to which you want to transfer");
		int id2=in.nextInt();
		System.out.println("Enter the amount");
		int amount=in.nextInt();
		try {
			accountDao.withdraw(id, amount);
			System.out.println("Transfer of "+amount+" is successful ");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			accountDao.deposit(id2, amount);
			System.out.println("Money received "+amount);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	private static void depositAmount() {
		System.out.println("Enter Account Id");
		int accountId=in.nextInt();
		System.out.println("Amount?");
		int amount=in.nextInt();
		
		int accountBalance=accountDao.deposit(accountId, amount);
		System.out.println("Amount Deposited,current balance="+accountBalance);
		
	}


	private static void getAccountById() {
		System.out.println("Enter Id");
		in.nextLine();
		int accountId=in.nextInt();
	
		Account account=accountDao.getAccountById(accountId);
		System.out.println(account);
		
		
	}

	private static void createAccount() {
		System.out.println("Name");
		in.nextLine();
		String name=in.nextLine();
		System.out.println("Opening balance");
		int openingBalance=in.nextInt();
		Account account=new Account();
		account.setAccountName(name);
		account.setAccountBalance(openingBalance);
		//account.getAccountId();
		int status=accountDao.saveAccount(account);
		System.out.println("Account created with status "+status);
		
		/*System.out.println("Your Account Id is "+account.getAccountId());
		int accountId=accountDao.getAccountById(accountId).getaccountPin();
		System.out.println("Account created with id  "+accountId+"\n"+"Your Pin is  "+accountPin);
		System.out.println("Remember this ID!!!");*/
	}
	private static void withdrawAmount() {
		System.out.println("Enter your account id");
		int accountId=in.nextInt();
		System.out.println("Amount");
		int amount=in.nextInt();
		
		int accountBalance=accountDao.withdraw(accountId, amount);
		System.out.println("Amount withdrawn,current balance="+accountBalance);
		
		
	}
}

