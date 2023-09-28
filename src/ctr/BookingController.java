package ctr;

import model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.*;

public class BookingController {

	private MemberController memberController;
	private RangeController rangeController;
	private DBIFBooking dbBooking;
	private Booking booking;
	//List with timesAndRanges Found
	private HashMap<Integer, List<String>> availableTimes = null;

	public BookingController() throws DataAccessException {
		this.memberController = new MemberController();
		this.rangeController = new RangeController();
		this.dbBooking = new DBBooking();
	}
	
	//
	public void createBooking(Member member) {
		this.booking = new Booking(member);
	}
	
	//Adding methods
	public void addMember(int memberID) throws DataAccessException {
		Member member = memberController.findMember(memberID);
		booking = new Booking(member);
	}
	
	public void addWeaponType(String weaponType) throws DataAccessException {
		this.booking.addWeaponType(weaponType);
	}
	
	
	public void addRange(int rangeID) throws DataAccessException {
		Range range  = rangeController.findRange(rangeID);
		booking.addRange(range);
	}
	
	
	public void addDate(String date) throws DataAccessException {
		booking.addDate(date);
	}
	
	public void addTime(String time) {
		booking.addTime((time));
	}
	
	
	
	public void setBookingToNull() {
		booking = null;
	}



	public HashMap<Integer, List<String>> findAvailableTimes(String date) throws DataAccessException, SQLException{ 
		booking.addDate(date);
		return dbBooking.findAvailableTimes(date, booking.getWeaponType());
		
	}


	public Booking getBooking() {
		return booking;
	}

	public Booking saveBooking() throws DataAccessException {
		Booking booking1 = dbBooking.saveBooking(booking);
		return booking1;
	}
	
	public HashMap<Integer, List<String>> getAvailableTimes(){
		return availableTimes;
	}
	
	
	// --------------------------------------------------------------------------------------Metoder--------------------------------------------------------------------
	
}

