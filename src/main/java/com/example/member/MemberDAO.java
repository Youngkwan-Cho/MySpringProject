package com.example.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberDAO {
	@Autowired
	JdbcTemplate template;

	class MemberRowMapper implements RowMapper<MemberVO> {

		@Override
		public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			MemberVO one = new MemberVO();
			one.setSid(rs.getInt("sid"));
			one.setUserid(rs.getString("userid"));
			one.setUsername(rs.getString("username"));
			one.setSex(rs.getString("sex"));
			one.setAge(rs.getInt("age"));
			one.setEmail(rs.getString("email"));
			one.setAddress(rs.getString("address"));
			one.setPhone(rs.getString("phone"));
			one.setRegdate(rs.getDate("regdate"));
			return one;
		}
	}

	private static final String M_INSERT = "insert into member(userid, password, username, sex, age, email, address, phone) values (?,sha1(?),?,?,?,?,?,?)";
	private static final String M_UPDATE = "update member set username=?, email=?, address=?, phone=? where sid=?";
	private static final String M_DELETE = "delete from member where sid=?";
	private static final String M_LIST = "select * from member order by regdate desc";

	public int insertMember(MemberVO data) {
		return template.update(M_INSERT, new Object[]{data.getUserid(), data.getPassword(), data.getUsername(), data.getSex(), data.getAge(), data.getEmail(), data.getAddress(), data.getPhone()});
	}
	// 글 삭제
	public int deleteMember(int sid) {
		return template.update(M_DELETE,  new Object[]{sid});
	}

	public int updateMember(MemberVO data) {
		return template.update(M_UPDATE,  new Object[]{data.getUsername(), data.getEmail(), data.getAddress(), data.getPhone(), data.getSid()});
	}

	public MemberVO getMember(int sid) {
		String sql="select * from member where sid=" + sid;
		return template.queryForObject(sql, new MemberRowMapper());
	}
	
	public List<MemberVO> getList() {
		return template.query(M_LIST, new MemberRowMapper());
	}
//
//	public String getaddressFilename(int sid) {
//		String filename=null;
//		conn = JDBCUtil.getConnection();
//		try {
//			stmt = conn.prepareStatement(M_GET);
//			stmt.setInt(1, sid);
//			rs = stmt.executeQuery();
//			if (rs.next()){
//				filename=rs.getString("address");
//			}
//			rs.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return filename;
//	}
}
