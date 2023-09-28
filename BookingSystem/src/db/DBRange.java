package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ctr.DataAccessException;
import model.Member;
import model.Range;

public class DBRange implements DBIFRange {
	//PreparedStatment
	private static final String SELECT_RANGE = "select weaponType from Range where rangeID = ?";
	PreparedStatement selectRange;
	
	//Constructor
	public DBRange() throws DataAccessException {
		init();
	}
	
	//init statments
	private void init() throws DataAccessException {
		Connection con = DBConnection.getInstance().getConnection();
		try {
			selectRange = con.prepareStatement(SELECT_RANGE);
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	
	//BUILD OBJECT METHOD -- calling a constructor method and inserting the values to the booking
	public Range buildRangeObject(ResultSet rs, int rangeID) {
		Range range = new Range();
		
		try {
			range.setRangeID(rangeID);
			range.setWeaponType(rs.getString("weaponType"));
		} catch (SQLException e) {}
		return range;
	}

	@Override
	//Finding a range in database with a primary key --> rangeID
	public Range findRange(int rangeID) throws DataAccessException {
		Range range = null;
		try {
			selectRange.setInt(1, rangeID);
			ResultSet rs = selectRange.executeQuery();
			while(rs.next()) {
				range = buildRangeObject(rs, rangeID);
			}	
		} catch (SQLException e) {}
		
		return range;
	}
	
	
	
}
