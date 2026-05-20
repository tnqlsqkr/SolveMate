package domain.rank;

import java.sql.SQLException;
import java.util.List;

public class RankServiceImpl implements RankService {
	private RankDAO dao = RankFactory.getRank();
	
	@Override
	public List<Rank> getRanking() {
	    try {
	        return dao.searchAll();
	    } catch (SQLException e) {
	        throw new RuntimeException("랭킹 조회 실패", e);
	    }
	}
}