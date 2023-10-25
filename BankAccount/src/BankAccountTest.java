/* Author: Christopher Reid
* Tests Bank account and subclasses.
*/

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BankAccountTest {

	@Test
	void test() {
		BankAccount regAcct = new RegularAccount("ID", 100.0);
		assertEquals(100.00, ((RegularAccount)regAcct).getBalance());
		((RegularAccount)regAcct).deposit(5.2);
		assertEquals(105.20, ((RegularAccount)regAcct).getBalance());
		((RegularAccount)regAcct).withdraw(50.00);
		assertEquals(55.20, ((RegularAccount)regAcct).getBalance());
	}
	
	@Test
	void testFeeAcount() {
		BankAccount feeAcct = new FeeAccount("ID", 100.00);
		assertEquals(100.00, ((FeeAccount)feeAcct).getBalance());
		((FeeAccount)feeAcct).withdraw(3.00);
		assertEquals(95.00, ((FeeAccount)feeAcct).getBalance());
		((FeeAccount)feeAcct).deposit(1002.00);
		
	}
	
	@Test
	void testSafeAccount() {
		BankAccount safeAcct = new SafeAccount("ID", 100.00);
		assertEquals(100.00, ((SafeAccount)safeAcct).getBalance());
		((SafeAccount)safeAcct).withdraw(3.00);
		assertEquals(1000.00, ((SafeAccount)safeAcct).getLoanAmount());
		assertNotEquals(100.00, ((SafeAccount)safeAcct).getBalance());
		((SafeAccount)safeAcct).withdraw(1001.00);
		((SafeAccount)safeAcct).deposit(1002.00);
		System.out.println(((SafeAccount)safeAcct).getBalance());
		System.out.println(((SafeAccount)safeAcct).getLoanAmount());
		((SafeAccount)safeAcct).withdraw(1098.00);
		System.out.println(((SafeAccount)safeAcct).getBalance());
		System.out.println(((SafeAccount)safeAcct).getLoanAmount());
		((SafeAccount)safeAcct).deposit(500.00);
		((SafeAccount)safeAcct).deposit(500.00);
		((SafeAccount)safeAcct).deposit(500.00);
		
	}

}
  