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
public class _06_demo {
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
                        |
                classloader3    ---------------->   AAA,CCC,DDD
     */
    public static void main(String[] args) throws Exception {


        ClassLoader classloader3 = ClassLoaderFactory.createClassLoaderChain4();
        ClassLoader classloader2 = classloader3.getParent();
        ClassLoader classloader1 = classloader2.getParent();

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        //Thread.currentThread().setContextClassLoader(classloader3);

        Class<?> D1Class = Class.forName("com.lzq.ddd.D3", false, classloader2);
        Object d3 = D1Class.newInstance();
        Method mainMethod = D1Class.getMethod("d3", ClassLoader.class);
        mainMethod.invoke(d3, classloader3);

        Thread.currentThread().setContextClassLoader(contextClassLoader);

    }
}
