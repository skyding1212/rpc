package com.alex.rpc.parse;

import com.alex.rpc.configBean.Protocol;
import com.alex.rpc.configBean.Reference;
import com.alex.rpc.configBean.Registry;
import com.alex.rpc.configBean.Service;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SOANamespaceHandler extends NamespaceHandlerSupport {
    
    public void init() {
        registerBeanDefinitionParser("registry",
                new RegistryBeanDefinitionParse(Registry.class));
        registerBeanDefinitionParser("protocol",
                new ProtocolBeanDefinitionParse(Protocol.class));
        registerBeanDefinitionParser("reference",
                new ReferenceBeanDifinitionParse(Reference.class));
        registerBeanDefinitionParser("service", new ServiceBeanDefinitionParse(
                Service.class));
        
    }
    
}
