package tocol.rpc.protocol.hessian;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import tocol.rpc.protocol.Protocol;

import com.caucho.hessian.io.Deflation;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

public class HessianProtocol implements Protocol {
	
	private final static String protocolName="hessian";

	//使用hessian 2协议 + deflat压缩
	private Deflation envelope = new Deflation();
	
	public void encoder(ByteBuf arg0, Object arg1) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeByte(protocolName.getBytes().length);
		arg0.writeBytes(protocolName.getBytes());
		
		HessianOutPutStream os = new HessianOutPutStream(arg0);
		Hessian2Output hessian2Os = new Hessian2Output(os);
		hessian2Os=envelope.wrap(hessian2Os);
		hessian2Os.writeObject(arg1);
		hessian2Os.flushBuffer();
		hessian2Os.close();
	}

	
	public Object decoder(ByteBuf arg0) throws IOException {
		HessianInputStream is = new HessianInputStream(arg0);
		Hessian2Input hessian2Input = new Hessian2Input(is);
		hessian2Input=envelope.unwrap(hessian2Input);
		Object obj = hessian2Input.readObject();
		hessian2Input.close();
		return obj;
	}

}
