package testcase;

import controller.model.MysqlAdaptor;

public class TestTableName {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MysqlAdaptor adp = new MysqlAdaptor("jdbc:mysql://mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306/dummydata","cmpe","password");
		adp.createListenerTable();

	}

}
