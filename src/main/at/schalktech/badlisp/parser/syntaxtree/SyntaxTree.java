package at.schalktech.badlisp.parser.syntaxtree;

import at.schalktech.badlisp.parser.lexer.Lexer;
import at.schalktech.badlisp.parser.lexer.Token;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class SyntaxTree {
    private SyntaxNode root = new ListNode();

    public SyntaxTree(Lexer lexer) throws SyntaxErrorException {
        Deque<SyntaxNode> treeStack = new LinkedList<>();
        treeStack.push(root);

        while(lexer.hasNext()) {
            Token token = lexer.next();

            switch (token.getType()) {
                case LIST_START -> {
                    ListNode listnode = new ListNode();
                    assert treeStack.peek() != null;
                    treeStack.peek().addChild(listnode);
                    treeStack.push(listnode);
                }

                case LIST_END -> {
                    if (!(treeStack.peek() instanceof ListNode)
                            || treeStack.peek() == root)
                        throw new SyntaxErrorException(token.getPosition(), "Unexpected token ')'.");
                    treeStack.pop();
                }

                case SYMBOL -> {
                    if (treeStack.peek() == root)
                        throw new SyntaxErrorException(token.getPosition(), "Unexpected Symbol " + token.getContent());

                    SymbolNode symbolNode = new SymbolNode(token.getContent());

                    assert treeStack.peek() != null;
                    treeStack.peek().addChild(symbolNode);
                }

                case NUMBER -> {
                    if (treeStack.peek() == root)
                        throw new SyntaxErrorException(token.getPosition(), "Unexpected Number " + token.getContent());

                    Optional<Integer> optionalInteger = parseInteger(token);
                    Optional<Double> optionalDouble = parseDouble(token);

                    SyntaxNode node;

                    if (optionalInteger.isPresent())
                        node = new IntegerNode(optionalInteger.get());
                    else if (optionalDouble.isPresent())
                        node = new RationalNode(optionalDouble.get());
                    else
                        throw new SyntaxErrorException(token.getPosition(), "Error parsing number" + token.getContent());

                    assert treeStack.peek() != null;
                    treeStack.peek().addChild(node);
                }

                case STRING -> {
                    if (treeStack.peek() == root)
                        throw new SyntaxErrorException(token.getPosition(), "Unexpected String " + token.getContent());

                    //TODO: add support for escape sequences
                    String string = token.getContent().substring(1, token.getContent().length() - 1);
                    StringNode node = new StringNode(string);

                    assert treeStack.peek() != null;
                    treeStack.peek().addChild(node);
                }
            }
        }
    }

    public void visitAll(SyntaxNodeVisitor visitor) {

    }

    private Optional<Integer> parseInteger(Token token) {
        int number;

        try {
            number = Integer.parseInt(token.getContent());
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        return Optional.of(number);
    }

    private Optional<Double> parseDouble(Token token) {
        double number;

        try {
            number = Double.parseDouble(token.getContent());
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        return Optional.of(number);
    }
}
