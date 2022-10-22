package com.lzq.example;

import com.lzq.in.Alphabet;
import com.lzq.ccc.C1;
import com.lzq.ccc.C2;
import com.lzq.util.ClassLoaderFactory;

import java.util.ServiceLoader;

/**
 * @author wujuan
 * @version 1.0
 * @date 2022/10/18 23:03 星期二
 * @email wujuan@dtstack.com
 * @company www.dtstack.com
 */
public class _01_demo {

    /*
                BootstrapClassloader
                        |
                ExtClassloader
                        |
                AppClassloader
                    /
            classloader1
                /
        classloader2
    */
    public static void main(String[] args) throws Exception {

        ClassLoader classloader2 = ClassLoaderFactory.createClassLoaderChain();
        ClassLoader classloader1 = classloader2.getParent();

        ClassLoader appClassLoader = Thread.currentThread().getContextClassLoader();

        Class<?> a = classloader1.loadClass("com.lzq.aaa.A0");
        Class<?> b = classloader2.loadClass("com.lzq.bbb.B0");
        C1 c1 = new C1();
        Thread.currentThread().setContextClassLoader(classloader1);
        C2 c2 = null;
        c2 = new C2();
        try {
            Class.forName("com.lzq.aaa.A01"); // no
        } catch (Exception e) {
            System.out.println(e);
        }
        Class.forName("com.lzq.ccc.C3"); // yes
        Class.forName("com.lzq.bbb.B4", false, classloader2);

        // ServiceLoader<Alphabet> alphabet = ServiceLoader.load(Alphabet.class); // yes
        // Alphabet 是 AppClassloader 加载的 ServiceLoader 就要用 AppClassloader
        ServiceLoader<Alphabet> alphabet =
                ServiceLoader.load(Alphabet.class, appClassLoader); // yes
        // ServiceLoader<Alphabet> alphabet = ServiceLoader.load(Alphabet.class, cl2); // no
        for (Alphabet s : alphabet) {
            s.pronounce();
        }
    }
}
