/*
 * Tests the check-styles "Number of Comments" in files
 * by testing the following components of the NumberOfCommets Class:
 * 	
 *   1. initial tree construction. 
 *   2. required and accepted tokens.
 *   3. No comments
 *   4. Single comments
 *   5. block comments
 *   
 * */

package WhiteBox_Tests;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import Checks.NumberOfCommetsCheck;
;


public class NumberOfCommentsCheckTest {
	
	int[] expectedTokens = { TokenTypes.COMMENT_CONTENT };
	
	
	@Test // Test initialization of count in Begin tree.
	public void BeginTreeTest() {
		NumberOfCommetsCheck test = new NumberOfCommetsCheck();
		DetailAST ast = mock(DetailAST.class);

		test.beginTree(ast);
		assertEquals(0, test.getResults());
	}
	
	
	@Test // Test that it only accepts expected tokens.
	public void GetRequiredTokensTest() {
		NumberOfCommetsCheck test = new NumberOfCommetsCheck();

		assertArrayEquals(expectedTokens, test.getRequiredTokens());
	}
	
	@Test // visit token test
	public void VisitTokenTest() {
		NumberOfCommetsCheck test = new NumberOfCommetsCheck();
		DetailAST ast = mock(DetailAST.class);
		test.beginTree(ast); 
		test.visitToken(ast);
		test.visitToken(ast);
		test.visitToken(ast);
		assertEquals(3, test.getResults());
	}
	
	@Test // Test function for only accepting the desired tokens.
	public void GetAcceptableTokensTest() {
		NumberOfCommetsCheck test = new NumberOfCommetsCheck();
		assertArrayEquals(expectedTokens, test.getAcceptableTokens());
	}
	
	@Test // Test for more than one  single comment.
	public void MultiSingleCommentTest() {
		NumberOfCommetsCheck test = new NumberOfCommetsCheck();
		
		DetailAST ast = mock(DetailAST.class);
		test.beginTree(ast); 
		
		doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(ast).getType();
		test.visitToken(ast);
		doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(ast).getType();
		test.visitToken(ast);
		doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(ast).getType();
		test.visitToken(ast);
		doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(ast).getType();
		test.visitToken(ast);
		
		assertEquals(4, test.getResults());
	}
	
	@Test // Test for no comments 
	public void NoCommentTest() {
		NumberOfCommetsCheck test = spy(new NumberOfCommetsCheck());
		DetailAST ast = mock(DetailAST.class);
		test.beginTree(ast); 
		doReturn(false).when(test).isCommentNodesRequired();
		assertEquals(false, test.isCommentNodesRequired());
		assertEquals(0, test.getResults());
	}
	
	
	@Test // Test for block comments.
	public void TestBlockComments() {
		NumberOfCommetsCheck test = new NumberOfCommetsCheck();
		DetailAST ast = mock(DetailAST.class);
		test.beginTree(ast);
		
		doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(ast).getType();
		test.visitToken(ast);
		doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(ast).getType();
		test.visitToken(ast);
		doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(ast).getType();
		test.visitToken(ast);
		doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(ast).getType();
		test.visitToken(ast);
		
		assertEquals(4, test.getResults());
		
		
	}
	@Test // Test Exception and exception message.
	public void CommentTest1() {
		
		NumberOfCommetsCheck test = spy(NumberOfCommetsCheck.class);
		DetailAST ast = mock(DetailAST.class);
		
	     final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	     System.setOut(new PrintStream(outputStreamCaptor));
		
		test.beginTree(ast); 
		test.finishTree(ast);
			
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error from treewalker!",outputStreamCaptor.toString().trim());
		
	}
	
	@Test // Test Exception and exception message.
	public void NumCommentsTest1() {
		
		NumberOfCommetsCheck test = spy(NumberOfCommetsCheck.class);
		DetailAST ast = mock(DetailAST.class);
		
	     final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	     System.setOut(new PrintStream(outputStreamCaptor));
		
		test.beginTree(ast); 
		test.finishTree(ast);
			
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error from treewalker!",outputStreamCaptor.toString().trim());
		
	}
}
