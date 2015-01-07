package tocol.rpc.protocol.java;

import io.netty.buffer.ByteBuf;
import tocol.rpc.protocol.Protocol;

public class JavaProtocol implements Protocol {
	private final static String protocolName="java";
	
	public void encoder(ByteBuf arg0, Object arg1) {
		// TODO Auto-generated method stub
		arg0.writeByte(protocolName.getBytes().length);
		arg0.writeBytes(protocolName.getBytes());
		byte[] datas = JavaByteObjConverter.objectToByte(arg1);
		arg0.writeBytes(datas);
	}

	
	public Object decoder(ByteBuf arg0) {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[arg0.readableBytes()];
		arg0.readBytes(bytes);
		Object obj = JavaByteObjConverter.byteToObject(bytes);
		return obj;
	}

}
