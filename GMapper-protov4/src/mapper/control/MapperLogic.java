package mapper.control;

import mapper.control.ProtobufData.QData;

public class MapperLogic {
private QData data;
private int mapperFieldId;
private String sourceFieldName;
private String sourceFieldType;
private String sourceFieldValue;
private String action;
private String targetFieldName;
private String targetFieldType;
private String targetObject;
private String targetPk;
private String targetPkType;
private String time;


	public MapperLogic(QData data){
		this.data = data;
		mapperFieldId = data.getMapperFieldConfigId();
		sourceFieldName = data.getColName();
		sourceFieldType = data.getColType();
		sourceFieldValue = data.getColValue();
		action = data.getAction();
		targetFieldName = data.getTargetName();
		targetFieldType = data.getTargetType();
		targetObject = data.getTargetObject();
		targetPk = data.getTargetPk();
		targetPkType = data.getTargetPkType();
		time = data.getTime();
	}
	
	public String generateLogic(){
		StringBuilder sb = new StringBuilder();
		if (action.equalsIgnoreCase("insert")){
		sb.append("INSERT INTO ");
		sb.append(targetObject+" (");
		sb.append(targetFieldName+") ");
		sb.append("VALUES ('");
		sb.append(sourceFieldValue+"')");
		}
		
		else if (action.equalsIgnoreCase("update")){
			
		}
		
		else if (action.equalsIgnoreCase("delete")){
			
		}
		return sb.toString();
	}
	
}
