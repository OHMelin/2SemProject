package db;

import ctr.DataAccessException;
import model.Range;

public interface DBIFRange {
	
	public Range findRange(int range) throws DataAccessException;

}
