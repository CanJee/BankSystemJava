
public class AccountType {
	
	private String typeName;
	private double interestRate;
	
	public AccountType(String typeName, double interestRate){
		this.typeName = typeName;
		this.interestRate = interestRate;
		
	}
	
	public String typeName(){
		return typeName;
	}
	
	public double interestRate(){
		return interestRate;
	}
	
	public String toString(){
		String accountTypeStr = "Type Name: " + typeName() + "\n Interest Rate: " + interestRate();
		return accountTypeStr;
	}
}
