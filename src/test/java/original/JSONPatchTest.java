package original;

import com.alibaba.fastjson.JSONPatch;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class JSONPatchTest {
    private String original;
    private String patch;
    private String expectedResult;

    /* test class constructor */
    public JSONPatchTest() {
        // 1: SC 42% BC 33%
        this.configure(
                "{\n" + "  \"baz\": \"qux\",\n" + "  \"foo\": \"bar\"\n" + "}",
                "[\n" +
                        "  { \"op\": \"replace\", \"path\": \"/baz\", \"value\": \"boo\" },\n" +
                        "  { \"op\": \"add\", \"path\": \"/hello\", \"value\": [\"world\"] },\n" +
                        "  { \"op\": \"remove\", \"path\": \"/foo\" }\n" +
                        "]",
                "{\"baz\":\"boo\",\"hello\":[\"world\"]}"
        );
        // 2: SC 38% BC 22%
        this.configure(
                "{}",
                "{ \"op\": \"add\", \"path\": \"/a/b/c\", \"value\": [ \"foo\", \"bar\" ] }",
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}"
        );
        // 3: SC 36% SC 22%
        this.configure(
                "{}",
                "{ \"op\": \"remove\", \"path\": \"/a/b/c\" }\n",
                "{}"
        );
        // 4: SC 36% SC 22%
        this.configure(
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",
                "{ \"op\": \"remove\", \"path\": \"/a/b/c\" }\n",
                "{\"a\":{\"b\":{}}}"
        );

        // 5: SC 38% BC 22%
        this.configure(
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",
                "{ \"op\": \"replace\", \"path\": \"/a/b/c\", \"value\": 42 }",
                "{\"a\":{\"b\":{\"c\":42}}}"
        );

        // 6: SC 51% BC 33%
        this.configure(
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",
                "{ \"op\": \"move\", \"from\": \"/a/b/c\", \"path\": \"/a/b/d\" }",
                "{\"a\":{\"b\":{\"d\":[\"foo\",\"bar\"]}}}"
        );

        // 7: SC 46% BC 27%
        this.configure(
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",
                "{ \"op\": \"copy\", \"from\": \"/a/b/c\", \"path\": \"/a/b/e\" }",
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"],\"e\":[\"foo\",\"bar\"]}}}"
        );

        // 8: SC 39% BC 22%
        this.configure(
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",
                "{ \"op\": \"test\", \"path\": \"/a/b/c\", \"value\": \"foo\" }",
                "false"
        );
    }

    /* configure method */
    private void configure(String original, String patch, String expectedResult) {
        this.original = original;
        this.patch = patch;
        this.expectedResult = expectedResult;
    }

    @Test
    public void runTest() {
//        String result = JSONPatch.apply(this.original, this.patch);
//        assertEquals(this.expectedResult, result);
    }
}