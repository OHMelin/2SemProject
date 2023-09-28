package db;

import ctr.DataAccessException;
import model.Member;

public interface DBIFMember {

	Member findMember(int memberID) throws DataAccessException;
}
