package domain.rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import global.DBUtil;

public class RankDAOImpl implements RankDAO {
	private DBUtil dbutil = DBUtil.getInstance();
	
	@Override
	public List<Rank> searchAll() throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Rank> list = new ArrayList<>();
		
		try {
			con = dbutil.getConnection();
			String sql = """
				    SELECT 
				        RANK() OVER (ORDER BY m.total_point DESC) AS rnk,
				        m.name AS memberName,
				        g.gname AS groupName,
				        m.total_point AS point,
				        COUNT(s.sub_id) AS solveCount
				    FROM Member m
				    JOIN StudyGroup g ON m.group_id = g.group_id
				    LEFT JOIN Submission s
				        ON m.member_id = s.member_id
				        AND s.status = 'SUCCESS'
				    GROUP BY m.member_id, m.name, g.gname, m.total_point
				    ORDER BY rnk, m.name
				""";
			
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
	            list.add(new Rank(
	            	rs.getInt("rnk"),
	            	rs.getString("memberName"),
	                rs.getString("groupName"),
	                rs.getInt("point"),
	                rs.getInt("solveCount")
	            ));
	        }
			
		} finally {
			dbutil.close(rs, stmt, con);
		}
		
		return list;
	}
}
