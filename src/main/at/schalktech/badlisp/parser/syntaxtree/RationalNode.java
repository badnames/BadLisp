package at.schalktech.badlisp.parser.syntaxtree;

public class RationalNode implements SyntaxNode {

    private double value;

    public RationalNode(double value) {
        this.value = value;
    }

    public double getValue() {
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

    }

}
