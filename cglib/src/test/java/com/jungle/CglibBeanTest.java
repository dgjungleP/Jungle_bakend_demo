package com.jungle;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.beans.ImmutableBean;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class CglibBeanTest {

    @Test
    public void clazzCreateTest() throws Exception {
        BeanGenerator generator = new BeanGenerator();
        generator.addProperty("name", String.class);
        Object o = generator.create();
        Method setName = o.getClass().getMethod("setName", String.class);
        setName.invoke(o, "jungle");
        Method getName = o.getClass().getMethod("getName");
        System.out.println(getName.invoke(o));
        System.out.println(o.getClass());
    }

    @Test
    public void clazzReadOnlyTest() {

        Student student = new Student();
        student.setName("Jungle");
        Student immutableStudent = (Student) ImmutableBean.create(student);
        System.out.println(immutableStudent.getName());
        student.setName("Jungle.Deng");
        System.out.println(immutableStudent.getName());
        immutableStudent.setName("Jungle.G.Deng");
        System.out.println(immutableStudent.getName());
    }

    @Test
    public void clazzToMapTest() throws Exception {
        BeanGenerator generator = new BeanGenerator();
        generator.addProperty("name", String.class);
        generator.addProperty("age", Integer.class);

        Object o = generator.create();

        Method setName = o.getClass().getMethod("setName", String.class);
        Method setAge = o.getClass().getMethod("setAge", Integer.class);

        setName.invoke(o, "Jungle");
        setAge.invoke(o, 21);

        BeanMap beanMap = BeanMap.create(o);

        System.out.println("beanMap.get(\"age\") = " + beanMap.get("age"));
        System.out.println(beanMap);
        System.out.println(o);

    }


}
