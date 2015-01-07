package tocol.rpc.protocol.hessian;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.OutputStream;

public class HessianOutPutStream extends OutputStream {

	private final ByteBuf buffer;
	private final int startIndex;

	public HessianOutPutStream(ByteBuf buffer) {
		if (buffer == null) {
			throw new NullPointerException("buffer");
		}
		this.buffer = buffer;
		startIndex = buffer.writerIndex();
	}

	public int writtenBytes() {
		return buffer.writerIndex() - startIndex;
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if (len == 0) {
			return;
		}

		buffer.writeBytes(b, off, len);
	}

	@Override
	public void write(byte[] b) throws IOException {
		buffer.writeBytes(b);
	}

	@Override
	public void write(int b) throws IOException {
		buffer.writeByte((byte) b);
	}

	public ByteBuf buffer() {
		return buffer;
	}

}
