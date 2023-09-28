package model;

public class Booking {
	private int bookingID;
	private String weaponType;
	private String time;
	private String date;
	private Range range;
	private Member member;
	private int timeLimit;
	
	//Constructor
	public Booking(Member member) {
		this.weaponType = null;
		this.time = null;
		this.date = null;
		this.range = null;
		this.member = member;
	}

	//ADD
	public void addMember(Member member) {
		this.member = member;
	}

	public void addWeaponType(String weaponType) {
		this.weaponType = weaponType;
	}
	
	public void addDate(String date) {
		this.date = date;
	}
	
	public void addTime(String time) {
		this.time = time;
	}
	
	public void addRange(Range range) {
		this.range = range;
	}

	public void addTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	//setter
	public void setBookingID(int id){
		this.bookingID = id;
	}
	
	
	
	
	//Getters
	
	public int getBookingID() {
		return bookingID;
	}
	public String getTime() {
		return time;
	}

	public String getDate() {
		return date;
	}

	public Range getRange() {
		return range;
	}

	public Member getMember() {
		return member;
	}

	
	public String getWeaponType() {
		return this.weaponType;
	}

	public int getTimeLimit() {
		return timeLimit;
	}
}
