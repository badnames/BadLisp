import at.schalktech.badlisp.parser.lexer.Token;
import at.schalktech.badlisp.parser.lexer.TokenType;
import at.schalktech.badlisp.parser.syntaxtree.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void testSimpleFunction() {
        String source = "(test)";
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.LIST_START, 0, 1, source));
        tokens.add(new Token(TokenType.SYMBOL, 1, 5, source));
        tokens.add(new Token(TokenType.LIST_END, 5, 6, source));

        SyntaxTree tree = null;
        try {
            tree = new SyntaxTree(tokens.iterator());
        } catch (SyntaxErrorException e) {
            fail("Syntax error encountered!");
        }

        assertNotEquals(null, tree);

        tree.traverse(root -> {
            assertTrue(root.getChild(0) instanceof ListNode);
            assertTrue(root.getChild(0).getChild(0) instanceof SymbolNode);
            assertEquals("test", ((SymbolNode) root.getChild(0).getChild(0)).getSymbol());
        });
    }

    @Test
    public void testNestedFunction() {
        String source = "(test (+ 1 2.5))";
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.LIST_START, 0, 1, source));
        tokens.add(new Token(TokenType.SYMBOL, 1, 5, source));
        tokens.add(new Token(TokenType.LIST_START, 6, 7, source));
        tokens.add(new Token(TokenType.SYMBOL, 7, 8, source));
        tokens.add(new Token(TokenType.NUMBER, 9, 10, source));
        tokens.add(new Token(TokenType.NUMBER, 11, 14, source));
        tokens.add(new Token(TokenType.LIST_END, 15, 16, source));
        tokens.add(new Token(TokenType.LIST_END, 16, 17, source));

        SyntaxTree tree = null;
        try {
            tree = new SyntaxTree(tokens.iterator());
        } catch (SyntaxErrorException e) {
            System.out.println(e.getMessage());
            fail("Syntax error encountered!");
        }

        assertNotEquals(null, tree);

        tree.traverse(root -> {
            assertTrue(root.getChild(0) instanceof ListNode);
            assertTrue(root.getChild(0).getChild(0) instanceof SymbolNode);
            assertEquals("test", ((SymbolNode) root.getChild(0).getChild(0)).getSymbol());

            assertTrue(root.getChild(0).getChild(1) instanceof ListNode);
            assertTrue(root.getChild(0).getChild(1).getChild(0) instanceof SymbolNode);
            assertEquals("+", ((SymbolNode) root.getChild(0).getChild(1).getChild(0)).getSymbol());
            assertTrue(root.getChild(0).getChild(1).getChild(1) instanceof IntegerNode);
            assertEquals(1, ((IntegerNode) root.getChild(0).getChild(1).getChild(1)).getValue());
            assertTrue(root.getChild(0).getChild(1).getChild(2) instanceof RationalNode);
            assertEquals(2.5, ((RationalNode) root.getChild(0).getChild(1).getChild(2)).getValue(), 0);
        });
    }

    @Test
    public void testSyntaxErrorOutsideList() {
        String source = "\"String outside List\" 1234";

        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.STRING, 0, 21, source));
        tokens.add(new Token(TokenType.NUMBER, 22, 26, source));

        SyntaxTree tree = null;
        try {
            tree = new SyntaxTree(tokens.iterator());
        } catch (SyntaxErrorException ignored) {

        }

        assertNull(tree);
    }
}
