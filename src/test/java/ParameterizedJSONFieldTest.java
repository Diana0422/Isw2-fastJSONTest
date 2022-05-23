import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParameterizedJSONFieldTest {
    private String expectedResult;
    private int id;
    private String name;
    private VO vo;

    /* test class constructor */
    public ParameterizedJSONFieldTest(int id, String name, String expectedResult) {
        this.configure(id, name, expectedResult);
    }

    /* configure method */
    private void configure(int id, String name, String expectedResult) {
        this.id = id;
        this.name = name;
        this.vo = new VO();
        this.vo.setId(this.id);
        this.vo.setName(this.name);
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {123, "xx", "{\"id\":123}"}
        });
    }

    @Test
    public void test_jsonField() {
        String text = JSON.toJSONString(vo);
        Assert.assertEquals(this.expectedResult, text);
    }


    public static class VO {
        private int id;

        @JSONField(serialize=false)
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
