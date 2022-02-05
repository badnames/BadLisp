package at.schalktech.badlisp.parser.syntaxtree;

public interface SyntaxNodeVisitor {
    void visitListNode(ListNode listNode);
    void visitSymbolNode(SymbolNode symbolNode);
    void visitIntegerNode(IntegerNode integerNode);
    void visitRationalNode(RationalNode rationalNode);
    void visitStringNode(StringNode stringNode);
}
