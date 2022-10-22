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
    public static ClassLoader createClassLoaderChain3() {

        File AAAJar = new File("./AAA/target/AAA-1.0-SNAPSHOT.jar");
        File BBBJar = new File("./BBB/target/BBB-1.0-SNAPSHOT.jar");
        File CCCJar = new File("./CCC/target/CCC-1.0-SNAPSHOT.jar");
        File DDDJar = new File("./DDD/target/DDD-1.0-SNAPSHOT.jar");

        System.out.println("Is file " + AAAJar.getName() + " exists ? " + AAAJar.exists());
        System.out.println("Is file " + BBBJar.getName() + " exists ? " + BBBJar.exists());
        System.out.println("Is file " + DDDJar.getName() + " exists ? " + DDDJar.exists());

        URL AAAJarURL;
        URL BBBJarURL;
        URL CCCJarURL;
        URL DDDJarURL;
        try {
            AAAJarURL = AAAJar.toURI().toURL();
            BBBJarURL = BBBJar.toURI().toURL();
            CCCJarURL = CCCJar.toURI().toURL();
            DDDJarURL = DDDJar.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        ClassLoader appClassLoader = Thread.currentThread().getContextClassLoader();
        URLClassLoader classloader1 = new NoParentDelegateClassLoader(new URL[]{AAAJarURL, CCCJarURL, DDDJarURL}, appClassLoader);
        URLClassLoader classloader2 = new NoParentDelegateClassLoader(new URL[]{BBBJarURL, CCCJarURL, DDDJarURL}, classloader1);
        URLClassLoader classloader3 = new NoParentDelegateClassLoader(new URL[]{AAAJarURL, CCCJarURL, DDDJarURL}, classloader2);

        return classloader3;
    }


    /*
            BootstrapClassloader
                    |
            ExtClassloader
                    |
            AppClassloader
                    |
            classloader1    ---------------->   AAA,CCC,DDD,InterfaceModule
                    |
            classloader2    ---------------->   BBB,CCC,DDD,InterfaceModule
                    |
            classloader3    ---------------->   BBB,CCC,DDD,InterfaceModule
    */
    public static ClassLoader createClassLoaderChain4() {

        File AAAJar = new File("./AAA/target/AAA-1.0-SNAPSHOT.jar");
        File BBBJar = new File("./BBB/target/BBB-1.0-SNAPSHOT.jar");
        File CCCJar = new File("./CCC/target/CCC-1.0-SNAPSHOT.jar");
        File DDDJar = new File("./DDD/target/DDD-1.0-SNAPSHOT.jar");
        File InterfaceJar = new File("./InterfaceModule/target/InterfaceModule-1.0-SNAPSHOT.jar");

        System.out.println("Is file " + AAAJar.getName() + " exists ? " + AAAJar.exists());
        System.out.println("Is file " + BBBJar.getName() + " exists ? " + BBBJar.exists());
        System.out.println("Is file " + DDDJar.getName() + " exists ? " + DDDJar.exists());

        URL AAAJarURL;
        URL BBBJarURL;
        URL CCCJarURL;
        URL DDDJarURL;
        URL InterfaceJarURL;
        try {
            AAAJarURL = AAAJar.toURI().toURL();
            BBBJarURL = BBBJar.toURI().toURL();
            CCCJarURL = CCCJar.toURI().toURL();
            DDDJarURL = DDDJar.toURI().toURL();
            InterfaceJarURL = InterfaceJar.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        ClassLoader appClassLoader = Thread.currentThread().getContextClassLoader();
        URLClassLoader classloader1 = new NoParentDelegateClassLoader(new URL[]{AAAJarURL, CCCJarURL, DDDJarURL, InterfaceJarURL}, appClassLoader);
        URLClassLoader classloader2 = new NoParentDelegateClassLoader(new URL[]{BBBJarURL, CCCJarURL, DDDJarURL, InterfaceJarURL}, classloader1);
        URLClassLoader classloader3 = new NoParentDelegateClassLoader(new URL[]{BBBJarURL, CCCJarURL, DDDJarURL, InterfaceJarURL}, classloader2);

        return classloader3;
    }
}
