package at.schalktech.badlisp.parser.syntaxtree;

public interface SyntaxNode {
    SyntaxNode getChild(int num);
    int getChildCount();
    void addChild(SyntaxNode node);
    void accept(SyntaxNodeVisitor visitor);
}
