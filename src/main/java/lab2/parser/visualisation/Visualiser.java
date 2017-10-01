package lab2.parser.visualisation;

import lab2.parser.ParseException;
import lab2.parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Sophia Titova on 01.10.17.
 */
public class Visualiser {
    static final String TAB = "  ";

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        PrintWriter out = new PrintWriter(new File("output.txt"));
        Parser.Tree t = new Parser().parse(new FileInputStream(new File("/Users/macbook/IdeaProjects/pp/titova/src/test/input.txt")));
        printChildren(t, out, "");
        out.close();
        // printChildren(t.getChildren());

    }

    private static void printChildren(Parser.Tree t, PrintWriter out, String tab) {
        out.println(tab + t.getNode() + " : ");

        final String curTab = tab + TAB;
        try {
            t.getChildren().forEach(it -> {
                printChildren(it, out, curTab);
            });
        } catch (NullPointerException e) {
//            System.out.println("");
        }
    }
}
