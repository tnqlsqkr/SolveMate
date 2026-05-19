package domain.study;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


import global.DBUtil;

public class StudyDAOImple implements StudyDAO {
	
	private DBUtil dbUtil = DBUtil.getInstance();
	
	@Override
	public int insertStudy(String gname) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			String sql = " INSERT into StudyGroup(gname) values(?) ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, gname);
			return stmt.executeUpdate();
		} finally {
			dbUtil.close(stmt, con);
		}
	}

	@Override
	public int updateStudy(Long groupId, String newGname) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			String sql = " UPDATE StudyGroup SET gname = ? WHERE group_id = ? ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++,newGname);
			stmt.setLong(idx++,groupId);
			return stmt.executeUpdate();
		} finally {
			dbUtil.close(stmt,con);
		}
	}

	@Override
	public int deleteStudy(Long groupId) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			String sql = " DELETE FROM StudyGroup WHERE group_id = ? ";	
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, groupId);
			return stmt.executeUpdate();
		} finally {
			dbUtil.close(stmt,con);
		}
	}
	
	@Override
	public List<StudyDTO> seachAllStudies() throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<StudyDTO> studyList = new ArrayList<>();
		
		try {
			String sql = " SELECT group_id, gname, created_at "
			           + "FROM StudyGroup "
			           + "ORDER BY group_id ";
			
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				StudyDTO study = new StudyDTO();
				study.setGroupId(rs.getLong("group_id"));
				study.setGname(rs.getString("gname"));
				study.setCreate_at(rs.getTimestamp("created_at").toLocalDateTime());
			
				studyList.add(study);
			}
		} finally {
			dbUtil.close(rs, stmt, con);
		}
		return studyList;
	}
	
	@Override
	public StudyDTO getStudy(Long groupId) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<StudyDTO> studyList = new ArrayList<>();
		
		try {
			String sql = " SELECT group_id, gname, created_at "
			           + "FROM StudyGroup "
			           + "WHERE group_id = ? ";
			
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, groupId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				StudyDTO study = new StudyDTO();
				study.setGroupId(rs.getLong("group_id"));
				study.setGname(rs.getString("gname"));
				study.setCreate_at(rs.getTimestamp("created_at").toLocalDateTime());
				return study;
			}
		} finally {
			dbUtil.close(rs, stmt, con);
		}
		return null;
	}

}
