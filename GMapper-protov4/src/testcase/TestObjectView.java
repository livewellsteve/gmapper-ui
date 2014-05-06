package testcase;

import java.util.ArrayList;

import controller.model.MysqlMeta;
import controller.model.PostgresMeta;

public class TestObjectView {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ArrayList<String> objSource = new ArrayList<String>();
        ArrayList<String> objTarget = new ArrayList<String>();
        
        objSource = new MysqlMeta("jdbc:mysql://mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306/dummydata","cmpe","password").getTables();
        objTarget = new PostgresMeta("jdbc:postgresql://mydbinstancepg1.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:5432/postgres","cmpe","password").getTables();
   
        for(String s: objSource){
        	System.out.println(s);
        }
        
        for(String s: objTarget){
        	System.out.println(s);
        }
	}

}
