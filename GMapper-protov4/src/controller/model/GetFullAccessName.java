package controller.model;

public class GetFullAccessName {

	public static String getNameMysql(String end, String db){
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:mysql://");
		sb.append(end);
		sb.append("/");
		sb.append(db);
		
		return sb.toString();
	}
	
	public static String getNamePostgres(String end, String db){
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:postgresql://");
		sb.append(end);
		sb.append("/");
		sb.append(db);
		
		return sb.toString();
	}
	
}
