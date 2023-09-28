package gui;

import java.sql.SQLException;

import ctr.DataAccessException;
import db.DBBooking;

public class ThreadDB extends Thread {
private Monitor monitor = null;
private DBBooking Database = null;

public ThreadDB() throws DataAccessException {
	monitor = Monitor.getInstance();
	Database = new DBBooking();
}


public void run() {
	 
//Checking the database if a new booking had been added every second. If a change has been made it will notify threads to wake up and update. 
	while(true) {
	try {
		Thread.sleep(1000);
		System.out.println("Checking the database for: ");
		if( Database.CheckMaxID() == true) {
			monitor.notifyAllMethod();
		}
	} catch (SQLException | InterruptedException e) {}
}
}


}
