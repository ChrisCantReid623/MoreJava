/**
 * @author Christopher Reid
 * 
 * 			Tests if words and different string combinations exists
 * 			inside sample dice trays. 
 */

package tests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.DiceTray;

public class DiceTrayTest {
	
	DiceTray constructorTest = new DiceTray();

	private static char[][] first = { 
			{ 'A', 'B', 'S', 'E' }, 
			{ 'I', 'M', 'T', 'N' }, 
			{ 'N', 'D', 'E', 'D' },
			{ 'S', 'S', 'E', 'N' } };
	
	private static char[][] second = { 
			{ 'A', 'B', 'C', 'Q' }, 
			{ 'T', 'G', 'I', 'X' }, 
			{ 'D', 'E', 'H', 'J' },
			{ 'K', 'L', 'M', 'N' } };
	
	private static char[][] third = { 
			{ 'F', 'A', 'L', 'L' }, 
			{ 'M', 'S', 'D', 'F' }, 
			{ 'O', 'H', 'J', 'K' },
			{ 'Q', 'E', 'Q', 'E' } };
	
	private static DiceTray tray1 = new DiceTray(first);
	private static DiceTray tray2 = new DiceTray(second);
	private static DiceTray tray3 = new DiceTray(third);
	
	@Test
	public void testForSmallStringsNotRealWords() {
		assertFalse(tray1.found(""));
		assertFalse(tray1.found("TS"));
		assertTrue(tray1.found("TMI"));
		assertTrue(tray1.found("aBs"));
		assertTrue(tray1.found("AbS"));
	}

	@Test
	public void testFound2() {
		assertTrue(tray1.found("sent"));
		assertTrue(tray1.found("SENT"));
		assertTrue(tray1.found("minded"));
		assertTrue(tray1.found("teen"));
		assertTrue(tray1.found("dibtd"));
	}

	@Test
	public void testForLongerStrings() {
		assertTrue(tray1.found("NTMINDED"));
		assertTrue(tray1.found("ABSENTMINDEDNESS"));
	}

	@Test
	public void testQu_Situations() {
		assertTrue(tray2.found("Quiet"));
		assertTrue(tray3.found("QueQue"));
		assertTrue(tray3.found("Quehsmo"));
		assertTrue(tray3.found("fallfdsmohjkeque"));
	}
	
	@Test
	public void testRepeatLetters() {
		assertFalse(tray3.found("Mom"));
		assertFalse(tray3.found("Madam"));
	}
	
	@Test
	public void testPrint() {
		tray3.printBoard();
	}

	@Test
	public void testOutOfBounds() {
		tray1.search(" ", 0, 4, 0);
	}

}
