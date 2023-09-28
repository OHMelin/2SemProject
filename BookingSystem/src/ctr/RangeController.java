package ctr;

import db.DBIFRange;
import model.Range;

import java.util.List;

import db.*;

public class RangeController {
	
	private DBIFRange DBRange;
	
	public RangeController() throws DataAccessException {
		DBRange = new DBRange();
	}
	
	//Finder den bestemte range vi leder efter
	public Range findRange(int rangeID) throws DataAccessException {
		 Range range = DBRange.findRange(rangeID);
		return range;
	}
	
}
