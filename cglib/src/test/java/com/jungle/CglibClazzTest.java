package com.jungle;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import net.sf.cglib.reflect.MethodDelegate;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;

import javax.sound.midi.Soundbank;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;


public class CglibClazzTest {

    @Test
    public void createInterfaceTest() {

        Signature hello = new Signature("hello", Type.INT_TYPE, new Type[]{Type.getType(String.class)});

        InterfaceMaker maker = new InterfaceMaker();
        maker.add(hello, new Type[0]);
        Class aClass = maker.create();
        System.out.println(aClass);
    }

    @Test
    public void methodDelegateTest() throws Exception {

        BeanGenerator generator = new BeanGenerator();
        generator.addProperty("value", String.class);
        Object o = generator.create();
        Method setValue = o.getClass().getMethod("setValue", String.class);

        setValue.invoke(o, "Jungle");

        ProxyMethod method = (ProxyMethod) MethodDelegate.create(o, "getValue", ProxyMethod.class);
        System.out.println(method.getValueFromProxy());


    }

    @Test
    public void fastClazzTest() throws Exception {
        FastClass fastClass = FastClass.create(Student.class);

        FastMethod fastMethod = fastClass.getMethod("getName", new Class[0]);

        Student student = new Student();
        student.setName("Jungle");

        System.out.println("fastMethod.invoke(student,new Object[0]) = " + fastMethod.invoke(student, new Object[0]));


    }

}
