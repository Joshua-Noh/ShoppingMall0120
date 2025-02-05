package com.shop.ShoppingMall_TeamPrj.customerCenter.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ChatVO;

@Repository("chatDAO")
public class ChatDAOImpl implements ChatDAO {

    private JdbcTemplate jdbcTemplate;

    // 기본 생성자 추가
    public ChatDAOImpl() {
        // 기본 생성자 (아무 내용 없이)
    }
    
    // DataSource 주입을 위한 setter
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private static final class ChatRowMapper implements org.springframework.jdbc.core.RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ChatVO vo = new ChatVO();
            vo.setChatId(rs.getInt("chat_id"));
            vo.setUserId(rs.getInt("user_id"));
            vo.setSender(rs.getString("sender"));
            vo.setMessage(rs.getString("message"));
            vo.setSentAt(rs.getTimestamp("sent_at"));
            return vo;
        }
    }
    
    public List getChatHistory(Integer userId) {
        String sql = "SELECT * FROM chat_log WHERE user_id = ? ORDER BY sent_at ASC";
        return jdbcTemplate.query(sql, new Object[] { userId }, new ChatRowMapper());
    }
    
    public void insertChat(ChatVO chat) {
        String sql = "INSERT INTO chat_log (user_id, sender, message) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, new Object[] { chat.getUserId(), chat.getSender(), chat.getMessage() });
    }
}
