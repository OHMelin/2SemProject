package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctr.BookingController;
import ctr.DataAccessException;
import model.Booking;

class succesfuldBooking {
	BookingController bookingController;

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void test() throws DataAccessException {
		//arrange
		bookingController = new BookingController();
		int expectedMemberID = 1;
		String expectedweaponType = "Pistol";
		String expectedDate = "10-05-2022";
		String expectedTime = "20:00";
		int expectedRangeID = 2;
		int expectedBookingID = 17;
	
		//act
		bookingController.addMember(1);
		bookingController.addWeaponType("Pistol");
		bookingController.addDate("10-05-2022");
		bookingController.addTime("20:00");
		bookingController.addRange(2);
		Booking booking = bookingController.saveBooking();
		
		//assert
		assertEquals(expectedMemberID, bookingController.getBooking().getMember().getMemberID());
		assertNotNull(bookingController.getBooking());
		assertEquals(expectedweaponType, bookingController.getBooking().getWeaponType());
		assertEquals(expectedDate, bookingController.getBooking().getDate());
		assertEquals(expectedTime, bookingController.getBooking().getTime());
		assertEquals(expectedRangeID, bookingController.getBooking().getRange().getRangeID());
		assertEquals(expectedBookingID, booking.getBookingID());
		
		
		
				
		
		
	}

}
