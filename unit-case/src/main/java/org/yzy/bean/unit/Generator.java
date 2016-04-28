package org.yzy.bean.unit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangzhiyong on 16/4/28.
 */
public class Generator {

    private static Generator generator = new Generator();

    private Generator() {
    }

    public static Generator getInstance() {
        return generator;
    }

    public String handle(final Class clz) throws IllegalAccessException, InstantiationException {
        Object object = clz.newInstance();

        String clzStr = clz.getSimpleName();
        if (clzStr.length() == 1) {
            clzStr = clzStr.toLowerCase();
        } else {
            clzStr = clzStr.substring(0, 1).toLowerCase() + clzStr.substring(1);
        }

        Method[] methods = clz.getMethods();
        Field[] fields = clz.getDeclaredFields();

        Map<String, Method> lowCase2Normal = new HashMap<>();
        for (Method m : methods) {
            String mStr = m.getName().toLowerCase();
            if (mStr.startsWith("set")) {
                lowCase2Normal.put(mStr.toLowerCase(), m);
            }
        }
        StringBuffer result = new StringBuffer();
        result.append(clz.getSimpleName() + " " + clzStr + "=new " + clz.getSimpleName() + "();\n");

        int number = 1;
        for (Field f : fields) {
            String fStr = f.getName();
            Method method = lowCase2Normal.get("set" + fStr.toLowerCase());
            if (method != null) {
                Class typeClz = f.getType();

                String prefix = clzStr + "." + method.getName() + "(";

                if (Number.class.isAssignableFrom(typeClz)) {
                    if (Double.class == typeClz) {
                        result.append(prefix + number + "D);\n");
                    } else if (Float.class == typeClz) {
                        result.append(prefix + number + "F);\n");
                    } else if (Long.class == typeClz) {
                        result.append(prefix + number + "L);\n");
                    } else {
                        result.append(prefix + number + ");\n");
                    }
                    number++;
                } else if (String.class == typeClz) {
                    result.append(prefix + fStr + ");\n");
                } else if (Date.class == typeClz) {
                    result.append(prefix + "new Date());\n");
                }
            }

        }

        return result.toString();
    }
}
