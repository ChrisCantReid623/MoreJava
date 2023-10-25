/*	Author: Christopher Reid
*	CSC 335 
* 	Regular bank account subclass. Never allows a negative balance. 
*/

public class RegularAccount extends BankAccount {
	public RegularAccount(String ID, double initialBalance) {
		super(ID, initialBalance);
	}

	/**
	 * Withdraws from balance if the balance remains positive.
	 */
	@Override
	public void withdraw(double amount) {
		if (balance - amount > 0.0) {
			balance -= amount;
		}
	}

	// Precondition: amount > 0.0
	@Override
	public void deposit(double amount) {
		if (amount > 0.0) {
			balance += amount;
		}

	}
}
 