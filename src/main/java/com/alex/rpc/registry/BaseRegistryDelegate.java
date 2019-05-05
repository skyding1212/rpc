package com.alex.rpc.registry;

import com.alex.rpc.configBean.Registry;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class BaseRegistryDelegate {
    public static void registry(String ref, ApplicationContext application) {
        Registry registry = application.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry registryBean = registry.getRegistryMap().get(protocol);
        registryBean.registry(ref, application);
    }
    
    public static List<String> getRegistry(String id,
            ApplicationContext application) {
        Registry registry = application.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry registryBean = registry.getRegistryMap().get(protocol);
        
        return registryBean.getRegistry(id, application);
    }
}
