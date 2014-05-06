package pool;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jolbox.bonecp.BoneCPDataSource;



import mapper.control.FinalConsumer;
import mapper.control.ListenerThread;
import mapper.control.MapperListener;

public class ListenerThreadPool {

	//returning in case later implementation closing pool: deleting configuration
	public static ExecutorService runListenerPool(String fromTableName, String type, String colName,int mapper_field_id, BoneCPDataSource ds,String pkType, String pkName,String action, String targetType,String targetName,String targetObject, String targetPk, String targetPkType, String targetDbType){
		ExecutorService es = Executors.newFixedThreadPool(1);
		Runnable t = new ListenerThread(fromTableName, type, colName, mapper_field_id, ds,pkType,pkName,action,targetType,targetName,targetObject,targetPk,targetPkType,targetDbType);
		es.execute(t);
		
		return es;
	}
	
	public static ExecutorService runListenerFinalPool(BoneCPDataSource dsFinal){
		ExecutorService esFinal = Executors.newFixedThreadPool(1);
		Runnable tFinal = new FinalConsumer(dsFinal);
		esFinal.execute(tFinal);
		
		return esFinal;
	}
	
	public static ExecutorService runMapperPool(){
		ExecutorService esMapper = Executors.newFixedThreadPool(1);
		Runnable tMapper = new MapperListener();
		esMapper.execute(tMapper);
		
		return esMapper;
	}
	
	
//	public static ExecutorService runProducertoQPool(){
//		
//		
//	}
}
