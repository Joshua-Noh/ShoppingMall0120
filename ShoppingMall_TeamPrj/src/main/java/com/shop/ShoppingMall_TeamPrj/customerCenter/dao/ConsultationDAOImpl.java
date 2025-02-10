package com.shop.ShoppingMall_TeamPrj.customerCenter.dao;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ConsultationVO;

@Repository("consultationDAO")
public class ConsultationDAOImpl implements ConsultationDAO {

    private JdbcTemplate jdbcTemplate;
    
    // DataSource는 setter를 통해 주입됨.
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        System.out.println("ConsultationDAOImpl: setDataSource 호출, DataSource = " + dataSource);
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final class ConsultationRowMapper implements org.springframework.jdbc.core.RowMapper<ConsultationVO> {
        public ConsultationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConsultationVO vo = new ConsultationVO();
            vo.setConsultationId(rs.getInt("consultation_id"));
            vo.setUserId(rs.getInt("user_id"));
            vo.setSubject(rs.getString("subject"));
            vo.setMessage(rs.getString("message"));
            vo.setReply(rs.getString("reply"));
            vo.setStatus(rs.getString("status"));
            vo.setCreatedAt(rs.getTimestamp("created_at"));
            vo.setUpdatedAt(rs.getTimestamp("updated_at"));
            vo.setUserName(rs.getString("user_name"));
            return vo;
        }
    }

    public List<ConsultationVO> getAllConsultations() {
        String sql = "SELECT cl.consultation_id, cl.user_id, cl.subject, cl.message, cl.reply, cl.status, cl.created_at, cl.updated_at, u.user_name " +
                     "FROM consultation_log cl " +
                     "JOIN users u ON cl.user_id = u.user_id " +
                     "ORDER BY cl.created_at DESC";
        return jdbcTemplate.query(sql, new ConsultationRowMapper());
    }

    public List<ConsultationVO> getConsultationsByUser(Integer userId) {
        String sql = "SELECT cl.consultation_id, cl.user_id, cl.subject, cl.message, cl.reply, cl.status, cl.created_at, cl.updated_at, u.user_name " +
                     "FROM consultation_log cl " +
                     "JOIN users u ON cl.user_id = u.user_id " +
                     "WHERE cl.user_id = ? " +
                     "ORDER BY cl.created_at DESC";
        return jdbcTemplate.query(sql, new Object[] { userId }, new ConsultationRowMapper());
    }

    public ConsultationVO getConsultation(int consultationId) {
        // 수정: 조인 쿼리를 사용하여 user_name을 포함하도록 변경
        String sql = "SELECT cl.consultation_id, cl.user_id, cl.subject, cl.message, cl.reply, cl.status, cl.created_at, cl.updated_at, u.user_name " +
                     "FROM consultation_log cl " +
                     "JOIN users u ON cl.user_id = u.user_id " +
                     "WHERE cl.consultation_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { consultationId }, new ConsultationRowMapper());
    }

    @Transactional
    public void insertConsultation(ConsultationVO consultation) {
        String sql = "INSERT INTO consultation_log (user_id, subject, message) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, consultation.getUserId(), consultation.getSubject(), consultation.getMessage());
        System.out.println("[DEBUG] insertConsultation - 삽입된 행의 수: " + rowsAffected);
        if (rowsAffected > 0) {
            System.out.println("[DEBUG] insertConsultation - 상담 내역이 성공적으로 삽입되었습니다.");
        } else {
            System.out.println("[ERROR] insertConsultation - 상담 내역 삽입 실패 (삽입된 행 수가 0입니다).");
        }
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
