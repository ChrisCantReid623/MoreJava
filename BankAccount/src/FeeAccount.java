/*	Author: Christopher Reid
*	CSC 335 
* 	Fee bank account subclass. Adds a fee to any balance withdraws. 
*/

public class FeeAccount extends BankAccount {
	private final double withdrawFee = 2.0;

	public FeeAccount(String ID, double initialBalance) {
		super(ID, initialBalance);
	}

	/**
	 * Adds fee to any withdraws if balance remains positive.
	 */
	@Override
	public void withdraw(double amount) {
		if (balance - amount > 0.0) {
			if (balance - (amount + withdrawFee) > 0.0)
				balance -= (amount + withdrawFee);
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