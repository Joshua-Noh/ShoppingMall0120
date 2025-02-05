package com.shop.ShoppingMall_TeamPrj.customerCenter.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ConsultationVO;

@Repository("consultationDAO")
public class ConsultationDAOImpl implements ConsultationDAO {

    private JdbcTemplate jdbcTemplate;
    
    // 기본 생성자 추가
    public ConsultationDAOImpl() {
        // 기본 생성자; DataSource는 setter로 주입받음.
    }
    
    // DataSource를 주입받는 setter
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private static final class ConsultationRowMapper implements org.springframework.jdbc.core.RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConsultationVO vo = new ConsultationVO();
            vo.setConsultationId(rs.getInt("consultation_id"));
            vo.setUserId(rs.getInt("user_id"));
            vo.setSubject(rs.getString("subject"));
            vo.setMessage(rs.getString("message"));
            vo.setReply(rs.getString("reply"));
            vo.setStatus(rs.getString("status"));
            vo.setCreatedAt(rs.getTimestamp("created_at"));
            vo.setUpdatedAt(rs.getTimestamp("updated_at"));
            return vo;
        }
    }
    
    public List getAllConsultations() {
        String sql = "SELECT * FROM consultation_log ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new ConsultationRowMapper());
    }
    
    public List getConsultationsByUser(Integer userId) {
        String sql = "SELECT * FROM consultation_log WHERE user_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new Object[] { userId }, new ConsultationRowMapper());
    }
    
    public ConsultationVO getConsultation(int consultationId) {
        String sql = "SELECT * FROM consultation_log WHERE consultation_id = ?";
        return (ConsultationVO) jdbcTemplate.queryForObject(sql, new Object[] { consultationId }, new ConsultationRowMapper());
    }
    
    public void insertConsultation(ConsultationVO consultation) {
        String sql = "INSERT INTO consultation_log (user_id, subject, message, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, new Object[] { consultation.getUserId(), consultation.getSubject(), consultation.getMessage(), consultation.getStatus() });
    }
    
    public void updateConsultation(ConsultationVO consultation) {
        String sql = "UPDATE consultation_log SET subject=?, message=?, reply=?, status=? WHERE consultation_id=?";
        jdbcTemplate.update(sql, new Object[] { consultation.getSubject(), consultation.getMessage(), consultation.getReply(), consultation.getStatus(), consultation.getConsultationId() });
    }
    
    public void deleteConsultation(int consultationId) {
        String sql = "DELETE FROM consultation_log WHERE consultation_id=?";
        jdbcTemplate.update(sql, new Object[] { consultationId });
    }
}
