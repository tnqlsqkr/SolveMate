package domain.rank;

public class RankFactory {
	private static RankDAO dao = new RankDAOImpl();
	
	public static RankDAO getRank() {
        return dao;
    }
}
