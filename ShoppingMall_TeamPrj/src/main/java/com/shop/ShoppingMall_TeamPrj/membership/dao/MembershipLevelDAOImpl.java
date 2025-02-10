package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipLevelVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * MembershipLevelDAOImpl는 MembershipLevelDAO 인터페이스의 구현체로,
 * 멤버십 등급 정보를 관리하는 CRUD 작업을 수행합니다.
 */
@Repository
public class MembershipLevelDAOImpl implements MembershipLevelDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ShoppingMallDB";
    private final String jdbcUsername = "yourUsername";
    private final String jdbcPassword = "yourPassword";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public MembershipLevelVO getMembershipLevel(String membershipLevel) {
        MembershipLevelVO level = null;
        String sql = "SELECT membership_level, discount_rate, required_points FROM membership_levels WHERE membership_level = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, membershipLevel);
            rs = ps.executeQuery();
            if (rs.next()) {
                level = new MembershipLevelVO();
                level.setMembershipLevel(rs.getString("membership_level"));
                level.setDiscountRate(rs.getDouble("discount_rate"));
                level.setRequiredPoints(rs.getInt("required_points"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(ps != null) try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        return level;
    }

    @Override
    public List<MembershipLevelVO> getAllMembershipLevels() {
        List<MembershipLevelVO> levels = new ArrayList<MembershipLevelVO>();
        String sql = "SELECT membership_level, discount_rate, required_points FROM membership_levels";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MembershipLevelVO level = new MembershipLevelVO();
                level.setMembershipLevel(rs.getString("membership_level"));
                level.setDiscountRate(rs.getDouble("discount_rate"));
                level.setRequiredPoints(rs.getInt("required_points"));
                levels.add(level);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(stmt != null) try { stmt.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        return levels;
    }

    @Override
    public int insertMembershipLevel(MembershipLevelVO level) {
        int result = 0;
        String sql = "INSERT INTO membership_levels (membership_level, discount_rate, required_points) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, level.getMembershipLevel());
            ps.setDouble(2, level.getDiscountRate());
            ps.setInt(3, level.getRequiredPoints());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(ps != null) try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        return result;
    }

    @Override
    public int updateMembershipLevel(MembershipLevelVO level) {
        int result = 0;
        String sql = "UPDATE membership_levels SET discount_rate = ?, required_points = ? WHERE membership_level = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, level.getDiscountRate());
            ps.setInt(2, level.getRequiredPoints());
            ps.setString(3, level.getMembershipLevel());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(ps != null) try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        return result;
    }

    @Override
    public int deleteMembershipLevel(String membershipLevel) {
        int result = 0;
        String sql = "DELETE FROM membership_levels WHERE membership_level = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, membershipLevel);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(ps != null) try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        return result;
    }
}
