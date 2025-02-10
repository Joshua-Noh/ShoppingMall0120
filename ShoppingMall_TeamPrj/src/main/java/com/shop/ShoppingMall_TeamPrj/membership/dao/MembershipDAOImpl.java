package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 * MembershipDAOImpl는 MembershipDAO 인터페이스의 구현체로,
 * 사용자 멤버십 정보를 관리하는 CRUD 작업을 수행합니다.
 *
 * 가정: 사용자 멤버십 정보는 'membership' 테이블에 저장되며, 컬럼은
 *       user_id, membership_level, membership_points 를 포함합니다.
 */
@Repository
public class MembershipDAOImpl implements MembershipDAO {

    // 데이터베이스 연결 정보 (실제 운영시 외부 설정 파일로 분리 권장)
    private final String jdbcURL = "jdbc:mysql://localhost:3306/ShoppingMallDB";
    private final String jdbcUsername = "yourUsername";
    private final String jdbcPassword = "yourPassword";

    /**
     * 데이터베이스 연결을 생성하여 반환합니다.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public MembershipVO getMembershipByUserId(int userId) {
        MembershipVO membership = null;
        String sql = "SELECT user_id, membership_level, membership_points FROM membership WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                membership = new MembershipVO();
                membership.setUserId(rs.getInt("user_id"));
                membership.setMembershipLevel(rs.getString("membership_level"));
                membership.setMembershipPoints(rs.getInt("membership_points"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return membership;
    }

    @Override
    public int insertMembership(MembershipVO membership) {
        int result = 0;
        String sql = "INSERT INTO membership (user_id, membership_level, membership_points) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, membership.getUserId());
            ps.setString(2, membership.getMembershipLevel());
            ps.setInt(3, membership.getMembershipPoints());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }

    @Override
    public int updateMembership(MembershipVO membership) {
        int result = 0;
        String sql = "UPDATE membership SET membership_level = ?, membership_points = ? WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, membership.getMembershipLevel());
            ps.setInt(2, membership.getMembershipPoints());
            ps.setInt(3, membership.getUserId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }
}
