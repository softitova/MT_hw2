package parser;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Sophia Titova on 01.10.17.
 */

enum Token {
    LPAREN, RPAREN, END, STAR, VAR, OR
}

class LexicalAnalyzer {
    InputStream is;
    private int curChar = 0;
    private char prevChar;
    private int curPos;
    private Token curToken;


    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            prevChar = (char) curChar;
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        if (Character.isLetter(curChar)) {
            nextChar();
            curToken = Token.VAR;
        } else {
            switch (curChar) {
                case '(':
                    nextChar();
                    curToken = Token.LPAREN;
                    break;
                case ')':
                    nextChar();
                    curToken = Token.RPAREN;
                    break;
                case '*':
                    nextChar();
                    curToken = Token.STAR;
                    break;
                case '|':
                    nextChar();
                    curToken = Token.OR;
                    break;
                case -1:
                    curToken = Token.END;
                    break;
                default:
                    throw new ParseException("Illegal character " + (char) curChar, curPos);
            }
        }
    }

    public Token getCurToken() {
        return curToken;
    }

    public int getCurPos() {
        return curPos;
    }

    public char getCurChar() {
        return (char) prevChar;
    }
}