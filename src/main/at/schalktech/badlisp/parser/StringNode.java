package at.schalktech.badlisp.parser;

public class StringNode implements SyntaxNode{

    private String string;

    public StringNode(String string) {
        this.string = string;
    }

    @Override
    public SyntaxNode getChild(int num) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public void addChild(SyntaxNode node) {

    }

    @Override
    public void accept(SyntaxNodeVisitor visitor) {
        visitor.visitStringNode(this);
    }

    public String getString() {
        return string;
    }
}
