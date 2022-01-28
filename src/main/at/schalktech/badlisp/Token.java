package at.schalktech.badlisp;

public class Token {

    private int startPosition;
    private int endPosition;
    private TokenType type;
    private String source;

    public Token(TokenType type, int start, int end, String source) {
        assert (start >= 0);
        assert (end > 0);
        assert (start < end);

        this.startPosition = start;
        this.endPosition = end;
        this.type = type;
        this.source = source;
    }

    public TokenType getType() {
        return type;
    }

    public int getPosition() {
        return startPosition;
    }

    public String getContent() {
        return source.substring(startPosition, endPosition);
    }

    @Override
    public String toString() {
        return "Token{" +
                "startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", type=" + type +
                ", content='" + getContent() + '\'' +
                '}';
    }
}
