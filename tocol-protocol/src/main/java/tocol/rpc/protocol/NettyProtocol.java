package tocol.rpc.protocol;

import tocol.rpc.protocol.params.Params;
import io.netty.buffer.ByteBuf;

public class NettyProtocol extends AbstractProtocol<ByteBuf> {

	@Override
	public Protocol<ByteBuf> getProtocol(Object arg1) throws Exception {
		Protocol<ByteBuf> handle = null;
		if (arg1 instanceof Params) {
			Params params = (Params) arg1;
			handle = getProtocolParams(params);
		} else if (arg1 instanceof ByteBuf) {
			handle = getProtocolByteBuf((ByteBuf) arg1);
		}
		return handle;
	}
	
	private Protocol getProtocolParams(Params params) {
		return getProtocol(params.getProtocol());
	}
	
	private Protocol getProtocol(String name) {
		return ProtocolManager.get(name);
	}

	private Protocol getProtocolByteBuf(ByteBuf arg1) throws Exception {
		byte index = getByte(arg1);
		String name=getProtocol(index,arg1);
		Protocol o = getProtocol(name);
		return o;
	}
	
	private String getProtocol(int count,ByteBuf arg1){
		byte[] a = new byte[count];
		arg1.readBytes(a);
		return new String(a);
	}

	private byte getByte(ByteBuf arg1) {
		byte[] a = new byte[5];
		arg1.readBytes(a);
		return a[4];
	}

	private int getLength(byte[] a) {
		int b = 0;
		for (int i = 0; i < a.length - 1; i++) {
			b += (a[i] & 0xff) << (24 - 8 * i);
		}
		return b;
	}

}
