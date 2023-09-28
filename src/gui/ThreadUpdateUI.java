package gui;

import java.sql.SQLException;

import ctr.DataAccessException;

public class ThreadUpdateUI extends Thread {
private Monitor monitor = null;
private BookingUI_1 UI = null;

public ThreadUpdateUI (BookingUI_1 frame) {
	this.monitor = Monitor.getInstance();
	this.UI = frame;
}


public void run() {
	while(true) {
			try {
				monitor.waitThread();
			} catch (InterruptedException e) {}
			try {
				UI.update();
			} catch (DataAccessException e) {} catch (SQLException e) {}
			System.out.println(" Gui has has been updated ");
		}
		//Calls the UI METHOD "x.DisplayUdate()"
		//Need a reference to the UI Method and makes sure it's the same object.
		
	}
}
