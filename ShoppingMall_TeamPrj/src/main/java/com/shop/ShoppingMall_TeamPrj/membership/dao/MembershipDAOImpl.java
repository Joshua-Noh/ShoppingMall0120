package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 * MembershipDAOImpl�� MembershipDAO �������̽��� ����ü��,
 * ����� ����� ������ �����ϴ� CRUD �۾��� �����մϴ�.
 *
 * ����: ����� ����� ������ 'membership' ���̺� ����Ǹ�, �÷���
 *       user_id, membership_level, membership_points �� �����մϴ�.
 */
@Repository
public class MembershipDAOImpl implements MembershipDAO {

    // �����ͺ��̽� ���� ���� (���� ��� �ܺ� ���� ���Ϸ� �и� ����)
    private final String jdbcURL = "jdbc:mysql://localhost:3306/ShoppingMallDB";
    private final String jdbcUsername = "yourUsername";
    private final String jdbcPassword = "yourPassword";

    /**
     * �����ͺ��̽� ������ �����Ͽ� ��ȯ�մϴ�.
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
