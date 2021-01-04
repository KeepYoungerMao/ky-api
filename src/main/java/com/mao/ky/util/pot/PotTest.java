package com.mao.ky.util.pot;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

/**
 * @author : create by zongx at 2020/12/28 16:13
 */
public class PotTest {

    public static void main(String[] args) {
        PotTestInterface potTestInterface = new PotTestInterfaceImpl();
        InvocationHandler invocationHandler = new PotInvoke(potTestInterface);
        PotTestInterface proxy = (PotTestInterface) Proxy.newProxyInstance(
                invocationHandler.getClass().getClassLoader(),
                potTestInterface.getClass().getInterfaces(),
                invocationHandler
        );
        System.out.println(proxy.getName());
    }

}
