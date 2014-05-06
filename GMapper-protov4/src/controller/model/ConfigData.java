package controller.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ConfigData implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;
	/*
	 * Todo: 4/24 define adaptor interface: connect, disconnect, check session, get metadata
	 * adaptor: define id, type as fields
	 * 
	 */
	private int sourceAdaptorId;
	private String sourceEndpoint;
	private String sourceDbName;
	private String sourceUser;
	private String sourcePassword;
	private String sourceType;
	
	private int targetAdaptorId;
	private String targetEndpoint;
	private String targetDbName;
	private String targetUser;
	private String targetPassword;
	private String targetType;
	
	private String turn;
	
	private int mapperId;
	private String mapperName;
	private String description;
	private String sourceObject;
	private String targetObject;
	private ArrayList<String> sourcePk;
	private ArrayList<String> targetPk;
	
	private int mapperFieldId;
	private String action;
	private Map<String,String> sourceField;
	private Map<String,String> targetField;
	
	
	public ConfigData(){
		
	}
	
	public Map<String, String> getSourceField() {
		return sourceField;
	}

	public void setSourceField(Map<String, String> sourceField) {
		this.sourceField = sourceField;
	}

	public Map<String, String> getTargetField() {
		return targetField;
	}

	public void setTargetField(Map<String, String> targetField) {
		this.targetField = targetField;
	}

	public int getMapperFieldId() {
		return mapperFieldId;
	}

	public void setMapperFieldId(int mapperFieldId) {
		this.mapperFieldId = mapperFieldId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}



	public ArrayList<String> getSourcePk() {
		return sourcePk;
	}

	public void setSourcePk(ArrayList<String> sourcePk) {
		this.sourcePk = sourcePk;
	}

	public ArrayList<String> getTargetPk() {
		return targetPk;
	}

	public void setTargetPk(ArrayList<String> targetPk) {
		this.targetPk = targetPk;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public int getUserId(){
		return userId;
	}
	
	public void setUserId(int userId){
		this.userId = userId;
	}
	
	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}
	
	
	public int getSourceAdaptorId() {
		return sourceAdaptorId;
	}
	public void setSourceAdaptorId(int sourceAdaptorId) {
		this.sourceAdaptorId = sourceAdaptorId;
	}
	public String getSourceEndpoint() {
		return sourceEndpoint;
	}
	public void setSourceEndpoint(String sourceEndpoint) {
		this.sourceEndpoint = sourceEndpoint;
	}
	public String getSourceDbName() {
		return sourceDbName;
	}
	public void setSourceDbName(String sourceDbName) {
		this.sourceDbName = sourceDbName;
	}
	public String getSourceUser() {
		return sourceUser;
	}
	public void setSourceUser(String sourceUser) {
		this.sourceUser = sourceUser;
	}
	public String getSourcePassword() {
		return sourcePassword;
	}
	public void setSourcePassword(String sourcePassword) {
		this.sourcePassword = sourcePassword;
	}
	public int getTargetAdaptorId() {
		return targetAdaptorId;
	}
	public void setTargetAdaptorId(int targetAdaptorId) {
		this.targetAdaptorId = targetAdaptorId;
	}
	public String getTargetEndpoint() {
		return targetEndpoint;
	}
	public void setTargetEndpoint(String targetEndpoint) {
		this.targetEndpoint = targetEndpoint;
	}
	public String getTargetDbName() {
		return targetDbName;
	}
	public void setTargetDbName(String targetDbName) {
		this.targetDbName = targetDbName;
	}
	public String getTargetUser() {
		return targetUser;
	}
	public void setTargetUser(String targetUser) {
		this.targetUser = targetUser;
	}
	public String getTargetPassword() {
		return targetPassword;
	}
	public void setTargetPassword(String targetPassword) {
		this.targetPassword = targetPassword;
	}
	public int getMapperId() {
		return mapperId;
	}
	public void setMapperId(int mapperId) {
		this.mapperId = mapperId;
	}
	public String getMapperName() {
		return mapperName;
	}
	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSourceObject() {
		return sourceObject;
	}
	public void setSourceObject(String sourceObject) {
		this.sourceObject = sourceObject;
	}
	public String getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}
	
	
	
}
