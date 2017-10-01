package lab2.parser;

/**
 * Created by Sophia Titova on 01.10.17.
 */
class UnexpectedTokenException extends ParseException {
    UnexpectedTokenException(String message, int curPos) {
        super("Unexpected token " + message + " at pos : ", curPos);
    }
}
