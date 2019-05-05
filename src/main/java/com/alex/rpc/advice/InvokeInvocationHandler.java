package com.alex.rpc.advice;

import com.alex.rpc.cluster.Cluster;
import com.alex.rpc.configBean.Reference;
import com.alex.rpc.invoke.Invocation;
import com.alex.rpc.invoke.Invoke;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/** 
 * @Description InvokeInvocationHandler 在这个advice里面就进行了rpc的远程调用
 * rpc：http、rmi、netty
 * @ClassName   InvokeInvocationHandler
 */

public class InvokeInvocationHandler implements InvocationHandler {
    
    private Invoke invoke;
    
    private Reference reference;
    
    public InvokeInvocationHandler(Invoke invoke, Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }
    
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        //在这个invoke里面最终要调用多个远程的provider
        System.out.print("已经获取到了代理实例，已经掉到了InvokeInvocationHandler.invoke");
        Invocation invocation = new Invocation();
        invocation.setMethod(method);
        invocation.setObjs(args);
        invocation.setReference(reference);
        invocation.setInvoke(invoke);
        //        String result = invoke.invoke(invocation);
        Cluster cluster = reference.getClusters().get(reference.getCluster());
        Object result = cluster.invoke(invocation);
        return result;
    }
}
