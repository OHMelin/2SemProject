package model;

public class Member {
	
	private String name;
	private String phoneno;
	private String email;
	private int memberID;
	
	
	public Member() {
		this.name = null;
		this.phoneno = null;
		this.memberID = 0;
	}
	
	//Setters
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	//Getters
	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	
	public int getMemberID() {
		return memberID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	
}



