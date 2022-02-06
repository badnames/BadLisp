package at.schalktech.badlisp.parser;

public class SyntaxErrorException extends Exception {

    private int position;
    private String message;

    public SyntaxErrorException(int position, String message) {
        super();

        this.position = position;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Syntax error at or near position " + position + ": " + message;
    }
}
