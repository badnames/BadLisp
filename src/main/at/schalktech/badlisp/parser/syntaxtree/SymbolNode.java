package at.schalktech.badlisp.parser.syntaxtree;

public class SymbolNode implements SyntaxNode {

    private String symbol;

    public SymbolNode(String symbol) {
        this.symbol = symbol;
    }

    // This Node cannot have any children
    @Override
    public SyntaxNode getChild(int num) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public void addChild(SyntaxNode node) {}

    @Override
    public void accept(SyntaxNodeVisitor visitor) {
        visitor.visitSymbolNode(this);
    }

    public String getSymbol() {
        return symbol;
    }
}
