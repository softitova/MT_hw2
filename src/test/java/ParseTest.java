import org.junit.Test;
import parser.ParseException;
import parser.Parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sophia Titova on 01.10.17.
 */
public class ParseTest {


    private InputStream getInputStream(String s) {
        try {
            return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void baseTest() throws ParseException {
        List<String> baseList = new ArrayList<>();
        baseList.add("a");
        baseList.add("(a)");
        baseList.add("ab");
        for (String s : baseList) {
            new Parser().parse(getInputStream(s));
        }
    }

    @Test
    public void varTest() throws ParseException {
        List<String> baseList = new ArrayList<>();
        baseList.add("abcd");
        baseList.add("aa");
        baseList.add("a");
        for (String s : baseList) {
            new Parser().parse(getInputStream(s));
        }
    }

    @Test
    public void orTest() throws ParseException {
        List<String> baseList = new ArrayList<>();
        baseList.add("ab|cd");
        baseList.add("a|a");
        baseList.add("a|aa");
        for (String s : baseList) {
            new Parser().parse(getInputStream(s));
        }
    }

    @Test
    public void parentesisTest() throws ParseException {
        List<String> baseList = new ArrayList<>();
        baseList.add("ab(c)d");
        baseList.add("(a(a))");
        baseList.add("a((a))a");
        baseList.add("(a((a))(a))");
        for (String s : baseList) {
            new Parser().parse(getInputStream(s));
        }
    }

    @Test
    public void kliniTest() throws ParseException {
        List<String> baseList = new ArrayList<>();
        baseList.add("ab(c)*d");
        baseList.add("(a(a))*");
        baseList.add("(a*)*");
        baseList.add("(a*)(b*(c)*)*");
        for (String s : baseList) {
            new Parser().parse(getInputStream(s));
        }
    }

    @Test
    public void hardTest() throws ParseException {
        List<String> baseList = new ArrayList<>();
        baseList.add("ab(c)*d|s");
        baseList.add("(a(a|sd))*");
        baseList.add("(a|as|v*)*");
        for (String s : baseList) {
            new Parser().parse(getInputStream(s));
        }
    }
}