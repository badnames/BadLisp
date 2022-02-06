package at.schalktech.badlisp.parser;

public class IntegerNode implements SyntaxNode {

    public int value;

    public IntegerNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
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
        visitor.visitIntegerNode(this);
    }
}
