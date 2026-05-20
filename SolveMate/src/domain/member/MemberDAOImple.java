package domain.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


import global.DBUtil;

public class MemberDAOImple implements MemberDAO {
	
	private DBUtil dbUtil = DBUtil.getInstance();

	@Override
	public int insertMember(MemberDTO member) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			String sql = " INSERT INTO Member(name, group_id) VALUES (?, ?) ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, member.getName());
			stmt.setLong(idx++, member.getGroupId());
			
			return stmt.executeUpdate();
		} finally {
			dbUtil.close(stmt, con);
		}
	}

	@Override
	public int updateMember(MemberDTO member) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			String sql = " UPDATE Member SET name = ?, total_point = ?, group_id = ? "
					+ " WHERE member_id = ? ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, member.getName());
			stmt.setInt(idx++, member.getTotalPoint());
			stmt.setLong(idx++, member.getGroupId());
			stmt.setLong(idx++, member.getMemberId());
			
			return stmt.executeUpdate();
			
		} finally {
			dbUtil.close(stmt, con);
		}
	}

	@Override
	public int deleteMember(Long groupId) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			String sql = " DELETE FROM Member WHERE member_id = ? ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, groupId);
			
			return stmt.executeUpdate();
		} finally {
			dbUtil.close(stmt, con);
		}
	}

	@Override
	public List<MemberDTO> searchAllMembers() throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<MemberDTO> memberList = new ArrayList<>();
		
		try {
			String sql = " SELECT member_id, name, total_point, created_at, group_id "
					+ " FROM Member ORDER BY member_id";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setMemberId(rs.getLong("member_id"));
				member.setName(rs.getString("name"));
				member.setTotalPoint(rs.getInt("total_point"));
				member.setCretedAt(rs.getTimestamp("created_at").toLocalDateTime());
				member.setGroupId(rs.getLong("group_id"));
				
				memberList.add(member);
			}
		} finally {
			dbUtil.close(rs, stmt, con);
		}
		return memberList;
	}

	@Override
	public List<MemberDTO> searchGroupMembers(Long groupId) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<MemberDTO> memberList = new ArrayList<>();
		
		try {
			String sql = " SELECT member_id, name, total_point, created_at, group_id "
					+ " FROM Member WHERE group_id = ? ORDER BY member_id";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, groupId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setMemberId(rs.getLong("member_id"));
				member.setName(rs.getString("name"));
				member.setTotalPoint(rs.getInt("total_point"));
				member.setCretedAt(rs.getTimestamp("created_at").toLocalDateTime());
				member.setGroupId(rs.getLong("group_id"));
				
				memberList.add(member);
			}
		} finally {
			dbUtil.close(rs, stmt, con);
		}
		return memberList;
	}

	@Override
	public MemberDTO getMember(Long memberId) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = " SELECT member_id, name, total_point, created_at, group_id "
					+ " FROM Member WHERE member_id = ? ";
			con = dbUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, memberId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setMemberId(rs.getLong("member_id"));
				member.setName(rs.getString("name"));
				member.setTotalPoint(rs.getInt("total_point"));
				member.setCretedAt(rs.getTimestamp("created_at").toLocalDateTime());
				member.setGroupId(rs.getLong("group_id"));
				
				return member;
			}
		} finally {
			dbUtil.close(rs, stmt, con);
		}
		return null;
	}
	
}
