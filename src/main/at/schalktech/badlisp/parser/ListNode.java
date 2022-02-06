package at.schalktech.badlisp.parser;

import java.util.LinkedList;
import java.util.List;

public class ListNode implements SyntaxNode {
    private List<SyntaxNode> children = new LinkedList();

    @Override
    public SyntaxNode getChild(int num) {
        return children.get(num);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public void addChild(SyntaxNode node) {
        children.add(node);
    }

    @Override
    public void accept(SyntaxNodeVisitor visitor) {
        visitor.visitListNode(this);
    }
}
