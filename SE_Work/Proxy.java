package com.bitten.file;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author : YangY
 * @Description :动态代理设计模式
 * @Time : Created in 16:31 2019/4/3
 */
interface ISubject {  //核心操作接口
    void eat(String msg,int num);
}

class RealSubject implements ISubject {
    @Override
    public void eat(String msg, int num) {
        System.out.println("我要吃"+num+"份"+msg);
    }
}

class proxySubject implements InvocationHandler {
    //保存真实主题对象
    private Object target;

    /**
     *
     * @param target
     * @return 返回动态代理对象
     */
    public Object bind(Object target) {
        //保存真实主题对象
        this.target = target;
        //创建一个代理对象来代理真实需要代理的对象，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        return java.lang.reflect.Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass()
        .getInterfaces(),this);
    }
    public void preHandle() {
        System.out.println("完成吃饭前业务");
    }
    public void afterHandle() {
        System.out.println("完成吃饭后业务");
    }
    /**
     * proxy:代表动态代理对象
     * method：代表正在执行的方法
     * args：代表调用目标方法时传入的实参
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //通过这来查看invoke方法调用了啥方法
        System.out.println("调用了"+method.getName()+"方法");
        this.preHandle();
        Object ret = method.invoke(target,args);
        this.afterHandle();
        return ret;
    }
}

public class Proxy {
    public static void main(String[] args) {
        ISubject subject =(ISubject) new proxySubject().bind(new RealSubject());
        subject.eat("苹果",5);
    }
}


//输出：
//调用了eat方法
//完成吃饭前业务
//我要吃5份苹果
//完成吃饭后业务
