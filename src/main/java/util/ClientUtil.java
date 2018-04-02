package util;

import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ClientUtil {

	private static Logger log = LogManager.getLogger(ClientUtil.class);
//	private static final String IP = "172.16.3.234";
	private static final String IP = "127.0.0.1";
	private static final int PORT = 9300;
	private static final String PROPERTY_NAME = "cluster.name";
	private static final String CLUSTER_NAME = "my-application";
	private static TransportClient client = initClient();
	
	/**
	 * 单例获取客户端
	 * @return
	 */
	public static TransportClient getClient(){
		client = initClient();
		return client;
	}
	
	@SuppressWarnings("resource")
	public static TransportClient initClient(){
		TransportClient client = null;
		//设置集群名称
		Settings settings = Settings.builder().put(PROPERTY_NAME, CLUSTER_NAME).build();
		try {
			//创建client,当EL为本地默认启动时，设置为Settings.EMPTY
//			client = new PreBuiltTransportClient(settings)
			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(IP), PORT));
		} catch (Exception e) {
			log.error("创建client发生异常，异常信息：" + e);
			e.printStackTrace();
		}
		return client;
	}
	
}
