import java.util.ArrayList;
import java.util.Random;


public class Account {
	
	private double balance;
	private String name;
	private int id;
	private AccountType accountType;
	private Person accountHolder;
	ArrayList<Transaction> transactions;
	
	public Account(double balance, String name, AccountType accountType, Person accountHolder){
		Random rand = new Random(); 
		int id = rand.nextInt(9000) + 1000;
		this.balance = balance;
		this.name = name;
		this.accountType = accountType;
		this.accountHolder = accountHolder;
		transactions = new ArrayList<Transaction>();
		accountHolder.addAccount(this);
		
	}
	
	public boolean withdraw(double amount){
		if (amount > balance){
			return false;
		}
		else{
			balance =- amount;
			Transaction trans = new Transaction(amount, "Withdraw from account " + accountName() + ":" + accountId());
			transactions.add(trans);
			return true;
		}
	}
	
	public double accountBalance(){
		return balance;
	}
	
	public void deposit(double amount){
		balance += amount;
		Transaction trans = new Transaction(amount, "Deposit into account " + accountName() + ":" + accountId());
		transactions.add(trans);
	}
	
	public boolean transfer(Account toAccount, double amount){
		boolean withdrawSuccessful = withdraw(amount);
		if (withdrawSuccessful){
			toAccount.deposit(amount);
			Transaction trans = new Transaction(amount, "Transfer from account " + accountName() + ":" + accountId() + " to account " + toAccount.accountName() + ":" + toAccount.accountId(), toAccount);
			transactions.add(trans);
			return true;
		}
		else
			return false;
	}
	
	public double computeInterest(){
		double interestRate = accountType.interestRate()/100;
		double interest = balance*interestRate;
		return interest;
	}
	
	public double addInterestToBalance(){
		deposit(computeInterest());
		return balance;
	}
	
	public String accountName(){
		return name;
	}
	
	public int accountId(){
		return id;
	}
	
	public Person accountHolder(){
		return accountHolder;
	}
	
	public AccountType accountType(){
		return accountType;
	}
	
	public String toString(){
		String accountStr = "Account Name: " + accountName() + "\n Balance: " + accountBalance() + "\n Account Type: " + accountType().toString() + "\n Account Holder: " + accountHolder.toString() + "\n Account Id: " + accountId();
		return accountStr;
	}
}
