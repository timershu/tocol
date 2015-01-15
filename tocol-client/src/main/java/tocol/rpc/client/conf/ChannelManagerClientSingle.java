package tocol.rpc.client.conf;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import tocol.rpc.client.netty.ClientChannelManager;
import tocol.rpc.common.channel.ChannelManager;

public class ChannelManagerClientSingle {

	private ChannelManagerClientSingle() {
	}

	private static class ChannelManagerMap {

		private static Map<String, List<ChannelManager>> ChannelManagerMapList=new ConcurrentHashMap<>();
		
		public static  void put(String key, ChannelManager value) {
			synchronized (ChannelManagerMapList) {
				List<ChannelManager> lists=ChannelManagerMapList.get(key);
				if(lists==null){
					lists=new CopyOnWriteArrayList<>();
				}
				lists.add(value);
				ChannelManagerMapList.put(key, lists);
			}
		}

		public static ChannelManager get(String key) throws Exception {
			List<ChannelManager> lists=ChannelManagerMapList.get(key);
			if(lists==null||lists.isEmpty()){
				throw new Exception("没有多余的连接");
			}
			if(lists.size()==1){
				return lists.get(0);
			}
			int index=-1;
			long time=0;
			
			for(int i=0;i<lists.size();i++){
				if(i==0){
					time=lists.get(i).getTransferTime();
				}else{
					if(lists.get(i).getTransferTime()<time && lists.get(i).getChannel().isActive()){
						index=i;
						time=lists.get(i).getTransferTime();
					}
				}
				
			}
			if(index == -1){
				remove(key);
				index=0;
			}
			return lists.get(index);
		}

		public static Map<String, List<ChannelManager>> getChannelManagerMap() {
			return ChannelManagerMapList;
		}
		
		private static void remove(ChannelManager channelManager) {
			if (channelManager instanceof ClientChannelManager) {
				ClientChannelManager clientChannel = (ClientChannelManager) channelManager;
				if(!clientChannel.getChannel().isActive()){
					ChannelManagerMapList.get(channelManager.getHostName()).remove(channelManager);
					if(ChannelManagerMapList.get(channelManager.getHostName()).size()<channelManager.getConnectCount()){
						clientChannel.getClient().doConnect();

					}
				}
			} 
		}
		
		public static synchronized void remove(String key,Channel channel) {
			List<ChannelManager> lists = ChannelManagerMapList.get(key);
			for(ChannelManager l:lists){
				if(l.getChannel()==channel){
					remove(l);
					break;
				}
			}
		}
		
		private static synchronized void remove(String key) {
			List<ChannelManager> lists = ChannelManagerMapList.get(key);
			for(ChannelManager l:lists){
				remove(l);
			}
		}
		
		private static void close(String key) {
			List<ChannelManager> lists = ChannelManagerMapList.get(key);
			for(ChannelManager l:lists){
				l.getChannel().close();
			}
		}
	}
	
	public static void close(String key) {
		ChannelManagerMap.close(key);
	}

	public static void put(String key, ChannelManager value) {
		ChannelManagerMap.put(key, value);
	}

	public static ChannelManager get(String key) throws Exception {
		return ChannelManagerMap.get(key);
	}

	public static Map<String, List<ChannelManager>> getChannelManagerMap() {
		return ChannelManagerMap.getChannelManagerMap();
	}

	public static void remove(String key,Channel channel) {
		ChannelManagerMap.remove(key,channel);
	}
}
