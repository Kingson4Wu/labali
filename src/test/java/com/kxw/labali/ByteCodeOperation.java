package com.kxw.labali;

import com.kxw.labali.demo.ProxyExample;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * Created by kingsonwu on 18/5/15.
 */
public class ByteCodeOperation {

    public static void main(String[] args)
        throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {



        CtClass ctClass= ClassPool.getDefault().get("com.kxw.labali.demo.ProxyExample");

        //CtConstructor ctConstructor = new CtConstructor();

        //ctClass.addConstructor();

        String oldName="forJavassistTest";
        CtMethod ctMethod=ctClass.getDeclaredMethod(oldName);
        String newName=oldName+"$impl";
        ctMethod.setName(newName);
        CtMethod newMethod= CtNewMethod.copy(ctMethod, "forJavassistTest", ctClass, null);
        StringBuffer sb=new StringBuffer();
        sb.append("{System.out.println(\"22222222\");\n")
            .append(newName+"($$);\n")
            .append("System.out.println(\"11111111111\");\n}");
        newMethod.setBody(sb.toString());
        //增加新方法
        ctClass.addMethod(newMethod);
        //类已经更改，注意不能使用A a=new A();，因为在同一个classloader中，不允许装载同一个类两次
        ProxyExample a=(ProxyExample)ctClass.toClass().newInstance();
        a.print();

        //new ProxyExample().print();
    }
}
