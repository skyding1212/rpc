package com.alex.rpc.rmi;

import com.alex.rpc.loadbalance.NodeInfo;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiUtil {

    /**
     * @param @param host
     * @param @param port
     * @param @param id 参数
     * @return void 返回类型
     * @throws
     * @Description 启动rmi服务
     */
    public void startRmiServer(String host, String port, String id) {
        try {
            SoaRmi soaRmi = new SoaRmiImpl();
            LocateRegistry.createRegistry(Integer.valueOf(port));
            //rmi://127.0.0.1:1135/fudisfuodsuf

            Naming.bind("rmi://" + host + ":" + port + "/" + id, soaRmi);
            System.out.println("rmi server start !!!");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public SoaRmi startRmiClient(NodeInfo nodeinfo, String id) {
        String host = nodeinfo.getHost();
        String port = nodeinfo.getPort();

        try {
            return (SoaRmi) Naming.lookup("rmi://" + host + ":" + port + "/"
                    + id);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
