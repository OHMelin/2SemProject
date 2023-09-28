package db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import ctr.DataAccessException;
import model.Booking;

public interface DBIFBooking {
	
//	public void addBooking(Booking booking) throws DataAccessException;
//	public void addBookingLines() throws DataAccessException;
	
	public HashMap<Integer, List<String>> findAvailableTimes(String date, String weaponType) throws DataAccessException, SQLException;
	//has to return a list with times in it
	
	public Booking saveBooking(Booking booking) throws DataAccessException;
}
