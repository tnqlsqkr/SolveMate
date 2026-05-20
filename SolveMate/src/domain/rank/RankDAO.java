package domain.rank;

import java.sql.SQLException;
import java.util.List;

public interface RankDAO {
	List<Rank> searchAll()						throws SQLException;
}
