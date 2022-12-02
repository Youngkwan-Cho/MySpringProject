package com.example.crud;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDAO {
	private static JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template=template;
	}
	private static final String M_INSERT = "insert into member(userid, password, username, email, photo, detail) values (?,sha1(?),?,?,?,?)";
	private static final String M_UPDATE = "update member set username=?, email=?, photo=?, detail=? where sid=?";
	private static final String M_DELETE = "delete from member where sid=?";
	private static final String M_GET = "select * from member where sid=?";
	private static final String M_LIST = "select * from member order by regdate desc";

	public static int insertMember(MemberVO data) {
		return template.update(M_INSERT, new Object[]{data.getUserid(), data.getPassword(), data.getUsername(), data.getEmail(), data.getPhoto(), data.getDetail()});
	}

	// 글 삭제
	public static int deleteMember(int sid) {
		return template.update(M_DELETE, new Object[]{sid});
	}

	public static int updateMember(MemberVO data) {
		return template.update(M_UPDATE, new Object[]{data.getUsername(), data.getEmail(), data.getPhoto(), data.getDetail(), data.getSid()});
	}	
	
	public static MemberVO getMember(int sid) {
		return template.queryForObject(M_GET, new Object[]{sid}, new BeanPropertyRowMapper<MemberVO>(MemberVO.class));
	}
	
	public static List<MemberVO> getList() throws SQLException {
		return template.query(M_LIST, new RowMapper<MemberVO>(){

			@Override
			public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberVO one = new MemberVO();
				one.setSid(rs.getInt("sid"));
				one.setUserid(rs.getString("userid"));
				one.setUsername(rs.getString("username"));
				one.setPhoto(rs.getString("photo"));
				one.setEmail(rs.getString("email"));
				one.setRegdate(rs.getDate("regdate"));
				return one;
			}
		});
	}
/*
	public String getPhotoFilename(int sid) {
		String filename=null;
		conn = JDBCUtil.getConnection();
		try {
			stmt = conn.prepareStatement(M_GET);
			stmt.setInt(1, sid);
			rs = stmt.executeQuery();
			if (rs.next()){
				filename=rs.getString("photo");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filename;
	}
	*/
}
