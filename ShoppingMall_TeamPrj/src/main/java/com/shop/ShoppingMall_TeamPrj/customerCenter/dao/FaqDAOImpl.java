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

    // �⺻ ������
    public FaqDAOImpl() {
        // �⺻ ������: �� ���� �� ������ �ʱ�ȭ �۾� ����.
    }
    
    // DataSource ������ ���� setter (Spring�� �ڵ� ����)
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    // ���׸��� ����� RowMapper ���� (FAQ ������ FaqVO ��ü�� ����)
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
    
    // FAQ ��ü ��� ��ȸ
    @Override
    public List<FaqVO> getAllFaq() {
        String sql = "SELECT * FROM faq ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new FaqRowMapper());
    }
    
    // FAQ �ܰ� ��ȸ
    @Override
    public FaqVO getFaq(int faqId) {
        String sql = "SELECT * FROM faq WHERE faq_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { faqId }, new FaqRowMapper());
    }
    
    // FAQ ���
    @Override
    public void insertFaq(FaqVO faq) {
        String sql = "INSERT INTO faq (question, answer) VALUES (?, ?)";
        jdbcTemplate.update(sql, faq.getQuestion(), faq.getAnswer());
    }
    
    // FAQ ����
    @Override
    public void updateFaq(FaqVO faq) {
        String sql = "UPDATE faq SET question = ?, answer = ? WHERE faq_id = ?";
        jdbcTemplate.update(sql, faq.getQuestion(), faq.getAnswer(), faq.getFaqId());
    }
    
    // FAQ ����
    @Override
    public void deleteFaq(int faqId) {
        String sql = "DELETE FROM faq WHERE faq_id = ?";
        jdbcTemplate.update(sql, faqId);
    }
}
