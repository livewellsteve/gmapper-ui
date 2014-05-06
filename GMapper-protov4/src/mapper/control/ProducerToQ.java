package mapper.control;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import mapper.control.ProtobufData.QData;

public class ProducerToQ {
	private int mapperFieldId;
	private String colName;
	private String colType;
	private String colValue;
	private String time;
	private String action;
	private String targetType;
	private String targetName;
	private String targetObject;
	private String targetPk;
	private String targetPkType;
	private String targetDbType;

	private final String URIQ = "amqp://wyblrefm:BqLIar27nUg78upggB-bQ6NQas9zGpfY@lemur.cloudamqp.com/wyblrefm";
	private final String USERQ = "wyblrefm";
	private final String PASSQ = "BqLIar27nUg78upggB-bQ6NQas9zGpfY";
	private final String NAMEQ = "testQ1";

	public ProducerToQ(int mapperFieldId, String colName, String colType,
			String colValue, Date time, String action, String targetType,
			String targetName, String targetObject, String targetPk,
			String targetPkType, String targetDbType) {

		this.mapperFieldId = mapperFieldId;
		this.colName = colName;
		this.colType = colType;
		this.colValue = colValue;
		this.time = String.valueOf(time);
		this.action = action;
		this.targetType = targetType;
		this.targetName = targetName;
		this.targetObject = targetObject;
		this.targetPk = targetPk;
		this.targetPkType = targetPkType;
		this.targetDbType = targetDbType;
	}

	public void run() {

		QData data = ProtobufData.QData.newBuilder()
				.setMapperFieldConfigId(mapperFieldId).setColName(colName)
				.setColType(colType).setColValue(colValue).setAction(action)
				.setTargetType(targetType).setTargetName(targetName)
				.setTargetObject(targetObject).setTargetPk(targetPk)
				.setTargetPkType(targetPkType).setTime(time)
				.setTargetDbType(targetDbType).build();

		ConnectionFactory factory = new ConnectionFactory();
		Connection conn = null;
		Channel chan = null;
		try {
			URI uri = new URI(URIQ);
			factory.setUri(uri);
			factory.setUsername(USERQ);
			factory.setPassword(PASSQ);

			conn = factory.newConnection();
			chan = conn.createChannel();
			chan.queueDeclare(NAMEQ, false, false, false, null);

			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			data.writeTo(oStream);
			chan.basicPublish("", NAMEQ, null, oStream.toByteArray());

			if (chan != null)
				chan.close();
			if (conn != null)
				conn.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
