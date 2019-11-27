package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserInfoService {
    @Autowired
    JdbcTemplate template;

    public List<User> findAll() {
        String sql = "select * from user";
        RowMapper<User> rm = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                Long id =resultSet.getLong("id");
                String username =  resultSet.getString("username");
                String name =  resultSet.getString("name");
                String email= resultSet.getString("email");

                User user = new User(id,username,name,email);
                user.setAddress(resultSet.getString("address"));
                user.setUsername(resultSet.getString("username"));
                return user;
            }

        };
        return template.query(sql, rm);
    }
}

