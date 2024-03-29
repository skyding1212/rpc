package com.alex.rpc.cluster;


import com.alex.rpc.invoke.Invocation;
import com.alex.rpc.invoke.Invoke;

/**
 * @Description 这个如果调用失败就自动切换到其他集群节点 
 * @ClassName   FailoverClusterInvoke 
 * @Date        2017年11月18日 下午9:37:46 
 * @Author      dn-jack 
 */
public class FailoverClusterInvoke implements Cluster {
    
    public Object invoke(Invocation invocation) throws Exception {
        
        String retries = invocation.getReference().getRetries();
        Integer retriint = Integer.parseInt(retries);
        
        for (int i = 0; i < retriint; i++) {
            try {
                Invoke invoke = invocation.getInvoke();
                return invoke.invoke(invocation);
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        
        throw new RuntimeException("retries" + retries + "全部失败！！！！");
    }
    
}
