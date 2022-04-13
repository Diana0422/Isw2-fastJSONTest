package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RunWith(Parameterized.class)
public class ParameterizedJSONArrayParseTest {
    private TypeReference<List<Map<String, Integer>>> tr;
    private String text;
    private int expectedValue1;
    private int expectedValue2;

    /* test class constructor */
    public ParameterizedJSONArrayParseTest(String text, int expectedValue1, int expectedValue2) {
        this.configure(text, expectedValue1, expectedValue2);
    }
    /* configure method */
    private void configure(String text, int expectedValue1, int expectedValue2) {
        this.text = text;
        this.tr = new TypeReference<List<Map<String, Integer>>>() {};
        this.expectedValue1 = expectedValue1;
        this.expectedValue2 = expectedValue2;
    }

    /* parameters assignation method */
    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {"[{id:123}]", 1, 123}
        });
    }

    @Test
    public void test_array() {
        List<Map<String, Integer>> array = JSON.parseObject(this.text, this.tr);
        Map<String, Integer> map = array.get(0);
        Assert.assertEquals(expectedValue1, array.size());
        Assert.assertEquals(expectedValue2, map.get("id").intValue());
    }
}
