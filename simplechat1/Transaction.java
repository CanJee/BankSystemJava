import java.util.Date;


public class Transaction {
	
	private double amount;
	private Date date;
	private String memo;
	private Account toAccount;
	
	public Transaction(double amount, String memo){
		this.amount = amount;
		this.date = new Date();;
		this.memo = memo;
		this.toAccount = null;
	}
	
	public Transaction(double amount, String memo, Account toAccount){
		this.amount = amount;
		this.date = new Date();;
		this.memo = memo;
		this.toAccount = toAccount;
	}
	
	public double transactionAmount(){
		return amount;
	}
	
	public String transactionDate(){
		return date.toString();
	}
	
	public String transactionMemo(){
		return memo;
	}
	
	public Account transferToAccount(){
		if (toAccount != null)
			return toAccount;
		else
			return null;
	}
	
	public String toString(){
		String transaction = "Amount: " + transactionAmount() + "\n Date: " + transactionDate() + "\n Memo: " + transactionMemo();
		Account toAccount = transferToAccount();
		if (toAccount==null)
			return transaction;
		else{
			transaction+="\n Transfer to Account: " + toAccount;
			return transaction;
		}
	}
	
}
