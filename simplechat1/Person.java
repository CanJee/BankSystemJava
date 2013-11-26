import java.util.ArrayList;


public class Person {
	
	private String name;
	private String password;
	private String email;
	private ArrayList<Account> accounts;
	
	public Person(String name, String password, String email){
		this.name = name;
		this.password = password;
		this.email = email;
		this.accounts = new ArrayList<Account>();
	}
	
	public void addAccount(Account account){
		accounts.add(account);
	}
	
}
