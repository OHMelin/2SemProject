package model;

public class Range {
	
	private int rangeID;
	private String weaponType;
	
	public Range(int rangeID, String weaponType) {
		this.rangeID = rangeID;
		this.weaponType = weaponType;
	}
	
	//Constructor
	public Range() {
		this.rangeID = 0;
		this.weaponType = "";
	}
	
	//Getters
	public int getRangeID() {
		return rangeID;
	}
	
	public String getWeaponType() {
		return weaponType;
	}

	//Setters
	public void setRangeID(int rangeID) {
		this.rangeID = rangeID;
	}

	public void setWeaponType(String weaponType) {
		this.weaponType = weaponType;
	}
}
