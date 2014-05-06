package mapper.control;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import mapper.control.ProtobufData.QData;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class QProducer {
private String qName;
private QData data;
private final String URIQ ="amqp://wyblrefm:BqLIar27nUg78upggB-bQ6NQas9zGpfY@lemur.cloudamqp.com/wyblrefm";
private final String USERQ ="wyblrefm";
private final String PASSQ ="BqLIar27nUg78upggB-bQ6NQas9zGpfY";
private Connection conn;
	
	public QProducer(String qName, QData data,Connection conn){
		this.qName = qName;
		this.data = data;
		this.conn = conn;
	}
	
	public boolean sendtoQ(){
		ConnectionFactory factory = new ConnectionFactory();
		 
		 Channel chan = null;
		 try {
				URI uri = new URI(URIQ);
				factory.setUri(uri);
				factory.setUsername(USERQ);
				factory.setPassword(PASSQ);
				
				chan = conn.createChannel();
				chan.queueDeclare(qName, false, false, false, null);
				
				ByteArrayOutputStream oStream = new ByteArrayOutputStream();
				data.writeTo(oStream);
				chan.basicPublish("", qName, null, oStream.toByteArray());
				
				if(chan!=null)
					chan.close();
				
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return false;
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		 return true;
	}
}
