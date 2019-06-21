package com.sever;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q1s {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      conn = DriverManager.getConnection("jdbc:sqlite::resource:db/cloudDB.db");
	      //conn = DriverManager.getConnection("jdbc:sqlite:"+"resource/db/xxwDB.db");
	      conn.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = conn.createStatement();
	      String sql = "SELECT * FROM QUIZE4";
	      ResultSet rs = stmt.executeQuery(sql);
	      int i=0;
	      
	      while ( rs.next() ) {
	         
	    	  double totalpop = Double.parseDouble(rs.getString("TOTALPOP"));
	    	  
	    	  if(totalpop>8000&&totalpop<40000) {
	    	  
	    	  System.out.print(" "+rs.getString("STATENAME"));
	    	  }
	    	 
	      }
	      rs.close();
	      stmt.close();
	      conn.close();
	    } 
	    catch(SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        }
	    catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    finally{
            // 最后是用于关闭资源的块
            try{
                if(stmt!=null)
                stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	    System.out.println("Operation done successfully");
	}

}
