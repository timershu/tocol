package tocol.rpc.client.future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import tocol.rpc.client.conf.ChannelManagerClientSingle;
import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.common.conf.Constants;
import tocol.rpc.protocol.params.RequestParams;
import tocol.rpc.protocol.params.ResponseParams;

public class ResponseFuture {

	private static final Map<Long, ChannelManager> channelManagerMaps = new ConcurrentHashMap<Long, ChannelManager>();

	private static final Map<Long, ResponseFuture> futureMaps = new ConcurrentHashMap<Long, ResponseFuture>();

	private final long id;

	private final ChannelManager ChannelManager;

	private final RequestParams request;

	private final int timeout;

	private final Lock lock = new ReentrantLock();

	private final Condition done = lock.newCondition();

	private final long start = System.currentTimeMillis();

	private volatile long sent;

	private volatile ResponseParams response;
	
	private volatile int timeOutCount=0;//超时次数

	public int getTimeOutCount() {
		return timeOutCount;
	}

	public void setTimeOutCount(int timeOutCount) {
		this.timeOutCount = timeOutCount;
	}

	public ResponseFuture(ChannelManager ChannelManager, RequestParams request,
			int timeout) {
		this.ChannelManager = ChannelManager;
		this.request = request;
		this.id = request.getId();
		this.timeout = timeout > 0 ? timeout : Constants.DEFAULT_TIMEOUT;
		futureMaps.put(id, this);
		channelManagerMaps.put(id, ChannelManager);
	}

	public Object get() throws Exception{
		return get(timeout);
	}

	public Object get(int timeout) throws Exception {
		if (timeout <= 0) {
			timeout = Constants.DEFAULT_TIMEOUT;
		}
		if (!isDone()) {
			lock.lock();
			try {
				while (!isDone()) {
					done.await(timeout, TimeUnit.MILLISECONDS);
					if (isDone()) {
						break;
					}
					if(timeOutCount>=Constants.TimeOutCount){
						throw new Exception(id+":超时");
					}
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				lock.unlock();
			}
		}
		return returnFromResponse();
	}

	private Object returnFromResponse() {
		return response;
	}

	public boolean isDone() {
		return response != null;
	}

	public long getTimeout() {
		return timeout;
	}

	public long getStart() {
		return start;
	}

	private void doReceived(ResponseParams res) {
		lock.lock();
		try {
			response = res;
			if (done != null) {
				done.signal();
			}
		} finally {
			lock.unlock();
		}

	}

	public void cancel() {
		ResponseParams errorResult = new ResponseParams(id);
		errorResult.setMessage("request future has been canceled.");
		response = errorResult;
		futureMaps.remove(id);
		channelManagerMaps.remove(id);
	}

	public ChannelManager getChannelManager() {
		return ChannelManager;
	}

	public static void received(ResponseParams response) {
		try {
			ResponseFuture future = futureMaps.remove(response.getId());
			if (future != null) {
				future.doReceived(response);
			} else {
				//System.out.println(response+"：该结果已经执行完毕！");
			}
		} finally {
			ChannelManager channelManager=channelManagerMaps.get(response.getId());
			if(channelManager!=null){
				channelManager.setTransferTime(System.currentTimeMillis());
			}
			channelManagerMaps.remove(response.getId());
		}
	}

	private static class RemotingInvocationTimeoutScan implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					for (ResponseFuture future : futureMaps.values()) {
						if (future == null || future.isDone()) {
							continue;
						}
						if (System.currentTimeMillis() - future.getStart() > future.getTimeout()) {
							if(future.getTimeout()<Constants.TimeOutCount){
								future.setTimeOutCount(future.getTimeOutCount()+1);
								ChannelManager channelManager=channelManagerMaps.get(future.id);
								channelManager=ChannelManagerClientSingle.get(channelManager.getHostName());
								channelManager.getChannel().writeAndFlush(future.request);
								channelManagerMaps.put(future.id, channelManager);
							}else{
								ResponseParams timeoutResponse = new ResponseParams(future.id);
								timeoutResponse.setMessage("获取数据超时");
								timeoutResponse.setStatus(Constants.StatusError);
								ResponseFuture.received(timeoutResponse);
							}
						}
					}
					Thread.sleep(1);
				} catch (Throwable e) {

				}
			}
		}
	}

	static {
		Thread th = new Thread(new RemotingInvocationTimeoutScan(),"ResponseTimeout");
		th.setDaemon(true);
		th.start();
	}

}
