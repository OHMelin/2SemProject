package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctr.BookingController;
import ctr.DataAccessException;
import model.Member;

class InvalidMemberID {
	BookingController bookingController;
	BookingController bookingController_1;
	
	@BeforeEach
	void setUp() throws Exception {
		bookingController = new BookingController();
		bookingController_1 = new BookingController();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	//Testing if a memberID which is invaild returns nothing - er det her en integration test? Er det okay vi kun har integration test.
	@Test
	void invalidMemberID() throws DataAccessException {
		
		//Arrange
		int memberID = 5;
		
		//Act
		bookingController.addMember(memberID);
		
		
		//Assert
		assertNull(bookingController.getBooking().getMember(), "Should return null and display a error message to the user in the gui");
		
		
	}
	
	
	

	
	
	
	

}
