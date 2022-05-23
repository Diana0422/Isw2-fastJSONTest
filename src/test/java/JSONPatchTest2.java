import com.alibaba.fastjson.JSONPatch;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class JSONPatchTest2 {
    private String original;
    private String patch;
    private String expectedResult;

    /* test class constructor */
    public JSONPatchTest2() {
        this.configure(
                "{\n" + "  \"baz\": \"qux\",\n" + "  \"foo\": \"bar\"\n" + "}",
                "[\n" +
                        "  { \"op\": \"replace\", \"path\": \"/baz\", \"value\": \"boo\" },\n" +
                        "  { \"op\": \"add\", \"path\": \"/hello\", \"value\": [\"world\"] },\n" +
                        "  { \"op\": \"remove\", \"path\": \"/foo\" }\n" +
                        "]",
                "{\"baz\":\"boo\",\"hello\":[\"world\"]}"
        );
        this.configure(
                "{}",
                "{ \"op\": \"add\", \"path\": \"/a/b/c\", \"value\": [ \"foo\", \"bar\" ] }",
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}"
        );
        this.configure(
                "{}",
                "{ \"op\": \"remove\", \"path\": \"/a/b/c\" }\n",
                "{}"
        );
        this.configure(
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",
                "{ \"op\": \"remove\", \"path\": \"/a/b/c\" }\n",
                "{\"a\":{\"b\":{}}}"
        );

        this.configure(
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",
                "{ \"op\": \"replace\", \"path\": \"/a/b/c\", \"value\": 42 }",
                "{\"a\":{\"b\":{\"c\":42}}}"
        );
        this.configure(
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",
                "{ \"op\": \"move\", \"from\": \"/a/b/c\", \"path\": \"/a/b/d\" }",
                "{\"a\":{\"b\":{\"d\":[\"foo\",\"bar\"]}}}"
        );
        this.configure(
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"]}}}",
                "{ \"op\": \"copy\", \"from\": \"/a/b/c\", \"path\": \"/a/b/e\" }",
                "{\"a\":{\"b\":{\"c\":[\"foo\",\"bar\"],\"e\":[\"foo\",\"bar\"]}}}"
        );
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
        String result = JSONPatch.apply(this.original, this.patch);
        assertEquals(this.expectedResult, result);
    }
}