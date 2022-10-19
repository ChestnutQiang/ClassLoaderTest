package com.lzq.bbb;

import com.lzq.in.Alphabet;

/**
 * @author wujuan
 * @version 1.0
 * @date 2022/10/18 21:56 星期二
 * @email wujuan@dtstack.com
 * @company www.dtstack.com
 */
public class B4 implements Alphabet {
    @Override
    public void pronounce() {
        System.out.println("I'am " + this.getClass().getName());
    }
}
