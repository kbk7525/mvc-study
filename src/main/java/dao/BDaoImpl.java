package dao;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.*;

import dto.BDto;

public class BDaoImpl implements BDao{
	
	public ArrayList<BDto> showBoardList() {
		ArrayList<BDto> dtosList = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String query = "select * from board order by bId";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				BDto bto = new BDto(bId, bName, bTitle, bContent, bDate, bHit);
				dtosList.add(bto);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			closeConnection(rs, ps, con);
		}
		return dtosList;
	}
	
	public int writeContent(BDto bdto) {
		int ret = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String bName = bdto.getbName();
		String bTitle = bdto.getbTitle();
		String bContent = bdto.getbContent();
		try {
			con = getConnection();
			String query = "insert into board values (null, ?, ?, ?, CURDATE(), 0)";
			ps = con.prepareStatement(query);
			ps.setString(1, bName);
			ps.setString(2, bTitle);
			ps.setString(3, bContent);
			ret = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	public BDto viewContent(int bId) {
		upHit(bId);
		BDto dto = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String query = "select * from board where bId = ?";
			ps = con.prepareStatement(query);
			ps.setInt(1, bId);
			rs = ps.executeQuery();
			if(rs.next()) {
				int dbId = rs.getInt("bId");
				String dbName = rs.getString("bName");
				String dbTitle = rs.getString("bTitle");
				String dbContent= rs.getString("bContent");
				Timestamp dbDate = rs.getTimestamp("bDate");
				int dbHit = rs.getInt("bHit");
				dto = new BDto(dbId, dbName, dbTitle, dbContent, dbDate, dbHit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(rs, ps, con);
		}
		return dto;
	}
	public int modifyContent(BDto bdto) {
		int ret = 0;
		int bId = bdto.getbId();
		String bName = bdto.getbName();
		String bTitle = bdto.getbTitle();
		String bContent = bdto.getbContent();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			String query = "update board set bName = ?, bTitle = ?,"
					+ " bContent = ? where bId = ?";
			ps = con.prepareStatement(query);
			ps.setString(1,bName);
			ps.setString(2, bTitle);
			ps.setString(3, bContent);
			ps.setInt(4, bId);
			ret = ps.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(rs, ps, con);
		}
		return ret;
	}
	
	public int deleteContent(int bId) {
		int ret = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String query = "delete from board where bId = ?";
			ps = con.prepareStatement(query);
			ps.setInt(1, bId);
			ret = ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeConnection(rs, ps, con);
		}
		return ret;
	}
	private void upHit(int bId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			String query = "update board set bHit = bHit+1 where bId = ?";
			ps = con.prepareStatement(query);
			ps.setInt(1, bId);
			int rn = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(rs, ps, con);
		}
	}
	public Connection getConnection() {
		
		Connection conn=null;		
		String dbURL = "jdbc:mysql://localhost:3306/jdbc_db?useSSL=false";
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			System.out.println("JDBC driver load success");

			conn = DriverManager.getConnection(dbURL
					, "root","k119720148"); 			
			System.out.println("DB connection success");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver load fail !!");
		} catch (SQLException e) {
			System.out.println("DB connection fail !!");
		}
		
		return conn;
	}
	
	public void closeConnection(ResultSet set, PreparedStatement pstmt, Connection connection) {
		if(set!=null)
		{
			try {
			set.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		if(pstmt!=null)
		{
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(connection!=null)
		{
			try {
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}