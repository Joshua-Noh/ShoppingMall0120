package com.shop.ShoppingMall_TeamPrj.customerCenter.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.FaqVO;

@Repository("faqDAO")
public class FaqDAOImpl implements FaqDAO {

    private JdbcTemplate jdbcTemplate;

    // 기본 생성자 추가
    public FaqDAOImpl() {
        // 기본 생성자
    }
    
    // DataSource 주입을 위한 setter
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private static final class FaqRowMapper implements org.springframework.jdbc.core.RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            FaqVO vo = new FaqVO();
            vo.setFaqId(rs.getInt("faq_id"));
            vo.setQuestion(rs.getString("question"));
            vo.setAnswer(rs.getString("answer"));
            vo.setCreatedAt(rs.getTimestamp("created_at"));
            vo.setUpdatedAt(rs.getTimestamp("updated_at"));
            return vo;
        }
    }
    
    public List getAllFaq() {
        String sql = "SELECT * FROM faq ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new FaqRowMapper());
    }
    
    public FaqVO getFaq(int faqId) {
        String sql = "SELECT * FROM faq WHERE faq_id = ?";
        return (FaqVO) jdbcTemplate.queryForObject(sql, new Object[] { faqId }, new FaqRowMapper());
    }
    
    public void insertFaq(FaqVO faq) {
        String sql = "INSERT INTO faq (question, answer) VALUES (?, ?)";
        jdbcTemplate.update(sql, new Object[] { faq.getQuestion(), faq.getAnswer() });
    }
    
    public void updateFaq(FaqVO faq) {
        String sql = "UPDATE faq SET question=?, answer=? WHERE faq_id=?";
        jdbcTemplate.update(sql, new Object[] { faq.getQuestion(), faq.getAnswer(), faq.getFaqId() });
    }
    
    public void deleteFaq(int faqId) {
        String sql = "DELETE FROM faq WHERE faq_id=?";
        jdbcTemplate.update(sql, new Object[] { faqId });
    }
}
