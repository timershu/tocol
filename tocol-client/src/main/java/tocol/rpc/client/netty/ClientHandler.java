package tocol.rpc.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import tocol.rpc.client.conf.ChannelManagerClientSingle;
import tocol.rpc.common.handle.ReceivedHandle;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	private final String hostName;
	private final ReceivedHandle receivedHandle;

	public ClientHandler(String hostName, ReceivedHandle receivedHandle) {
		super();
		this.hostName = hostName;
		this.receivedHandle = receivedHandle;
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)throws Exception {
		 /*心跳处理*/
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                /*读超时*/
                System.out.println("READER_IDLE 读超时");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                /*写超时*/   
        		System.out.println("写超时:"+ctx.channel()+"\t"+ChannelManagerClientSingle.getChannelManagerMap().get(hostName).size());
                ChannelManagerClientSingle.remove(hostName,ctx.channel());

            } else if (event.state() == IdleState.ALL_IDLE) {
                /*总超时*/
                System.out.println("ALL_IDLE 总超时");
                
            }
        }
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			receivedHandle.received(ctx.channel(), msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		ChannelManagerClientSingle.remove(hostName,ctx.channel());
	}

}
