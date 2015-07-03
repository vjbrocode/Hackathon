package tneb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BatchDao {

	public void batchReportInsert(List<ReportDto> totalList) throws SQLException{
		System.out.println("Inside batchInsert()###");

		Connection con = null;
		PreparedStatement pst = null;		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			final String url_db = "jdbc:mysql://localhost:3306/world";
		    final String user = "scoot";
			final String pass = "Tiger"; 			
			con = DriverManager.getConnection(url_db,user,pass);
			//System.out.println("Inside batchInsert()###");
			String query = "insert into customer_report values (?,?,?,?)";
			pst = con.prepareStatement(query);
			//System.out.println("reportDto.getCustId(): "+totalList);
			for(ReportDto reportDto : totalList) {	
				System.out.println("reportDto.getCustId(): "+reportDto.toString());
				pst.setString(1,reportDto.getCustId());
				pst.setString(2,reportDto.getStartId());
				pst.setString(3,reportDto.getEndId());
				pst.setString(4,reportDto.getMonth());
				pst.addBatch();		
			}
			//System.out.println(pst.addBatch());
			pst.executeBatch();
			
		}catch(Exception e){
			System.out.println("Exception batchInsert() ### "+e);
		}finally{
			con.close();
			pst.close();
		}
	}
	
	public void batchRateInsert(List<ReportDto> rateTable) throws SQLException{
		System.out.println("Inside rateinsertion()###");

		Connection con = null;
		PreparedStatement pst = null;		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			final String url_db = "jdbc:mysql://localhost:3306/world";
		    final String user = "scoot";
			final String pass = "Tiger"; 
			
			con = DriverManager.getConnection(url_db,user,pass);
			String query = "insert into rate_per_unit values (?,?,?)";
			pst = con.prepareStatement(query);
			
			for(ReportDto reportDto : rateTable) {				
				pst.setString(1,reportDto.getTier_start());
				pst.setString(2,reportDto.getTier_end());
				pst.setString(3,reportDto.getUnit());
				pst.addBatch();		
			}
			pst.executeBatch();
			
		}catch(Exception e){
			System.out.println("Exception batchProfileInsert() ### "+e);
		}finally{
			con.close();
			pst.close();
		}
	}
}
