package parser;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sophia Titova on 01.10.17.
 */
public class Parser {
    LexicalAnalyzer lex;

    public Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return E();
    }

    private Tree E() throws ParseException {
        if (cmp(Token.LPAREN, Token.VAR)) {
            return new Tree("E", A(), EPrime());
        }
        throw new UnexpectedTokenException(lex.getCurChar() + "", lex.getCurPos());
    }

    private Boolean cmp(Token... t) {
        for (Token a : Arrays.asList(t))
            if (lex.getCurToken() == a) return true;
        return false;
    }

    private Tree EPrime() throws ParseException {
        if (cmp(Token.OR)) {
            lex.nextToken();
            return new Tree("EPrime", A(), EPrime());
        } else if (cmp(Token.END, Token.RPAREN)) {
            return new Tree("EPrime");
        }
        throw new UnexpectedTokenException(lex.getCurChar() + "", lex.getCurPos());
    }

    private Tree A() throws ParseException {
        if (cmp(Token.LPAREN, Token.VAR)) {
            return new Tree("A", C(), APrime());
        }
        throw new UnexpectedTokenException(lex.getCurChar() + "", lex.getCurPos());
    }

    private Tree APrime() throws ParseException {
        Token curToken = lex.getCurToken();
        if (cmp(Token.LPAREN, Token.VAR)) {
            return new Tree("APrime", C(), APrime());
        } else if (cmp(Token.OR, Token.RPAREN, Token.END)) {
            return new Tree("APrime");
        }
        throw new UnexpectedTokenException(lex.getCurChar() + "", lex.getCurPos());

    }

    private Tree C() throws ParseException {
        switch (lex.getCurToken()) {
            case VAR:
                lex.nextToken();
                return new Tree("C", new Tree(lex.getCurChar() + ""), CPrime());
            case LPAREN:
                lex.nextToken();
                Tree e = E();
                if (lex.getCurToken() != Token.RPAREN) {
                    throw new ParseException(") expected at position", lex.getCurPos());
                }
                lex.nextToken();
                return new Tree("C", new Tree("("), e, new Tree(")"), CPrime());
            default:
                throw new UnexpectedTokenException(lex.getCurChar() + "", lex.getCurPos());
        }
    }

    private Tree CPrime() throws ParseException {
        if (cmp(Token.STAR)) {
            lex.nextToken();
            return new Tree("CPrime", new Tree("*"), CPrime());
        } else if (cmp(Token.END, Token.VAR, Token.LPAREN, Token.RPAREN, Token.OR)) {
            return new Tree("CPrime");
        }
        throw new UnexpectedTokenException(lex.getCurChar() + "", lex.getCurPos());
    }

    public class Tree {
        private String node;
        private List<Tree> children;

        Tree(String node, Tree... children) {
            this.node = node;
            this.children = Arrays.asList(children);
        }

        Tree(String node) {
            this.node = node;
        }

        public List<Tree> getChildren() {
            return children;
        }

        public String getNode() {
            return node;
        }
    }
}
