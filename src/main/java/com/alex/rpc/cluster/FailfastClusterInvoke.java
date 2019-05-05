package com.alex.rpc.cluster;

import com.alex.rpc.invoke.Invocation;
import com.alex.rpc.invoke.Invoke;

/** 
 * @Description 这个如果调用节点异常，直接失败 
 * @ClassName   FailfastClusterInvoke 
 */

public class FailfastClusterInvoke implements Cluster {
    
    public Object invoke(Invocation invocation) throws Exception {
        Invoke invoke = invocation.getInvoke();
        
        try {
            return invoke.invoke(invocation);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
