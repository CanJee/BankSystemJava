Commands:

  `#login <email> <password>`
  
  Log in as a certain person
  
  `#displayaccounts`
  
  Display all the accounts of the currently logged in person
  
  `#withdraw <account_id> <amount>`
  
  Withdraw from an account which the currently logged in user owns a certain amount
  
  `#deposit <account_id> <amount>`
  
  Deposit to an account which the currently logged in user owns a certain amount
  
  `#transfer <transfer_from_id> <transfer_to_id> <amount>`
  
  Transfer from an account which the currently logged in user owns to another account which exists a certain amount
  
  `#dotest`
  
  Preform a test that verifies all commands are functioning correctly
  
  The above command must be done while logged in as test@gmail.com using the following command:
  
  `#login test@gmail.com test`
  
  `#displaytransactions <account_id>`
  
  Display the transactions of a specific account which the currently logged in user owns
  
  When the Server is initiated, 3 people are created and 2 accounts are created for testing purposes. The details are as    follows:
  
  Person 0:
    Name : Jack
    Email : jack@gmail.com
    Password : 1234
    
    Accounts :
      Name : Jack's Checking Account
      Balance : 100.50
      Account Type : Checking
  Person 1:
    Name : Bob
    Email : bob@gmail.com
    Password : 5678
    
    Accounts :
      Name : Bob's Savings Account
      Balance : 500.21
      Account Type : Savings
      
  You can preform various commands with these two accounts
  
  Person 3:
    Name : Test
    Email : test@gmail.com
    Password : test
    
    Accounts : <None>
    
  The only command you can preform with the above person is `#dotest`.
  
  What does the `#dotest` command do?
  
    1) Display's all of Jack's accounts
    2) Withdraw 50 from Jack's Checking Account
    3) Deposit 50 into Jack's Checking Account
    4) Transfer 50 from Jack's Checking Account into Bob's Savings Account
    5) Display transactions conducted on Jack's Checking Account
    6) Display Bob's accounts
    7) Display transactions conducted on Bob's Savings Account
