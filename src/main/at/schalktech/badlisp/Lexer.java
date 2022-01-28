package at.schalktech.badlisp;

import java.util.Iterator;

public class Lexer implements Iterator<Token> {

    private final String source;
    private int index = 0;

    public Lexer(String source) {
        // lisp is case insensitive
        this.source = source.toLowerCase();
    }

    @Override
    public boolean hasNext() {
        incrementUntilFalse(Character::isWhitespace);
        return index < source.length();
    }

    @Override
    public Token next() {
        Token token;

        incrementUntilFalse(Character::isWhitespace);

        //prevent IndexOutOfBoundsExceptions when hasNext() has not been called.
        if (index >= source.length())
            return null;

        //ignore comments
        if (source.charAt(index) == ';') {
            while(source.charAt(index) != '\n')
                index++;
            incrementUntilFalse(Character::isWhitespace);
        }

        if (source.charAt(index) == '#'
                && source.charAt(index + 1) == '|') {
            index += 2;

            while(source.charAt(index) != '|'
                    && source.charAt((index + 1)) != '#')
                index++;

            // skip over the remaining "|#" sequence
            index += 2;
            incrementUntilFalse(Character::isWhitespace);
        }

        // filter out one token at a time
        if (source.charAt(index) == '(') {
            token = new Token(TokenType.LIST_START, index, index + 1, source);
            index++;
        }

        else if (source.charAt(index) == ')') {
            token = new Token(TokenType.LIST_END, index, index + 1, source);
            index++;
        }

        else if (source.charAt(index) == '"') {
            int startIndex = index;

            // Avoid looking at the same quote twice
            index++;

            incrementUntilFalse((c) -> c != '"');

            token = new Token(TokenType.STRING, startIndex, index + 1, source);
            index++;
        }

        else if (Character.isDigit(source.charAt(index))) {
            int startIndex = index;

            incrementUntilFalse(Character::isDigit);

            // floating point numbers are also allowed
            if (checkAndIncrementIfTrue((c) -> c == '.')) {
                incrementUntilFalse(Character::isDigit);
            }

            // exponent
            if (checkAndIncrementIfTrue((c) -> c == 'e')) {
                incrementUntilFalse(Character::isDigit);
            }

            token = new Token(TokenType.NUMBER, startIndex, index, source);
        }

        else {
            int startIndex = index;

            incrementUntilFalse(Lexer::allowedInSymbol);

            token = new Token(TokenType.SYMBOL, startIndex, index, source);
        }

        return token;

    }

    private interface CharChecker {
        boolean check(char c);
    }

    private void incrementUntilFalse(CharChecker checker) {
        while (index < source.length() && checker.check(source.charAt(index)))
            index++;
    }

    private boolean checkAndIncrement(CharChecker checker) {
        return (index < source.length()) && checker.check(source.charAt(index++));
    }

    private boolean checkAndIncrementIfTrue(CharChecker checker) {
        boolean value = (index < source.length()) && checker.check(source.charAt(index));

        if (value) index++;

        return value;
    }

    private static boolean allowedInSymbol(char c) {
        return !Character.isWhitespace(c)
                && c != '('
                && c != ')'
                && c != ';'
                && c != '#';
    }

}
