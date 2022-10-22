package com.lzq.ddd;

import com.lzq.in.Alphabet;

import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * @author wujuan
 * @version 1.0
 * @date 2022/10/18 23:13 星期二
 * @email wujuan@dtstack.com
 * @company www.dtstack.com
 */
public class D3 {

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
    public void d3(ClassLoader classloader) {
        System.out.println("The d3 method called");
        //Alphabet d4 = new D4();
        ServiceLoader<Alphabet> alphabet = ServiceLoader.load(Alphabet.class, classloader);
        Iterator<Alphabet> alphabetIterator = alphabet.iterator();
        while(alphabetIterator.hasNext()){
            // B0 被 classloader3 加载，但是同时 classloader3 也会先加载 InterfaceModule 里面的 Alphabet.
            // 但是 D3 被 classloader2 加载，然后 Alphabet 自然被 classloader2 加载了。
            alphabetIterator.next().pronounce();
            Alphabet a1 = alphabetIterator.next();
            Alphabet a2 = alphabetIterator.next();

        }
    }
    /*
        Caused by: java.util.ServiceConfigurationError: com.lzq.in.Alphabet: Provider com.lzq.bbb.B0 not a subtype
        at java.util.ServiceLoader.fail(ServiceLoader.java:239)
        at java.util.ServiceLoader.access$300(ServiceLoader.java:185)
        at java.util.ServiceLoader$LazyIterator.nextService(ServiceLoader.java:376)
        at java.util.ServiceLoader$LazyIterator.next(ServiceLoader.java:404)
        at java.util.ServiceLoader$1.next(ServiceLoader.java:480)
        at com.lzq.ddd.D3.d3(D3.java:23)
        ... 5 more
     */
}
