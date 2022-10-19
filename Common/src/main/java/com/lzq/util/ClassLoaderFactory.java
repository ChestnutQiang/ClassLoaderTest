package com.lzq.util;

import com.lzq.classloader.NoParentDelegateClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author wujuan
 * @version 1.0
 * @date 2022/10/19 10:35 星期三
 * @email wujuan@dtstack.com
 * @company www.dtstack.com
 */
public class ClassLoaderFactory {


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
    public static ClassLoader createClassLoaderChain() {

        File AAAJar = new File("./AAA/target/AAA-1.0-SNAPSHOT.jar");
        File BBBJar = new File("./BBB/target/BBB-1.0-SNAPSHOT.jar");
        File CCCJar = new File("./CCC/target/CCC-1.0-SNAPSHOT.jar");
        File DDDJar = new File("./DDD/target/DDD-1.0-SNAPSHOT.jar");

        System.out.println("Is file " + AAAJar.getName() + " exists ? " + AAAJar.exists());
        System.out.println("Is file " + BBBJar.getName() + " exists ? " + BBBJar.exists());
        System.out.println("Is file " + DDDJar.getName() + " exists ? " + DDDJar.exists());

        URL AAAJarURL1 = null;
        URL AAAJarURL2 = null;
        URL AAAJarURL3 = null;
        URL AAAJarURL4 = null;
        try {
            AAAJarURL1 = AAAJar.toURI().toURL();
            AAAJarURL2 = BBBJar.toURI().toURL();
            AAAJarURL3 = CCCJar.toURI().toURL();
            AAAJarURL4 = DDDJar.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        ClassLoader appClassLoader = Thread.currentThread().getContextClassLoader();
        URLClassLoader classloader1 = new NoParentDelegateClassLoader(new URL[]{AAAJarURL1, AAAJarURL3, AAAJarURL4}, appClassLoader);
        URLClassLoader classloader2 = new NoParentDelegateClassLoader(new URL[]{AAAJarURL2, AAAJarURL3, AAAJarURL4}, classloader1);

        return classloader2;
    }
}
