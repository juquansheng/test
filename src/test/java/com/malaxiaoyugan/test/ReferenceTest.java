package com.malaxiaoyugan.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * description: ReferenceTest
 * date: 2021/3/5 17:31
 * author: juquansheng
 * version: 1.0 <br>
 */
@SpringBootTest
public class ReferenceTest {
    static Object object = new Object();
    @Test
    public void test1(){

        /**
         * 强引用
         */
            Object obj = object;
            object = null;
            System.gc();
            System.out.print("after system.gc-strongReference---obj = " + obj);


    }

    /**
     * 软引用
     */
    @Test
    public void testSoftReference(){

        SoftReference<Object> obj = new SoftReference<>(object);

        object = null;
        System.gc();
        System.out.print("after system.gc---softReference = " + obj);
    }

    static String str = "strTest";
    //static String str = new String("strTest");
    @Test
    public void testWeakReference(){
        WeakReference<Object> weakReference = new WeakReference<Object>(object);
        WeakReference<Object>  weakReferenceStr = new WeakReference<Object>(str);
        object = null;
        str = null;
        System.gc();
        System.out.println("after system.gc---weakReference = " + weakReference.get());
        System.out.print("after system.gc---weakReferenceStr = " + weakReferenceStr.get());
    }

    /**
     * 虚引用无论什么时候调用get方法，都是返回null的，和有没有被回收没有关系，虚引用一定要配合着引用队列去使用。也就是代码中的referenceQueue，当垃圾回收要回收这个虚引用的时候，会把这个虚引用加到referenceQueue中，相当于系统通知一样，通过referenceQueue中的remove可以获取到这个虚引用对象的话，就说明这个虚引用即将被回收。软引用和弱引用也可以在创建的时候加入这个队列，效果一样。
     */
    @Test
    public void testPhantonReference(){
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(object,referenceQueue);
        PhantomReference<Object> phantomReferenceStr = new PhantomReference<>(str,referenceQueue);
//        object = null;
//        str = null;
        System.gc();
        System.out.println("after system.gc---phantomReference = " + phantomReference.get());
        System.out.print("after system.gc---phantomReferenceStr = " + phantomReferenceStr.get());
    }
}
