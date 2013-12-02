import java.util.ArrayList;
import java.util.Random;


public class Account {
	
	private double balance;
	private String name;
	private int identification;
	private AccountType accountType;
	private Person accountHolder;
	private ArrayList<Transaction> transactions;
	
	public Account(double balance, String name, AccountType accountType, Person accountHolder){
		Random rand = new Random();
		identification = rand.nextInt((9999 - 1000) + 1) + 1000;
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
			balance = balance - amount;
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
	
	public void addTransaction(Transaction transaction){
		transactions.add(transaction);
	}
	
	public boolean transfer(Account toAccount, double amount){
		boolean withdrawSuccessful = !(amount>balance);
		if (withdrawSuccessful){
			balance = balance - amount;
			toAccount.balance += amount;
			Transaction trans = new Transaction(amount, "Transfer sent from account " + accountName() + ":" + accountId() + " to account " + toAccount.accountName() + ":" + toAccount.accountId(), toAccount);
			transactions.add(trans);
			Transaction trans2 = new Transaction(amount, "Transfer received from account " + accountName() + ":" + accountId() + " to account " + toAccount.accountName() + ":" + toAccount.accountId(), toAccount);
			toAccount.addTransaction(trans2);
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
		double amount = computeInterest();
		deposit(amount);
		Transaction trans = new Transaction(amount, "Deposit interest into account " + accountName() + ":" + accountId());
		transactions.add(trans);
		return balance;
	}
	
	public String accountName(){
		return name;
	}
	
	public int accountId(){
		return identification;
	}
	
	public Person accountHolder(){
		return accountHolder;
	}
	
	public AccountType accountType(){
		return accountType;
	}
	
	public ArrayList<Transaction> accountTransactions(){
		return transactions;
	}
	
	public String toString(){
		String accountStr = "Account Name: " + accountName() + "\n Balance: " + accountBalance() + "\n Account Type: " + accountType().toString() + "\n Account Holder: " + accountHolder().toString() + "\n Account Id: " + identification;
		return accountStr;
	}
}
