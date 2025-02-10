package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipTransactionVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * MembershipTransactionDAOImpl는 MembershipTransactionDAO 인터페이스의 구현체로,
 * 포인트 거래 내역에 대한 데이터 접근 작업을 수행합니다.
 */
@Repository
public class MembershipTransactionDAOImpl implements MembershipTransactionDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ShoppingMallDB";
    private final String jdbcUsername = "yourUsername";
    private final String jdbcPassword = "yourPassword";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public List<MembershipTransactionVO> getTransactionsByUserId(int userId) {
        List<MembershipTransactionVO> transactions = new ArrayList<MembershipTransactionVO>();
        String sql = "SELECT transaction_id, user_id, points_change, transaction_type, transaction_date, description FROM membership_transactions WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                MembershipTransactionVO transaction = new MembershipTransactionVO();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setPointsChange(rs.getInt("points_change"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
                transaction.setDescription(rs.getString("description"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(ps != null) try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        return transactions;
    }

    @Override
    public int insertTransaction(MembershipTransactionVO transaction) {
        int result = 0;
        String sql = "INSERT INTO membership_transactions (user_id, points_change, transaction_type, transaction_date, description) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, transaction.getUserId());
            ps.setInt(2, transaction.getPointsChange());
            ps.setString(3, transaction.getTransactionType());
            ps.setTimestamp(4, transaction.getTransactionDate());
            ps.setString(5, transaction.getDescription());
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
