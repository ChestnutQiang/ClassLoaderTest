package com.lzq;

/**
 * @author wujuan
 * @version 1.0
 * @date 2022/10/18 17:52 星期二
 * @email wujuan@dtstack.com
 * @company www.dtstack.com
 */
public class B0 extends A0 implements Alphabet {
    @Override
    public void pronounce() {
        System.out.println("I'am " + this.getClass().getName());
    }
}
