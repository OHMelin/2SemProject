package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ctr.DataAccessException;
import model.Booking;

public class DBBooking implements DBIFBooking {

	// CheckMaxBookingID
	private int oldID = 0;
	private int currentID = 0;

	// SQL Commands
	
	//insert statment
	private static final String INSERT_Q = "insert into Booking (memberID, rangeID, date, time, timeLimit) values (?, ?, ?, ?, ?)";
	
	//Threadchecking
	private static final String SELECT_CHECK_Q = "select MAX(bookingID) from Booking";
	
	//select booking from database
	private static final String SELECT_BOOKING_Q = "select phoneno, baneID from Booking where BookingID = ?";
	
	//Find times in booking where date is search key
	//private static final String SELECT_AvailebleTIME_Q = "select date from Booking where date = ?";
	
	//Finds all the ranges for weaponType of pistol
	private static final String SELECT_PISTOL_Q = "select time, rangeID from Booking where rangeID < 8 and date = ?";
	
	//Finds all the ranges for weaponType of rifle
	private static final String SELECT_RIFLE_Q = "select time, rangeID from Booking where rangeID > 8 and date = ?";

	
	// Get the database connection
	DBConnection con = DBConnection.getInstance();

	// Prepared statements
	PreparedStatement insertPS;
	PreparedStatement CheckPS;
	PreparedStatement SelectPS;
	PreparedStatement SelectTime;
	PreparedStatement SelectPistol;
	PreparedStatement SelectRifle;

	// PreparedStatement insertOrderlinesPS;

	// Other variables
	boolean alreadyRunning = false;

	// Constructor
	public DBBooking() throws DataAccessException {
		init();
	}

	// Initialization method
	private void init() throws DataAccessException {

		try {

			insertPS = con.getConnection().prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
			CheckPS = con.getConnection().prepareStatement(SELECT_CHECK_Q);
			SelectPS = con.getConnection().prepareStatement(SELECT_BOOKING_Q);
			//SelectTime = con.getConnection().prepareStatement(SELECT_AvailebleTIME_Q);
			SelectPistol = con.getConnection().prepareStatement(SELECT_PISTOL_Q);
			SelectRifle = con.getConnection().prepareStatement(SELECT_RIFLE_Q);

		} catch (SQLException e) {

			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	//Gemmer en booking 
	@Override
	public Booking saveBooking(Booking booking) throws DataAccessException {
		
			try {
				//Set isolation level (could also be set in SQL)
				con.getConnection().setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
				
				//Start transaction
				con.startTransaction();
				
				//Create insertPS statements
				insertPS.setInt(1, booking.getMember().getMemberID()); //phoneno
				insertPS.setInt(2, booking.getRange().getRangeID());
				insertPS.setString(3, booking.getDate());
				insertPS.setString(4, booking.getTime());
				insertPS.setInt(5, booking.getTimeLimit());
				
				int id = con.executeInsertWithIdentity(insertPS);
				
				
				//Commit everything and run it in SQL
				con.commitTransaction();
				
				booking.setBookingID(id);
				
				return booking;
				
					
			} catch (SQLException e) {
					
				con.rollbackTransaction();
				throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
			}
		}
	


	//Checking the database for new Booking --> used by ThreadDB
	public boolean CheckMaxID() throws SQLException {
		ResultSet rs = CheckPS.executeQuery();
		boolean update = false;
		while (rs.next()) {
			currentID = rs.getInt(1);
		}
		if (currentID > oldID) {
			System.out.println("New booking has been created -> " + " OrderID: " + currentID);
			oldID = currentID;
			update = true;
		} else {
			//System.out.println("There has been no update in booking");
			System.out.println("Old OrderID: " + oldID + " == " + "Current OrderID: " + currentID);
			update = false;
		}
		return update;
	}

////############################################################################################################/////

	//Finds all available times for the date and weapontype
	public HashMap<Integer, List<String>> findAvailableTimes(String date, String weaponType)
			throws DataAccessException, SQLException {
		HashMap<Integer, List<String>> availableTimes = null;
		HashMap<Integer, List<String>> bookedTimes = null;
		
		switch (weaponType) {
		case "Pistol":
			availableTimes = initHashMap(1, 7);
			bookedTimes = findBookedTimes(date, SelectPistol);
			break;
		case "Rifle":
			availableTimes = initHashMap(8, 14);
			bookedTimes = findBookedTimes(date, SelectRifle);
			break;
		}
		// Retunere en liste med alle tilg�ngelige baner med deres tidspunkter til den
		// dato
		availableTimes = mergeHashMap(availableTimes, bookedTimes);
		//System.out.println(availableTimes);
		return availableTimes;

	}
	
	//Finder booked times ud fra dato og returnere en hashmap med tiderne samt banerne. 
	private HashMap<Integer, List<String>> findBookedTimes(String date, PreparedStatement weapon)
			throws SQLException {
		// HashMap Contains all range with its times
		HashMap<Integer, List<String>> bookedTimesHM = new HashMap<Integer, List<String>>();
		int rangeID;
		String time;
		weapon.setString(1, date);
		ResultSet rs = weapon.executeQuery();
		while (rs.next()) {
			rangeID = rs.getInt("rangeID");
			time = rs.getString("time");

			if (bookedTimesHM.containsKey(rangeID)) {
				bookedTimesHM.get(rangeID).add(time);
			} else {
				bookedTimesHM.put(rangeID, new ArrayList<String>());
				bookedTimesHM.get(rangeID).add(time);
			}
		}
		//System.out.println("The booked times which has been found: " + " " + bookedTimesHM + "\n");
		return bookedTimesHM;
	}

		//Genererated hashmap = genHM
	private HashMap<Integer, List<String>> initHashMap(int startRange, int limitRange) {

		HashMap<Integer, List<String>> genHM = new HashMap<Integer, List<String>>();
		for (int index = startRange; index <= limitRange; index++) {
			genHM.put(index, new ArrayList<String>());
			genHM.get(index).add("17:00");
			genHM.get(index).add("18:00");
			genHM.get(index).add("19:00");
			genHM.get(index).add("20:00");
		}
		//System.out.println("The autogenerated HS: " + " " + genHM + "\n");
		return genHM;
	}

	//Merger to hashmap sammen og sletter de tider som er i findBookedTime fra hashmap autogen
	private HashMap<Integer, List<String>> mergeHashMap(HashMap<Integer, List<String>> map_1,
			HashMap<Integer, List<String>> map_2) 
	
	{
		HashMap<Integer, List<String>> availbleTimes = map_1;
		HashMap<Integer, List<String>> bookedTimes = map_2;

		List<String> availbleTimesList = null;
		List<String> bookedTimesList = null;

		for (int key : bookedTimes.keySet()) {
			availbleTimesList = availbleTimes.get(key);
			bookedTimesList = bookedTimes.get(key);
			//System.out.println( "Range number: " + + key + " " + "The booked times for this range:" + " "  + bookedTimesList + " " + "is being removed from our availble times:" +  " " + availbleTimesList + " " + "from this range: " + " " + key);
			availbleTimesList.removeAll(bookedTimesList);
		}
		
		return availbleTimes;
	}

}
