package WhiteBox_Tests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import Checks.HalsteadEffortCheck;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class HalsteadEffortCheckTest {


Integer[] tokens = { 
			
			/* Unary Operator Type*/	
			TokenTypes.POST_INC,TokenTypes.POST_DEC,TokenTypes.DEC,TokenTypes.INC,
			TokenTypes.LNOT,TokenTypes.BNOT,TokenTypes.UNARY_MINUS,TokenTypes.UNARY_PLUS,
			
			/* Arithmetic Operator type */
			TokenTypes.STAR,TokenTypes.DIV,TokenTypes.MOD,TokenTypes.PLUS,TokenTypes.MINUS,
			TokenTypes.BSR,TokenTypes.SR,TokenTypes.SL,
			
			/* Relational Operator type */
			TokenTypes.LT,TokenTypes.GT,TokenTypes.LE,TokenTypes.GE,
			TokenTypes.LITERAL_INSTANCEOF,TokenTypes.EQUAL,TokenTypes.NOT_EQUAL,
			
			/* Bitwise */
			TokenTypes.BAND,TokenTypes.BXOR,TokenTypes.BOR,
			
			/* Logical Operator type */
			TokenTypes.LAND,TokenTypes.LOR,
			
			/* Ternary  Operator type */
			TokenTypes.QUESTION,TokenTypes.EOF,
			
			/* Assignment  Operator type  */
			TokenTypes.ASSIGN,TokenTypes.BAND_ASSIGN,TokenTypes.BOR_ASSIGN,
			TokenTypes.BSR_ASSIGN,TokenTypes.BXOR_ASSIGN,TokenTypes.DIV_ASSIGN,
			TokenTypes.MINUS_ASSIGN,TokenTypes.MOD_ASSIGN,TokenTypes.PLUS_ASSIGN,
			TokenTypes.SL_ASSIGN,TokenTypes.SR_ASSIGN,TokenTypes.STAR_ASSIGN,
			
			// operands 
			TokenTypes.IDENT, 
			TokenTypes.NUM_DOUBLE,
			TokenTypes.NUM_FLOAT,
			TokenTypes.NUM_INT,
			TokenTypes.NUM_LONG 
			
	};

	HashSet<Integer> expectedTokens = new HashSet<Integer>(Arrays.asList(tokens));

	@Test
	public void DefaultTokensTest() {
		
		HalsteadEffortCheck test = new HalsteadEffortCheck();
		List<Integer> toks = Arrays.stream(test.getDefaultTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(toks);

		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void testGetAcceptableTokens() {
		HalsteadEffortCheck test = new HalsteadEffortCheck();
		
		List<Integer> toks = Arrays.stream(test.getAcceptableTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(toks);

		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void testGetRequiredTokens() {
		HalsteadEffortCheck test = new HalsteadEffortCheck();
		List<Integer> toks = Arrays.stream(test.getRequiredTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(toks);

		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	
	@Test // Test for knowed values.
	public void HalsteadEffortTest1() {
		
		//Halstead Effort is the difficulty multiplied by the volume. Effort = DV. 
		//Effort was intended as a suggestion for how long code review might take [
		
		HalsteadEffortCheck test = spy(new HalsteadEffortCheck());
		DetailAST ast = mock(DetailAST.class);

		/*
		 *  = .5*(unique operators)*(total operands) / 
		 *        (uniqu operands)
		 */
		doReturn(10.0).when(test).getHalsteadVolume();
		doReturn(10.0).when(test).getHalsteadDifficulty();
		
		
		
		test.beginTree(ast);
		test.finishTree(ast);

		//  volume = length * log2(vocabulary)
		//  volume = 45 * log2(19) = 191.15
		assertEquals(10.0, test.getHalsteadVolume(), 0.1);
		assertEquals(10.0, test.getHalsteadDifficulty(), 0.1);
		assertEquals(100, test.CalcHalsteadEffort(), 0.1);
		
	}
	
	
	/*@Test // Test for no values
	public void NoTokensTest() {
		HalsteadEffortCheck test = spy(new HalsteadEffortCheck());
		DetailAST ast = mock(DetailAST.class);
		
		test.beginTree(ast);
		test.finishTree(ast);

		assertEquals(0, test.getHalsteadDifficulty(), 0.1);
		assertEquals(0, test.getHalsteadVolume(), 0.1);
		assertEquals(0, test.CalcHalsteadEffort(), 0.1);
	}*/
	
	/*@Test // test for wrong token provided in visitToken method.
	public void TestVisitToken() {
		
		HalsteadEffortCheck test = spy(new HalsteadEffortCheck());
		DetailAST ast = mock(DetailAST.class);
		test.beginTree(ast);
		
		doReturn(TokenTypes.LITERAL_FOR).when(ast).getType(); 
		doReturn("forloop").when(ast).getText();
		
		test.visitToken(ast);
		test.finishTree(ast);


		assertEquals(0, test.getHalsteadDifficulty(), 0.1);
		assertEquals(0, test.getHalsteadVolume(), 0.1);
		assertEquals(0, test.CalcHalsteadEffort(), 0.1);
		
	}*/
	
	//@Test // Test for when only halstead volume is present.
	//public void TestOneTokenGiven() {
		
		///HalsteadEffortCheck test = spy(new HalsteadEffortCheck());
		//DetailAST ast = mock(DetailAST.class);
		//test.beginTree(ast);
		
		///doReturn(10.0).when(test).getHalsteadVolume();
		
		//test.beginTree(ast);
		//test.finishTree(ast);
		
		//assertEquals(0, test.CalcHalsteadEffort(), 0.1);
	//}
}
