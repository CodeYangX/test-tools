package org.yzy.bean.unit;

import org.junit.Test;

/**
 * Created by yangzhiyong on 16/4/28.
 */
public class GeneratorTest {
    @Test
    public void test() throws InstantiationException, IllegalAccessException {
        Generator generator = Generator.getInstance();
        String result = generator.handle(TestBean.class);
        System.out.println(result);
    }
}
