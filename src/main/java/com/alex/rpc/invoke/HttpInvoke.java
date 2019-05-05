package com.alex.rpc.invoke;

import com.alex.rpc.configBean.Reference;
import com.alex.rpc.http.HttpRequest;
import com.alex.rpc.loadbalance.LoadBalance;
import com.alex.rpc.loadbalance.NodeInfo;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Description 这个是http的调用过程
 * @ClassName HttpInvoke
 */

public class HttpInvoke implements Invoke {

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

            //http://127.0.0.1:8023/jack/soa/service
            String url = "http://" + nodeinfo.getHost() + ":"
                    + nodeinfo.getPort() + nodeinfo.getContextpath();

            //调用对端的生产者的服务
            String result = HttpRequest.sendPost(url, sendparam.toJSONString());
            Class<?> returnType = invocation.getMethod().getReturnType();
            return JSONObject.parseObject(result,returnType);
        } catch (Exception e) {
            throw e;
        }
    }

}
