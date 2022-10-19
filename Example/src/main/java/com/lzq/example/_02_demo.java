package com.lzq.example;

import com.lzq.Alphabet;
import com.lzq.classloader.NoParentDelegateClassLoader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ServiceLoader;

/**
 * @author wujuan
 * @version 1.0
 * @date 2022/10/18 23:03 星期二
 * @email wujuan@dtstack.com
 * @company www.dtstack.com
 */
public class _02_demo {
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
        File AAAJar = new File("./AAA/target/AAA-1.0-SNAPSHOT.jar");
        File BBBJar = new File("./BBB/target/BBB-1.0-SNAPSHOT.jar");
        File CCCJar = new File("./CCC/target/CCC-1.0-SNAPSHOT.jar");
        System.out.println("Is file " + AAAJar.getName() + " exists ? " + AAAJar.exists());
        System.out.println("Is file " + BBBJar.getName() + " exists ? " + BBBJar.exists());
        System.out.println("Is file " + CCCJar.getName() + " exists ? " + CCCJar.exists());

        URL AAAJarURL1 = AAAJar.toURI().toURL();
        URL AAAJarURL2 = BBBJar.toURI().toURL();
        URL AAAJarURL3 = CCCJar.toURI().toURL();
        ClassLoader appClassLoader = Thread.currentThread().getContextClassLoader();
        URLClassLoader classloader1 = new NoParentDelegateClassLoader(new URL[]{AAAJarURL1, AAAJarURL3}, appClassLoader);
        URLClassLoader classloader2 = new NoParentDelegateClassLoader(new URL[]{AAAJarURL2, AAAJarURL3}, classloader1);


        Class<?> a = classloader1.loadClass("com.lzq.A0");
        Thread.currentThread().setContextClassLoader(classloader1);
        //Thread.currentThread().setContextClassLoader(classloader2);

        ServiceLoader<Alphabet> alphabet = ServiceLoader.load(Alphabet.class); // no
        //ServiceLoader<Alphabet> alphabet = ServiceLoader.load(Alphabet.class, classloader2); // no
        //ServiceLoader<Alphabet> alphabet = ServiceLoader.load(Alphabet.class, cl2); // no
        for (Alphabet s : alphabet) {
            s.pronounce();
        }

        // 因为是不同的类加载器
        System.out.println();
    }
}
