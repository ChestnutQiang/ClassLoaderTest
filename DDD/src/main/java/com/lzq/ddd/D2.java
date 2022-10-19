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
public class D2 {

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
    public static void d2() {

        System.out.println("The d2 method called");

        ClassLoader classloader2 = ClassLoaderFactory.createClassLoaderChain();
        ClassLoader classloader1 = classloader2.getParent();

        try {
            Class<?> a = classloader1.loadClass("com.lzq.aaa.A0");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Thread.currentThread().setContextClassLoader(classloader1);
        Thread.currentThread().setContextClassLoader(classloader2);

        D3 d3 = new D3();

        D4.d4();
        // ① 如果上下文类加载器是 classloader1 无法发现类加载器， 因为 classloader1 里面没有 SPI 实现类
        // ② 如果上下文类加载器是 classloader2 是可以获得 SPI 实现类
        ServiceLoader<Alphabet> alphabet = ServiceLoader.load(Alphabet.class);
        // ServiceLoader<Alphabet> alphabet = ServiceLoader.load(Alphabet.class, classloader2); //
        // no
        // ServiceLoader<Alphabet> alphabet = ServiceLoader.load(Alphabet.class, cl2); // no
        for (Alphabet s : alphabet) {
            s.pronounce();
        }
    }
}
