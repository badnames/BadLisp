package at.schalktech.badlisp.parser;

public interface SyntaxNode {
    SyntaxNode getChild(int num);
    int getChildCount();
    void addChild(SyntaxNode node);
    void accept(SyntaxNodeVisitor visitor);
}
