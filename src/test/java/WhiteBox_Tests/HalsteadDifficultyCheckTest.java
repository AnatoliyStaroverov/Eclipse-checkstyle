package WhiteBox_Tests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import Checks.HalsteadDifficultyCheck;




// Halstead Difficulty is half of the unique operators multiplied by the total number
// of operands, divided by the number of unique operands 
public class HalsteadDifficultyCheckTest {

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
		
		HalsteadDifficultyCheck test = new HalsteadDifficultyCheck();
		List<Integer> toks = Arrays.stream(test.getDefaultTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(toks);

		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void testGetAcceptableTokens() {
		HalsteadDifficultyCheck test = new HalsteadDifficultyCheck();
		
		List<Integer> toks = Arrays.stream(test.getAcceptableTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(toks);

		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void testGetRequiredTokens() {
		HalsteadDifficultyCheck test = new HalsteadDifficultyCheck();
		List<Integer> toks = Arrays.stream(test.getRequiredTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(toks);

		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	
	@Test
	public void HalsteadDifficultyTest1() {
		
		HalsteadDifficultyCheck test = spy(new HalsteadDifficultyCheck());
		DetailAST ast = mock(DetailAST.class);

		/*
		 *  = .5*(unique operators)*(total operands) / 
		 *        (uniqu operands)
		 */
		doReturn(10.0).when(test).getUniqueOperators();
		doReturn(20.0).when(test).getOperands();
		doReturn(10.0).when(test). getUniqueOperands();
		
		
		test.beginTree(ast);
		test.finishTree(ast);

		//  volume = length * log2(vocabulary)
		//  volume = 45 * log2(19) = 191.15
		assertEquals(10, test.CalcHalsteadDifficulty(), 0.1);
		
	}
}
