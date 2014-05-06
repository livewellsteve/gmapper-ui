package mapper.control;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import mapper.control.ProtobufData.QData;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class MapperListener implements Runnable {

	private final String URIQ ="amqp://wyblrefm:BqLIar27nUg78upggB-bQ6NQas9zGpfY@lemur.cloudamqp.com/wyblrefm";
	private final String USERQ ="wyblrefm";
	private final String PASSQ ="BqLIar27nUg78upggB-bQ6NQas9zGpfY";
	private final String NAMEQ ="testQ1";
	private final String NAMEQ_FINAL ="testQ2";
	
	public MapperListener(){
		
	}
	
	@Override
	public void run(){
//		String URIQ ="amqp://wyblrefm:BqLIar27nUg78upggB-bQ6NQas9zGpfY@lemur.cloudamqp.com/wyblrefm";
//		String USERQ ="wyblrefm";
//		String PASSQ ="BqLIar27nUg78upggB-bQ6NQas9zGpfY";
//		String NAMEQ ="testQ1";
//		String NAMEQ_FINAL ="testQ2";
		ConnectionFactory factory = new ConnectionFactory();
		 Connection conn = null;
		 Channel chan = null;
		 QueueingConsumer consumer = null;
		
		 System.out.println("**Starting MapperPair Instance***");
		try {
			URI uri = new URI(URIQ);
			factory.setUri(uri);
			factory.setUsername(USERQ);
			factory.setPassword(PASSQ);
		    
		    
		    while (true) {
				//for later implement
					synchronized(this){
					conn = factory.newConnection();
					chan = conn.createChannel();
					chan.queueDeclare(NAMEQ, false, false, false, null);
					consumer = new QueueingConsumer(chan);
				    chan.basicConsume(NAMEQ, true, consumer);
					}
				    
		      QueueingConsumer.Delivery delivery;
		      delivery = consumer.nextDelivery();
		      
		      if (delivery != null){
		    	  String  message = new String(delivery.getBody());
			      System.out.println(" [x] "+NAMEQ+" Received " + message);
			      
			      ByteArrayInputStream iStream = new ByteArrayInputStream(delivery.getBody());
			      QData data = QData.parseFrom(iStream);
			      MapperLogic ml = new MapperLogic(data);
			      String logic = ml.generateLogic();
			      
			      QData.Builder processedData = QData.newBuilder();
			      processedData.mergeFrom(data);
			      processedData.setMapperLogic(logic);
			      QData pData = processedData.build();
			      
			    //  synchronize this too for later more than 1 threads
			      synchronized(this){
			      QProducer qp = new QProducer(NAMEQ_FINAL,pData,conn);
			      qp.sendtoQ();
			      }
//			      System.out.println(data.getMapperFieldConfigId());
//			      System.out.println(data.getColName());
//			      System.out.println(data.getColType());
//			      System.out.println(data.getColValue());
//			      System.out.println(data.getAction());
//			      System.out.println(data.getTargetType());
//			      System.out.println(data.getTargetName());
//			      System.out.println(data.getTargetObject());
//			      System.out.println(data.getTargetPk());
//			      System.out.println(data.getTargetPkType());
//			      System.out.println(data.getTime());
			      
		  //  	System.out.println("received msg from mapper!");
		    	}
		 
		      if (chan!=null)
		    	  chan.close();
		      if (conn!=null)
		    	  conn.close();
		      
		    }
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		catch (ShutdownSignalException | ConsumerCancelledException
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
	}
	}

	
}
