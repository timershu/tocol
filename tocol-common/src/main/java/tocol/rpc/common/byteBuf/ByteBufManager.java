package tocol.rpc.common.byteBuf;

import io.netty.buffer.ByteBuf;

public class ByteBufManager {

	private final ByteBuf byteBuf;

	public ByteBuf getByteBuf() {
		return byteBuf;
	}

	public ByteBufManager(ByteBuf byteBuf) {
		super();
		this.byteBuf = byteBuf;
	}
}
