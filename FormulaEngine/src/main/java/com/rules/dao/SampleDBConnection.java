/*package com.rules.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

import com.rules.entity.RulesDbConDetailsEntity;


public class SampleDBConnection {

public static Connection createDBConnection(RulesDbConDetailsEntity rulesDbConDetailsEntity) throws Exception{
		
		Connection connection = null;
		try{
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://3.60.6.196:5432/postgres","postgres","ddsdev123");
		}catch(Exception e){
			throw e;
		}
		
		return connection;
	}

public static void main(String[] args) {
	try{
		
		CallableStatement upperProc = createDBConnection(null).prepareCall("{ ? = call totalRecords() }");

		String getDBUSERByUserIdSql = "{call getDBUSERByUserId(?,?,?,?)}";
		callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSql);
		callableStatement.setInt(1, 10);
		callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
		callableStatement.registerOutParameter(4, java.sql.Types.DATE);
		
		CallableStatement upperProc = createDBConnection(null).prepareCall("{ { ? = fib(?)} }");
		upperProc.registerOutParameter(1, Types.INTEGER);
		upperProc.setInt(2, 4);
		upperProc.execute();
		System.out.println("DATA RESULT:"+ upperProc.getInt(1));
		upperProc.close();
		try{
		
			StringBuilder temp = new StringBuilder("{ ? = fib( ?,) }");
			
			temp.replace(temp.lastIndexOf(","),temp.lastIndexOf(",")+1,"");
			
			System.out.println("temp2 :"+temp);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
		
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
*/