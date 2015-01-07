package tocol.rpc.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.common.conf.Constants;
import tocol.rpc.common.handle.ReceivedHandle;
import tocol.rpc.server.Server;
import tocol.rpc.server.conf.ChannelManagerSingle;

public class ServerHandlerAdapter extends ChannelInboundHandlerAdapter {

	private final ReceivedHandle receivedHandle;
	private final String hostName;
	private final Server server;
	public ServerHandlerAdapter(Server server,ReceivedHandle receivedHandle,String hostName) {
		super();
		this.server=server;
		this.receivedHandle = receivedHandle;
		this.hostName=hostName;
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		System.out.println(Constants.MessageTypeHeartbeat+"\t"+evt);
		 /*心跳处理*/
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                /*读超时*/
        		System.out.println("读超时:"+ctx.channel()+"\t"+ChannelManagerSingle.getChannelManagerMap().get(hostName).size());
                ChannelManagerSingle.remove(hostName,ctx.channel());
            } else if (event.state() == IdleState.WRITER_IDLE) {
                /*写超时*/   
                System.out.println("WRITER_IDLE 写超时");
            } else if (event.state() == IdleState.ALL_IDLE) {
                /*总超时*/
                System.out.println("ALL_IDLE 总超时");
                
            }
        }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ChannelManager manager=new ServerChannelManager(server,ctx.channel(), hostName);
		ChannelManagerSingle.put(hostName,manager);

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println(ctx.channel()+"\t"+msg);
		receivedHandle.received(ctx.channel(), msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
		ChannelManagerSingle.remove(hostName,ctx.channel());
		System.out.println("断开连接:"+ctx.channel()+"\t"+ChannelManagerSingle.getChannelManagerMap().get(hostName).size());

	}
}
