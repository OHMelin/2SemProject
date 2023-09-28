package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ctr.DataAccessException;
import model.Member;

public class DBMember implements DBIFMember {
	
	private static final String SELECTALL_Q = "SELECT name from Member";
	private PreparedStatement selectPS;
	private static final String SELECT_PHONENO = SELECTALL_Q + " where memberID = ?";
	private PreparedStatement selectMember;
	
	public DBMember() throws DataAccessException {
		init();
	}
	
	private void init() throws DataAccessException {
		Connection con = DBConnection.getInstance().getConnection();
		try {
			selectPS = con.prepareStatement(SELECTALL_Q);
			selectMember = con.prepareStatement(SELECT_PHONENO);
			
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	public Member buildMemberObject(ResultSet rs, int memberID) {
		Member member = new Member();
		try { 
			member.setName(rs.getString("name"));
			member.setMemberID(memberID);
			//member.setPhoneno(rs.getString("phoneno"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}
	
	@Override
	public Member findMember(int memberID) throws DataAccessException {
		Member member = null;
		try {
			selectMember.setInt(1, memberID);
			ResultSet rs = selectMember.executeQuery();
			while(rs.next()) {
				member = buildMemberObject(rs, memberID);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}
}
