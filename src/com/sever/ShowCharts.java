package com.sever;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

/*UTA-CSE 6331
Xiangxiang Wang
ID: 1001681420*/


/**
 * Servlet implementation class ShowChart
 */
@WebServlet("/scatter")
public class ShowCharts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	double pop1;
	double pop2;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pop11 = request.getParameter("pop1");
		String pop22 = request.getParameter("pop2");
		 pop1=Double.parseDouble(pop11)*1000;
		 pop2=Double.parseDouble(pop22)*1000;
		
		
		response.setContentType("image/jpeg");
		try {
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(),showChart(),800,600);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	
	
	public double[][] getData(){
    	double[][] data = new double[2][100]; //��һ�д�lat���ڶ��д�long

    	
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
	    	  double regist = Double.parseDouble(rs.getString("REGISTERED"));
	    	  
	    	  if(totalpop>pop1&&totalpop<pop2) {
	    	  
	    	  data[0][i] = totalpop;
	    	  data[1][i] = regist;
	    	  i++;
	    	  }
	    	 
	      }
	      rs.close();
	      stmt.close();
	      conn.close();
	    } 
	    catch(SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        }
	    catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    finally{
            // ��������ڹر���Դ�Ŀ�
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
    	
    	
    	return data;
    }



    public JFreeChart showChart() throws Exception {  

        DefaultXYDataset xydataset = new DefaultXYDataset();  

        //������������ݼ�   
        //data = dataTransfer.getData();
        double[][] data = getData();
        //double[][] data= {{1,2,3,4,5,6,7,8,9},{2,3,4,5,6,7,8,9,1}};

        // x axis data - lung capacity(ml)  
        //data[0] = new double[]{34.2382,34.238166666666665};  

        // y axis data - time holding breath(s)  
        //data[1] = new double[]{108.91033333333333,108.91046666666666,};  

        xydataset.addSeries("magnitude position", data);  

        JFreeChart chart = ChartFactory.createScatterPlot("scatter", "total population", "registred",
                                               xydataset, 
                                               PlotOrientation.VERTICAL,
                                               true, 
                                               false,
                                               false);  
        //ChartFrame frame = new ChartFrame("ɢ��ͼ", chart, true);  
        /*chart.setBackgroundPaint(Color.white);    
        chart.setBorderPaint(Color.GREEN);    
        chart.setBorderStroke(new BasicStroke(1.5f));    
        XYPlot xyplot = (XYPlot) chart.getPlot();    

        xyplot.setBackgroundPaint(new Color(255, 253, 246));    
        ValueAxis vaaxis = xyplot.getDomainAxis();    
        vaaxis.setAxisLineStroke(new BasicStroke(1.5f));    

        ValueAxis va = xyplot.getDomainAxis(0);    
        va.setAxisLineStroke(new BasicStroke(1.5f));    

        va.setAxisLineStroke(new BasicStroke(1.5f)); // �������ϸ    
        va.setAxisLinePaint(new Color(215, 215, 215)); // ��������ɫ    
        xyplot.setOutlineStroke(new BasicStroke(1.5f)); // �߿��ϸ    
        va.setLabelPaint(new Color(10, 10, 10)); // �����������ɫ    
        va.setTickLabelPaint(new Color(102, 102, 102)); // ��������ֵ��ɫ    
        ValueAxis axis = xyplot.getRangeAxis();    
        axis.setAxisLineStroke(new BasicStroke(1.5f));    

        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot    
                .getRenderer();    
        xylineandshaperenderer.setSeriesOutlinePaint(0, Color.WHITE);    
        xylineandshaperenderer.setUseOutlinePaint(true);    
        NumberAxis numberaxis = (NumberAxis) xyplot.getDomainAxis();    
        numberaxis.setAutoRangeIncludesZero(false);    
        numberaxis.setTickMarkInsideLength(2.0F);    
        numberaxis.setTickMarkOutsideLength(0.0F);    
        numberaxis.setAxisLineStroke(new BasicStroke(1.5f));  */  

        return chart;
    } 
    


}
