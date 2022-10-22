package com.lzq.example;

import com.lzq.util.ClassLoaderFactory;

import java.lang.reflect.Method;

/**
 * @author wujuan
 * @version 1.0
 * @date 2022/10/18 23:03 星期二
 * @email wujuan@dtstack.com
 * @company www.dtstack.com
 */
public class _05_demo {
    /*
                BootstrapClassloader
                        |
                ExtClassloader
                        |
                AppClassloader
                        |
                classloader1    ---------------->   AAA,CCC,DDD
                        |
                classloader2    ---------------->   BBB,CCC,DDD
     */
    public static void main(String[] args) throws Exception {

        ClassLoader classloader2 = ClassLoaderFactory.createClassLoaderChain();
        ClassLoader classloader1 = classloader2.getParent();


        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(classloader1);

        Class<?> D1Class = Class.forName("com.lzq.ddd.D1", false, classloader1);
        Object d1 = D1Class.newInstance();
        Method mainMethod = D1Class.getMethod("d0");
        mainMethod.invoke(d1);

        Thread.currentThread().setContextClassLoader(contextClassLoader);

    }
}
