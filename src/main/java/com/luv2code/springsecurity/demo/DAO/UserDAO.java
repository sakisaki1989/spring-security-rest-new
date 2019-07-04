package com.luv2code.springsecurity.demo.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.model.Users;
@Repository
public class UserDAO {
	 private JdbcTemplate jdbcTemplate;
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
	    }
	    public Users getUserInfo(String username){
	    	String sql = "SELECT u.username name, u.password pass, u.role role FROM "+
	    			     "users u WHERE "+
	    			     "u.enabled =1 and u.username = ?";
	    	Users user = (Users)jdbcTemplate.queryForObject(sql, new Object[]{username},
	    		new RowMapper<Users>() {
		            public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	Users loggeduser = new Users();
		            	loggeduser.setUsername(rs.getString("name"));
		            	loggeduser.setPassword(rs.getString("pass"));
		            	loggeduser.setRole(rs.getString("role"));
		                return loggeduser;
		            }
	        });
	    	return user;
	    }
}
