import at.schalktech.badlisp.Lexer;
import at.schalktech.badlisp.Token;
import at.schalktech.badlisp.TokenType;
import org.junit.Test;
import static org.junit.Assert.*;

public class LexerTest {
    @Test
    public void testEmptyProgram() {
        Lexer lexer = new Lexer("");

        assertFalse(lexer.hasNext());
    }

    @Test
    public void testHelloWorld() {
        Lexer lexer = new Lexer("(print \"Hello World\")");

        Token token;
        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), "(");
        assertEquals(token.getType(), TokenType.LIST_START);

        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), "print");
        assertEquals(token.getType(), TokenType.SYMBOL);

        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), "\"hello world\"");
        assertEquals(token.getType(), TokenType.STRING);

        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), ")");
        assertEquals(token.getType(), TokenType.LIST_END);

    }

    @Test
    public void testInteger() {
        Lexer lexer = new Lexer("12345");

        Token token;
        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), "12345");
        assertEquals(token.getType(), TokenType.NUMBER);
    }

    @Test
    public void testDecimal() {
        Lexer lexer = new Lexer("123.456");

        Token token;
        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), "123.456");
        assertEquals(token.getType(), TokenType.NUMBER);
    }

    @Test
    public void testExponent() {
        Lexer lexer = new Lexer("123e456");

        Token token;
        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), "123e456");
        assertEquals(token.getType(), TokenType.NUMBER);
    }

    @Test
    public void testDecimalExponent() {
        Lexer lexer = new Lexer("123.456e789");

        Token token;
        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), "123.456e789");
        assertEquals(token.getType(), TokenType.NUMBER);
    }

    @Test
    public void testComments() {
        Lexer lexer = new Lexer("; Inline comment \n #|\n Multi Line\n|# (test)");

        Token token;
        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), "(");
        assertEquals(token.getType(), TokenType.LIST_START);

        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), "test");
        assertEquals(token.getType(), TokenType.SYMBOL);

        assertTrue(lexer.hasNext());
        token = lexer.next();
        assertEquals(token.getContent(), ")");
        assertEquals(token.getType(), TokenType.LIST_END);
    }
}
