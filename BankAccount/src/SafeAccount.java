/*	Author: Christopher Reid
*	CSC 335 
* 	Bank account with extra credit line. Prioritizes deposits to 
* 	credit line repayments over replenishing balance. 
*/

public class SafeAccount extends BankAccount {

	protected double creditLine = 1000.00;

	public SafeAccount(String ID, double initialBalance) {
		super(ID, initialBalance);
	}

	/**
	 * Returns creditLine.
	 */
	public double getLoanAmount() {
		return creditLine;
	}

	/**
	 * Withdraws money from both $1000 credit line and initial balance
	 */
	@Override
	public void withdraw(double amount) {
		double limit = (balance + creditLine);
		if (limit >= amount) {
			if (balance >= amount) {
				balance -= amount;
			} else {
				creditLine -= (amount - balance);
				balance = 0;
			}
		}

	}

	/**
	 * Prioritizes the credit line before the balance upon deposit.
	 */
	@Override
	public void deposit(double amount) {
		if (creditLine < 1000) {
			if ((creditLine + amount) <= 1000) {
				creditLine += amount;
			} else {
				double remaining = amount - (1000 - creditLine);
				creditLine = 1000;
				balance += remaining;
			}
		} else {
			balance += amount;
		}

	}

} 
