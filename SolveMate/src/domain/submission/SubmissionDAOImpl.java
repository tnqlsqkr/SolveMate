package domain.submission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import global.DBUtil;


public class SubmissionDAOImpl implements SubmissionDAO {
	private DBUtil dbutil = DBUtil.getInstance();

	@Override
	public void add(Submission submission) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			con = dbutil.getConnection();
			String sql = "INSERT INTO submission (problem_id, member_id, status) VALUES (?, ?, ?)";
			
			stmt = con.prepareStatement(sql);
			int idx = 1;
			
			stmt.setLong(idx++, submission.getProblemId());
			stmt.setLong(idx++, submission.getMemberId());
			stmt.setString(idx++, submission.getStatus().name());
			
		    stmt.executeUpdate();
		    
		} finally {
			dbutil.close(stmt, con);
		}
	}

	@Override
	public void update(Submission submission) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			con = dbutil.getConnection();
			String sql = "UPDATE submission SET status = ? WHERE sub_id = ?";
			stmt = con.prepareStatement(sql);
			
			int idx = 1;
			
			stmt.setString(idx++, submission.getStatus().name());
			stmt.setLong(idx++, submission.getSubId());
			
			stmt.executeUpdate();
			
		} finally {
			dbutil.close(stmt, con);
		}
	}

	@Override
	public void remove(long subId) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			con = dbutil.getConnection();
			String sql = "DELETE FROM submission WHERE sub_id = ?";
			stmt = con.prepareStatement(sql);
			
			stmt.setLong(1, subId);
			stmt.executeUpdate();
			
		} finally {
			dbutil.close(stmt, con);
		}
	}

	@Override
	public Submission search(long subId) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Submission submission = null;
		
		try {
			con = dbutil.getConnection();
			String sql = "SELECT * FROM submission WHERE sub_id = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, subId);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				submission = new Submission();
				
				submission.setSubId(rs.getLong("sub_id"));
				submission.setProblemId(rs.getLong("problem_id"));
				submission.setMemberId(rs.getLong("member_id"));
				
				submission.setStatus(
	                    SubmissionStatus.valueOf(rs.getString("status"))
	            );
				
				submission.setSubmittedAt(rs.getTimestamp("submitted_at").toLocalDateTime());
				
				return submission; 
				
			}
		} finally {
			dbutil.close(rs, stmt, con);
		}
		
		return null;
	}

	@Override
	public List<Submission> searchAll() throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Submission> list = new ArrayList<>();
		try {
			con = dbutil.getConnection();
			String sql = " SELECT * FROM submission ";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Submission submission = new Submission();
				
				submission.setSubId(rs.getLong("sub_id"));
				submission.setProblemId(rs.getLong("problem_id"));
				submission.setMemberId(rs.getLong("member_id"));
				submission.setStatus(SubmissionStatus.valueOf(rs.getString("status")));
				submission.setSubmittedAt(rs.getTimestamp("submitted_at").toLocalDateTime());
				
				list.add(submission);
				
			}
		} finally {
			dbutil.close(rs, stmt, con);
		}
		
		return list;
	}
	
	@Override 
	public Submission findByMemberAndProblem(long memberId, long problemId) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
	    	con = dbutil.getConnection();
	    	String sql = """
	    	        SELECT *
	    	        FROM submission
	    	        WHERE member_id = ?
	    	          AND problem_id = ?
	    	        LIMIT 1
	    	    """;
	    	stmt = con.prepareStatement(sql);
	    	
	    	int idx = 1;
	    	
	    	stmt.setLong(idx++, memberId);
	    	stmt.setLong(idx++, problemId);
	    	
	    	rs = stmt.executeQuery();
	    	
	    	if (rs.next()) {
	            Submission s = new Submission();
	            s.setSubId(rs.getLong("sub_id"));
	            s.setMemberId(rs.getLong("member_id"));
	            s.setProblemId(rs.getLong("problem_id"));
	            s.setStatus(SubmissionStatus.valueOf(rs.getString("status")));
	            return s;
	        }

	    } finally {
	    	dbutil.close(rs, stmt, con);
	    }
	    
	    return null;
	}
	
	@Override
	public void addPoint(Long memberId, int point) throws SQLException {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dbutil.getConnection();
			String sql = "UPDATE Member SET total_point = total_point + ? WHERE member_id = ?";
			stmt = con.prepareStatement(sql);
					
			int idx = 1;
			
			stmt.setInt(idx++, point);
	        stmt.setLong(idx++, memberId);
	        stmt.executeUpdate();
	        
		} finally {
	    	dbutil.close(rs, stmt, con);
	    }
	}
	
	@Override
	public int getScore(long problemId) throws SQLException {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dbutil.getConnection();
			
			String sql = """
			        SELECT d.point
			        FROM Problem p
			        JOIN DifficultScore d ON p.ds_id = d.ds_id
			        WHERE p.problem_id = ?
			    """;
			
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, problemId);
			
			rs = stmt.executeQuery();
			
			if(rs.next())
				return rs.getInt("point");
		} finally {
	    	dbutil.close(rs, stmt, con);
	    }
		
	    return 0;
	}
}
