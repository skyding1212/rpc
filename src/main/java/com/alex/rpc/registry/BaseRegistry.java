package com.alex.rpc.registry;

import org.springframework.context.ApplicationContext;

import java.util.List;

public interface BaseRegistry {
    public boolean registry(String param, ApplicationContext application);
    
    public List<String> getRegistry(String id, ApplicationContext application);
}
