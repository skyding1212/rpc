package com.alex.rpc.invoke;

import com.alex.rpc.configBean.Reference;
import com.alex.rpc.loadbalance.LoadBalance;
import com.alex.rpc.loadbalance.NodeInfo;
import com.alex.rpc.netty.NettyUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class NettyInvoke implements Invoke {

    public Object invoke(Invocation invocation) throws Exception {
        try {
            List<String> registryInfo = invocation.getReference()
                    .getRegistryInfo();
            //这个是负载均衡算法
            String loadbalance = invocation.getReference().getLoadbalance();
            Reference reference = invocation.getReference();
            LoadBalance loadbalanceBean = reference.getLoadBalances()
                    .get(loadbalance);

            NodeInfo nodeinfo = loadbalanceBean.doSelect(registryInfo);

            //我们调用远程的生产者是传输的json字符串
            //根据serviceid去对端生产者的spring容器中获取serviceid对应的实例
            //根据methodName和methodType获取实例的method对象
            //然后反射调用method方法
            JSONObject sendparam = new JSONObject();
            sendparam.put("methodName", invocation.getMethod().getName());
            sendparam.put("methodParams", invocation.getObjs());
            sendparam.put("serviceId", reference.getId());
            sendparam.put("paramTypes", invocation.getMethod()
                    .getParameterTypes());

            return NettyUtil.sendMsg(nodeinfo.getHost(),
                    nodeinfo.getPort(),
                    sendparam.toJSONString());
        } catch (Exception e) {
            throw e;
        }
    }

}
