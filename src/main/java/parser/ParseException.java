package parser;

/**
 * Created by Sophia Titova on 01.10.17.
 */
public class ParseException extends Exception {
    String msg;
    int pos;

    public ParseException(String message, int curPos) {
        msg = message;
        pos = curPos;
    }
}
