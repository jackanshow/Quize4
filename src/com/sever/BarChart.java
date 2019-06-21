package com.sever;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Servlet implementation class BarChart
 */
@WebServlet("/BarChart")
public class BarChart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String s = request.getParameter("range");
		double range=Double.parseDouble(s);
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		int[] count = new int[10];
		for(int i=0;i<count.length;i++) {					
			for(int j=1;j<=range;j++) {
					if(((j*j*j)%10) == i){
						count[i]++;
					}
					
			}
			ds.addValue(count[i], String.valueOf(i), "");
		}
		JFreeChart chart = ChartFactory.createBarChart3D(  
                "total register", //ͼ�����  
                "group", //Ŀ¼�����ʾ��ǩ  
                "register", //��ֵ�����ʾ��ǩ  
                ds, //���ݼ�  
                PlotOrientation.VERTICAL, //ͼ����  
                true, //�Ƿ���ʾͼ�������ڼ򵥵���״ͼ����Ϊfalse  
                false, //�Ƿ�������ʾ����  
                false);         //�Ƿ�����url����  
		
		response.setContentType("image/jpeg");
		try {
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(),chart,800,600);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
