package tocol.rpc.client.heart;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartClient {

	private static final ScheduledExecutorService scheduled = Executors
			.newScheduledThreadPool(1);
	private static int heartbeat = 0;
	private static int heartbeatTimeout = 5;

	static {
		scheduled.scheduleAtFixedRate(new HeartBeatTask(heartbeatTimeout),
				heartbeat, heartbeatTimeout, TimeUnit.SECONDS);
	}

	public static void main(String args[]) {
	}

}
