package com.sever;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Servlet implementation class PieChart
 */
@WebServlet("/PieChart")
public class PieChart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String stateN=request.getParameter("state");
		double stateNum=Double.parseDouble(stateN)*1000;
		double in=0;
		double[] count=new double[5];
		for(int i=0;i<count.length;i++) {
			
			count[i]=searchSql(stateNum,in);
			in=in+stateNum;
		}
		double total=0.0;
		for(int i=0;i<count.length;i++) {
			
			total=total+count[i];
		}
		
		DefaultPieDataset dataset = new DefaultPieDataset();  
		for(int i=0;i<count.length;i++) {
			double rate = 0.00;
			String s = ""+count[i];
			rate = count[i]/total;
			System.out.println(total+" "+count[i]+" "+rate);

		    dataset.setValue(s,rate );    
		    //通过工厂类生成JFreeChart对象    
		}
		JFreeChart chart = ChartFactory.createPieChart3D("IT pipe chart",  
	            dataset, true, false, false);  
	  response.setContentType("image/jpeg");
		try {
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(),chart,800,600);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public int searchSql(double statNum,double i) {
		Connection conn = null;
	    Statement stmt = null;
	    int count=0;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      conn = DriverManager.getConnection("jdbc:sqlite::resource:db/cloudDB.db");
	      //conn = DriverManager.getConnection("jdbc:sqlite:"+"resource/db/xxwDB.db");
	      conn.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = conn.createStatement();
	      String sql = "SELECT * FROM QUIZE4";
	      ResultSet rs = stmt.executeQuery(sql);
	      
	      
	      while ( rs.next() ) {
	         
	    	  double totalpop = Double.parseDouble(rs.getString("TOTALPOP"));
	    	  
	    	  if(totalpop>i&&totalpop<i+statNum) {
	    	  count++;
	    	  
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
		
		return count;
		
	}

}
