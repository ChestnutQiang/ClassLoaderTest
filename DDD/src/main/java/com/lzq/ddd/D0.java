package com.lzq.ddd;

import com.lzq.in.Alphabet;
import com.lzq.util.ClassLoaderFactory;

import java.util.ServiceLoader;

/**
 * @author wujuan
 * @version 1.0
 * @date 2022/10/18 23:13 星期二
 * @email wujuan@dtstack.com
 * @company www.dtstack.com
 */
public abstract class D0 {

    /*
              BootstrapClassloader
                      |
              ExtClassloader
                      |
              AppClassloader
                      |
              classloader1    ---------------->   AAA,CCC,DDD
                      |
              classloader1    ---------------->   AAA,CCC,DDD
                      |
              classloader2    ---------------->   BBB,CCC,DDD
    */
    public void d0() {

        System.out.println("The d0 method called");

        ClassLoader classloader2 = ClassLoaderFactory.createClassLoaderChain();
        ClassLoader classloader1 = classloader2.getParent();

        try {
            Class<?> a = classloader1.loadClass("com.lzq.aaa.A0");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Thread.currentThread().setContextClassLoader(classloader1);
        Thread.currentThread().setContextClassLoader(classloader2);

        ClassLoader classLoader = this.getClass().getClassLoader();

        d00();
    }

    protected abstract void d00();
}
