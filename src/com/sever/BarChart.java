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
                "total register", //图表标题  
                "group", //目录轴的显示标签  
                "register", //数值轴的显示标签  
                ds, //数据集  
                PlotOrientation.VERTICAL, //图表方向  
                true, //是否显示图例，对于简单的柱状图必须为false  
                false, //是否生成提示工具  
                false);         //是否生成url链接  
		
		response.setContentType("image/jpeg");
		try {
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(),chart,800,600);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
