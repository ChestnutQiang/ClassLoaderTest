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
public class _04_demo {
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

        Class<?> D1Class = Class.forName("com.lzq.ddd.D2", false, classloader1);
        Method mainMethod = D1Class.getMethod("d2");
        mainMethod.invoke(null);

        Thread.currentThread().setContextClassLoader(contextClassLoader);

    }
}
