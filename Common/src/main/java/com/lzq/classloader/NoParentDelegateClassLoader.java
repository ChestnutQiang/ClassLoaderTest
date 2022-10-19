package com.lzq.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author wujuan
 * @version 1.0
 * @date 2022/3/3 20:10 星期四
 * @email wujuan@dtstack.com
 * @company www.dtstack.com
 */
public class NoParentDelegateClassLoader extends URLClassLoader {

    protected ClassLoader parent;

    public NoParentDelegateClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.parent = parent;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        Class<?> clazz;
        if (null == loadedClass) {
            // 子类先加载
            try {
                clazz = findClass(name);
                if (clazz != null) {
                    System.out.println(
                            "Loading class from NoParentDelegateClassLoader : " + this);
                    return clazz;
                }
            } catch (ClassNotFoundException e) {
                // Ignore
            }
        }
        // 父类加载
        return super.loadClass(name);
    }
}
