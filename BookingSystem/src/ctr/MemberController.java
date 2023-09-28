package ctr;

import db.DBMember;
import model.Member;
import db.DBIFMember;

public class MemberController {
	
	private DBIFMember DBMember;
	
	public MemberController() throws DataAccessException {
		DBMember = new DBMember();
	}
	
	public Member findMember(int memberID) throws DataAccessException {
		return DBMember.findMember(memberID);
	}
}
