package com.shop.ShoppingMall_TeamPrj.customerCenter.dao;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.FaqVO;

@Repository("faqDAO")
public class FaqDAOImpl implements FaqDAO {

    private JdbcTemplate jdbcTemplate;

    // 기본 생성자
    public FaqDAOImpl() {
        // 기본 생성자: 빈 생성 시 별도의 초기화 작업 없음.
    }
    
    // DataSource 주입을 위한 setter (Spring이 자동 주입)
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    // 제네릭을 사용한 RowMapper 구현 (FAQ 정보를 FaqVO 객체에 매핑)
    private static final class FaqRowMapper implements org.springframework.jdbc.core.RowMapper<FaqVO> {
        @Override
        public FaqVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            FaqVO vo = new FaqVO();
            vo.setFaqId(rs.getInt("faq_id"));
            vo.setQuestion(rs.getString("question"));
            vo.setAnswer(rs.getString("answer"));
            vo.setCreatedAt(rs.getTimestamp("created_at"));
            vo.setUpdatedAt(rs.getTimestamp("updated_at"));
            return vo;
        }
    }
    
    // FAQ 전체 목록 조회
    @Override
    public List<FaqVO> getAllFaq() {
        String sql = "SELECT * FROM faq ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new FaqRowMapper());
    }
    
    // FAQ 단건 조회
    @Override
    public FaqVO getFaq(int faqId) {
        String sql = "SELECT * FROM faq WHERE faq_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { faqId }, new FaqRowMapper());
    }
    
    // FAQ 등록
    @Override
    public void insertFaq(FaqVO faq) {
        String sql = "INSERT INTO faq (question, answer) VALUES (?, ?)";
        jdbcTemplate.update(sql, faq.getQuestion(), faq.getAnswer());
    }
    
    // FAQ 수정
    @Override
    public void updateFaq(FaqVO faq) {
        String sql = "UPDATE faq SET question = ?, answer = ? WHERE faq_id = ?";
        jdbcTemplate.update(sql, faq.getQuestion(), faq.getAnswer(), faq.getFaqId());
    }
    
    // FAQ 삭제
    @Override
    public void deleteFaq(int faqId) {
        String sql = "DELETE FROM faq WHERE faq_id = ?";
        jdbcTemplate.update(sql, faqId);
    }
}
