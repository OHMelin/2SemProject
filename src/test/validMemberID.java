package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctr.BookingController;
import ctr.DataAccessException;

class validMemberID {
	BookingController bookingController;
	@BeforeEach
	void setUp() throws Exception {
		bookingController = new BookingController();
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void test() throws DataAccessException {
		
		//arrange
		int expectedmemberID = 1;
		int memberID = 1;
		
		
		//act
		bookingController.addMember(memberID);
		
		
		
		//assert
		assertEquals(expectedmemberID, bookingController.getBooking().getMember().getMemberID());
		assertNotNull(bookingController.getBooking());
		
		
	}

}
