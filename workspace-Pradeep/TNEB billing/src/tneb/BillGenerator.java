package tneb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fileprocessor.ExcelRead;

public class BillGenerator extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request , HttpServletResponse response)throws ServletException,IOException{
		
		try{
			PrintWriter out = response.getWriter();
			BillCalculator obj1 = new BillCalculator();
			String custId="";
			
			if(request.getParameter("custId")!=null && !request.getParameter("custId").isEmpty()&& request.getParameter("report")==null){
				custId = request.getParameter("custId");
				    String html = obj1.generateBill(custId);
					RequestDispatcher dispatch = request.getRequestDispatcher("../ResultPage.jsp");
					request.setAttribute("htmldata", html);
					dispatch.forward(request, response);
			}else if(request.getParameter("report") !=null){				
				 custId ="";
					ExcelRead in = new ExcelRead();
					in.ReadFile();				
				    String html = obj1.generateBill(custId);
					RequestDispatcher dispatch = request.getRequestDispatcher("../ResultPage.jsp");
					request.setAttribute("htmldata", html);
					dispatch.forward(request, response);
			}else if(request.getParameter("insertCust")!=null ){
				System.out.println("insert cust");
				insertProfile(request.getParameter("newCustId"),request.getParameter("custName"),request.getParameter("address"));
				out.println("User Inserted SuccessFully");
			} 
			
			}catch(Exception e){
				System.out.println(e);
			}
	 }
	
	public java.util.List<ReportDto> processQuery() throws SQLException{
		System.out.println("inside processQuery () ### ");
		ArrayList<ReportDto> userlist = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			final String url_db = "jdbc:mysql://localhost:3306/world";
		    final String user = "scoot";
			final String pass = "Tiger"; 
			
			con = DriverManager.getConnection(url_db,user,pass);
			String query = "select * from customer_report";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			userlist = new ArrayList<ReportDto>();
			
			ReportDto userDetails = null;
			while(rs.next()){
				userDetails = new ReportDto();
				userDetails.setCustId(rs.getString("cust_id"));
				userDetails.setStartId(rs.getString("start_id"));
				userDetails.setEndId(rs.getString("end_id"));	
				userDetails.setMonth(rs.getString("month"));
				userlist.add(userDetails);
			}
			
		}catch(Exception e){
			System.out.println("Exception "+e);
		}finally{
			con.close();
			pst.close();
			rs.close();
		}
		return userlist;
	}
	
	//select userdetails on cust search
	public java.util.List<ReportDto> processSearchQuery(String[] custIds) throws SQLException{
		System.out.println("inside processSearchQuery () ### ");
		ArrayList<ReportDto> userlist = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			final String url_db = "jdbc:mysql://localhost:3306/world";
		    final String user = "scoot";
			final String pass = "Tiger"; 
			
			con = DriverManager.getConnection(url_db,user,pass);
			if(custIds.length==1){
				String query = "select * from customer_report where cust_id in (?) ";			
				pst = con.prepareStatement(query);
				pst.setString(1, custIds[0]);
			}else{
				String query = "select * from customer_report where cust_id in (?,?,?) ";			
				pst = con.prepareStatement(query);
				for(int i=0 ; i< custIds.length ; i++){
					pst.setString(i+1, custIds[i]);
				}
			}
			rs = pst.executeQuery();
			userlist = new ArrayList<ReportDto>();			
			ReportDto userDetails = null;
			while(rs.next()){
				userDetails = new ReportDto();
				userDetails.setCustId(rs.getString("cust_id"));
				userDetails.setStartId(rs.getString("start_id"));
				userDetails.setEndId(rs.getString("end_id"));
				userDetails.setMonth(rs.getString("month"));
				userlist.add(userDetails);
			}
			
		}catch(Exception e){
			System.out.println("Exception "+e);
		}finally{
			con.close();
			pst.close();
			rs.close();
		}
		return userlist;
	}
	
	//method to get the cust details from profile table
	
	public void getCustomerProfile(ReportDto reportDto) throws SQLException{
		System.out.println("inside getCustomerProfile() ### ");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			String cust_id = (reportDto.getCustId().length() !=0)?reportDto.getCustId():"";
			if(cust_id != null){
				Class.forName("com.mysql.jdbc.Driver");
				final String url_db = "jdbc:mysql://localhost:3306/world";
			    final String user = "scoot";
				final String pass = "Tiger"; 
				
				con = DriverManager.getConnection(url_db,user,pass);
				String query = "select * from customer_profile where customer_id=?";				
				pst = con.prepareStatement(query);
				pst.setString(1,cust_id);
				rs = pst.executeQuery();
				if(rs.next()){
					reportDto.setName(rs.getString("name"));
					reportDto.setAddress(rs.getString("address"));
				}
			}else{
				reportDto.setName("XYZ");
				reportDto.setAddress("ABC street");
			}
		}catch(Exception e){
			System.out.println("Exception inside getCustomerProfile()###"+e);
		}finally{
			con.close();
			pst.close();
			rs.close();
		}
	}
	
	//for inserting customer profile
	public void insertProfile(String custId , String custName ,String address) throws SQLException{
		System.out.println("Inside insertProfile() ### ");
		Connection con = null;
		PreparedStatement pst = null;		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			final String url_db = "jdbc:mysql://localhost:3306/world";
		    final String user = "scoot";
			final String pass = "Tiger"; 			
			con = DriverManager.getConnection(url_db,user,pass);
			String query = "insert into customer_profile values (?,?,?)";
			
			pst = con.prepareStatement(query);
			pst.setString(1, custId);
			pst.setString(2, custName);
			pst.setString(3, address);
			
			pst.execute();
			
		}catch(Exception e){
			System.out.println("Exception in insertProfile()##"+e);
		}finally{
			con.close();
			pst.close();
		}
	}
	
}
