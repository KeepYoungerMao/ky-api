package com.mao.ky.util.pot;

import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @author : create by zongx at 2020/12/28 15:02
 */
public class PotInvoke implements InvocationHandler {

    //代理类中的真实对象
    private Object object;

    public PotInvoke(Object o) {
        this.object = o;
    }

    /**
     * 每一个动态代理类的调用处理程序都必须实现InvocationHandler接口，
     * 并且每个代理类的实例都关联到了实现该接口的动态代理类调用处理程序中。
     * 当我们调用动态代理对象调用一个方法时
     *
     * proxy：最常见的是 newProxyInstance 方法，创建一个代理对象
     * newProxyInstance接收3个参数：
     * loader：classloader对象，定义了由哪个classloader对象对生成的代理类进行加载
     * interfaces：一个interface对象数组，表示我们将要给我们的代理对象提供一组什么样的接口，
     *              如果我们提供了这样一个接口对象数组，那么也就是声明了代理类实现了这些接口，
     *              代理类就可以调用接口中声明的所有方法。
     * h：InvocationHandler对象，表示的是当动态代理对象调用方法的时候会关联到哪一个
     *      InvocationHandler对象上，并最终由其调用。
     *
     * @param proxy 代理类代理的真实代理对象
     * @param method 我们所要调用的某个对象的真实的方法对象
     * @param args 调用代理对象真实的方法所需要传递的参数
     * @return 代理后的对象
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理前");
        Object invoke = method.invoke(this.object, args);
        System.out.println("代理后");
        return invoke;
    }

}
