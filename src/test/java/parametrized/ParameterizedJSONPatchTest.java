package parametrized;

import com.alibaba.fastjson.JSONPatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class ParameterizedJSONPatchTest {
    private String original;
    private String patch;
    private String expectedResult;

    /* test class constructor */
    public ParameterizedJSONPatchTest(String original, String patch, String expected) {
        this.configure(original, patch, expected);
    }

    /* configure method */
    private void configure(String original, String patch, String expectedResult) {
        this.original = original;
        this.patch = patch;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                // SC: 42% BC: 33%
                {"{\n" + "  \"baz\": \"qux\",\n" + "  \"foo\": \"bar\"\n" + "}", "[\n" +
                "  { \"op\": \"replace\", \"path\": \"/baz\", \"value\": \"boo\" },\n" +
                "  { \"op\": \"add\", \"path\": \"/hello\", \"value\": [\"world\"] },\n" +
                "  { \"op\": \"remove\", \"path\": \"/foo\" }\n" +
                "]", "{\"baz\":\"boo\",\"hello\":[\"world\"]}"},
                // SC: 38% BC: 22%
                {"{}", "{ \"op\": \"add\", \"path\": \"/a/b/c\", \"value\": [ \"foo\", \"bar\" ] }", "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}"},
                // SC: 36% BC: 22%
                {"{}", "{ \"op\": \"remove\", \"path\": \"/a/b/c\" }\n", "{}"},
                // SC: 36% BC: 22%
                {"{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}", "{ \"op\": \"remove\", \"path\": \"/a/b/c\" }\n", "{\"a\":{\"b\":{}}}"},
                // SC: 38% BC: 22%
                {"{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}", "{ \"op\": \"replace\", \"path\": \"/a/b/c\", \"value\": 42 }", "{\"a\":{\"b\":{\"c\":42}}}"},
                // SC: 51% BC: 33%
                {"{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",  "{ \"op\": \"move\", \"from\": \"/a/b/c\", \"path\": \"/a/b/d\" }", "{\"a\":{\"b\":{\"d\":[\"foo\",\"bar\"]}}}"},
                // SC: 46% BC: 27%
                {"{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}", "{ \"op\": \"copy\", \"from\": \"/a/b/c\", \"path\": \"/a/b/e\" }", "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"],\"e\":[\"foo\",\"bar\"]}}}"},
                // SC: 39% BC: 22%
                {"{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}", "{ \"op\": \"test\", \"path\": \"/a/b/c\", \"value\": \"foo\" }", "false"}
        });
    }

    @Test
    public void runTest() {
        String result = JSONPatch.apply(this.original, this.patch);
        assertEquals(this.expectedResult, result);
    }
}
