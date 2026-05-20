package domain.problem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.print.attribute.standard.PresentationDirection;
import javax.swing.JOptionPane;

import java.util.ArrayList;


import global.DBUtil;


public class ProblemDAOImple implements ProblemDAO {
	
	private DBUtil dbUtil = DBUtil.getInstance();

	@Override
	public int insertProblem(ProblemDTO problem) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			String sql = " INSERT INTO Problem(title, url, ds_id)"
					+ "VALUES (?, ?, ?) ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, problem.getTitle());
			stmt.setString(idx++, problem.getUrl());
			stmt.setLong(idx++, problem.getDsId());
			return stmt.executeUpdate();
		} finally {
			dbUtil.close(stmt, con);
		}
	}

	@Override
	public int updateProblem(ProblemDTO problem) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			String sql = " UPDATE Problem SET title = ?, url = ?, ds_id = ? "
					+ " WHERE problem_id = ? ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, problem.getTitle());
			stmt.setString(idx++, problem.getUrl());
			stmt.setLong(idx++, problem.getDsId());
			stmt.setLong(idx++, problem.getProblemId());
			return stmt.executeUpdate();
		} finally {
			dbUtil.close(stmt, con);
		}
	}

	@Override
	public int deleteProblem(Long problemId) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			String sql = " DELETE FROM Problem WHERE problem_id = ? ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, problemId);
			return stmt.executeUpdate();
		} finally {
			dbUtil.close(stmt, con);
		}
	}

	@Override
	public List<ProblemDTO> searchAllProblems() throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ProblemDTO> problemList = new ArrayList<>();
		
		try {
			String sql = " SELECT problem_id, title, url, ds_id,"
					+ " ds.platform, ds.level, ds.point "
					+ " FROM Problem p"
					+ " JOIN DifficultScore ds"
					+ " USING(ds_id)"
					+ " ORDER BY problem_id ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				ProblemDTO problem = new ProblemDTO();
				problem.setProblemId(rs.getLong("problem_id"));
				problem.setTitle(rs.getString("title"));
				problem.setUrl(rs.getString("url"));				
				problem.setDsId(rs.getLong("ds_id"));
				
				DifficultyScoreDTO difficultyScore = new DifficultyScoreDTO();
				difficultyScore.setDsId(rs.getLong("ds_id"));
				difficultyScore.setPlatform(rs.getString("platform"));
				difficultyScore.setLevel(rs.getString("level"));
				difficultyScore.setPoint(rs.getInt("point"));
	            
				problem.setDifficultyScore(difficultyScore);
				
				problemList.add(problem);
			}
		} finally {
			dbUtil.close(rs, stmt, con);
		}
		return problemList;
	}

	@Override
	public ProblemDTO getProblem(Long problemId) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = " SELECT p.problem_id, p.title, p.url, p.ds_id, "
					+ " ds.platform, ds.level, ds.point "
					+ " FROM Problem p "
					+ " JOIN DifficultScore ds "
					+ " USING(ds_id)"
					+ " WHERE problem_id = ? ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, problemId);
			rs = stmt.executeQuery();
			while(rs.next()) {
				ProblemDTO problem = new ProblemDTO();
				problem.setProblemId(rs.getLong("problem_id"));
				problem.setTitle(rs.getString("title"));
				problem.setUrl(rs.getString("url"));				
				problem.setDsId(rs.getLong("ds_id"));

				DifficultyScoreDTO difficultyScore = new DifficultyScoreDTO();
				difficultyScore.setDsId(rs.getLong("ds_id"));
				difficultyScore.setPlatform(rs.getString("platform"));
				difficultyScore.setLevel(rs.getString("level"));
				difficultyScore.setPoint(rs.getInt("point"));
	            
				problem.setDifficultyScore(difficultyScore);
				
				return problem;
			}
		} finally {
			dbUtil.close(rs, stmt, con);
		}
		return null;
	}

	@Override
	public List<DifficultyScoreDTO> searchAllDifficulties() throws SQLException {
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    List<DifficultyScoreDTO> difficultyList = new ArrayList<>();

	    try {
	        String sql = " SELECT ds_id, platform, level, point "
	                   + " FROM DifficultScore "
	                   + " ORDER BY platform, ds_id ";

	        con = dbUtil.getConnection();
	        stmt = con.prepareStatement(sql);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            DifficultyScoreDTO difficulty = new DifficultyScoreDTO();

	            difficulty.setDsId(rs.getLong("ds_id"));
	            difficulty.setPlatform(rs.getString("platform"));
	            difficulty.setLevel(rs.getString("level"));
	            difficulty.setPoint(rs.getInt("point"));

	            difficultyList.add(difficulty);
	        }
	    } finally {
	        dbUtil.close(rs, stmt, con);
	    }

	    return difficultyList;
	}


}
