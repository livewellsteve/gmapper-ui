package testcase;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import testcase.ProtobufAdminQMsg.AdminQMsg;

public class Test3sendtoadminQ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	String URIQ = "amqp://wyblrefm:BqLIar27nUg78upggB-bQ6NQas9zGpfY@lemur.cloudamqp.com/wyblrefm";
	String USERQ = "wyblrefm";
	String PASSQ = "BqLIar27nUg78upggB-bQ6NQas9zGpfY";
	String NAMEQ = "AdminMysqlQ";
	
	try{
		
		AdminQMsg msg = ProtobufAdminQMsg.AdminQMsg.newBuilder()
				.setAction("create").setAdaptorType("mysql").setAdaptorId(90)
				.setMapperId(8).setSourceOrTarget("source").setTriggerId("gmapper_listen_201405020535")
				.setTableId("gmapper_listen_201405020535").build();
		ConnectionFactory factory = new ConnectionFactory();
	
		URI uri = new URI(URIQ);
		factory.setUri(uri);
		factory.setUsername(USERQ);
		factory.setPassword(PASSQ);

		Connection conn = factory.newConnection();
		Channel chan = conn.createChannel();
		
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
		msg.writeTo(oStream);
		chan.basicPublish("", NAMEQ, null, oStream.toByteArray());

		if (chan != null)
			chan.close();
		if (conn != null)
			conn.close();
		
		//System.out.println("sent msg");
	}catch(Exception e){
		e.printStackTrace();
	}

	}

}
