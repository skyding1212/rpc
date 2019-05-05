package com.alex.rpc.cluster;


import com.alex.rpc.invoke.Invocation;

public interface Cluster {
    public Object invoke(Invocation invocation) throws Exception;
}
