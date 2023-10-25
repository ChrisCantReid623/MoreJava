/**
 * @author Christopher Reid
 * 
 *         Tests the tray of dice in the game Boggle. A DiceTray can be
 *         constructed with 4x4 array of characters for testing.
 */

package tests;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import model.Boggle;

class BoggleTest {
	
	Boggle randomGame = new Boggle();
	
	private char[][] sampleDiceTray = {
			  { 'A', 'B', 'S', 'E' },
			  { 'I', 'M', 'T', 'N' },
			  { 'N', 'D', 'E', 'D' },  
			  { 'S', 'S', 'E', 'N' } };
	
	Boggle customGame = new Boggle(sampleDiceTray);
	
	@Test
	void testCustomConstructors() {
		assertTrue(customGame.foundInTray("absent"));
		assertTrue(customGame.foundInTray("SeNd"));
	}
	
	@Test
	void testMissedWords() {
		HashSet<String> correctTest = new HashSet<String>();
		HashSet<String> possibleWordsTest = new HashSet<String>();
		correctTest.add("wrong");
		possibleWordsTest.add("send");
		assertEquals(possibleWordsTest, customGame.missedWords(correctTest, possibleWordsTest));
		
		
	}
	
	@Test
	void testGetScore() {
		HashSet<String> correctTest = new HashSet<String>();
		correctTest.add("cor");
		correctTest.add("corr");
		correctTest.add("corre");
		correctTest.add("correc");
		correctTest.add("correct");
		correctTest.add("correcta");
		correctTest.add("correctan");
		assertNotEquals(5, customGame.getScore(correctTest));
		
	}
	
	@Test
	void testWordsToString() {
		HashSet<String> testSet = new HashSet<String>();
		testSet.add("test");
		testSet.add("to");
		testSet.add("compare");
		String testComp = "compare test to";
		assertEquals(testComp, customGame.wordsToString(testSet));
		
	}
	
	@Test
	void testPrinter() {
		customGame.printBoard();
	}
}
