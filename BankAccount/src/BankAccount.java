/*	Author: Christopher Reid
*	CSC 335 
* 	Superclass for various bank accounts. 
*/

public abstract class BankAccount {
	protected String ID;
	protected double balance;
	
	public BankAccount(String ID, double initialBalance) {
		this.ID = ID;
		balance = initialBalance;
	}
	
	// Implement command methods
	public double getBalance() {
		return balance;
	}
	
	abstract public void withdraw(double amount);
	abstract public void deposit(double amount);
}
  