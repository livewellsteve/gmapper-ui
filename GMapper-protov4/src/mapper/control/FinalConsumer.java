package mapper.control;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Statement;

import mapper.control.ProtobufData.QData;

import com.jolbox.bonecp.BoneCPDataSource;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class FinalConsumer implements Runnable {
	private final String URIQ = "amqp://wyblrefm:BqLIar27nUg78upggB-bQ6NQas9zGpfY@lemur.cloudamqp.com/wyblrefm";
	private final String USERQ = "wyblrefm";
	private final String PASSQ = "BqLIar27nUg78upggB-bQ6NQas9zGpfY";
	private final String NAMEQ_FINAL = "testQ2";
	
	private BoneCPDataSource ds;

	public FinalConsumer(BoneCPDataSource ds) {
		this.ds = ds;
	}

	
	
	@Override
	public void run() {
		ConnectionFactory factory = new ConnectionFactory();
		Connection conn = null;
		Channel chan = null;
		QueueingConsumer consumer = null;

		try {
			URI uri = new URI(URIQ);
			factory.setUri(uri);
			factory.setUsername(USERQ);
			factory.setPassword(PASSQ);

			while (true) {
				synchronized(this){
					conn = factory.newConnection();
					chan = conn.createChannel();
					chan.queueDeclare(NAMEQ_FINAL, false, false, false, null);
					consumer = new QueueingConsumer(chan);
					chan.basicConsume(NAMEQ_FINAL, true, consumer);
					}
				
				QueueingConsumer.Delivery delivery;

				delivery = consumer.nextDelivery();

				if (delivery != null) {
					String message = new String(delivery.getBody());
					System.out
							.println(" [xx] " + NAMEQ_FINAL + " Received " + message);

					ByteArrayInputStream iStream = new ByteArrayInputStream(
							delivery.getBody());
					QData data = QData.parseFrom(iStream);
					
					String sql = data.getMapperLogic();
				
					System.out.println(data.getColValue()+"******"+sql);
					
					
					java.sql.Connection connDb = ds.getConnection();
					Statement stmt = connDb.createStatement();
					
					stmt.executeUpdate(sql);
					
					if (stmt!=null){
					stmt.close();
					}
					
					if (connDb!=null){
					connDb.close();
					}
//					if (dbType.equals("mysql")){
//						
//					}
//					else if (dbType.equals("postgres")){
//						
//					}
					
				}
				if (chan!=null)
					chan.close();
				if (conn!=null)
					conn.close();
			}
		} catch (IOException e) {

			e.printStackTrace();
			
		} catch (ShutdownSignalException | ConsumerCancelledException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		

		} catch (URISyntaxException e) {
			e.printStackTrace();
		
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}catch (SQLException e) {
			
			e.printStackTrace();
		}

		
	}

	

}
