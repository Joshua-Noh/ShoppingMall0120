package com.shop.ShoppingMall_TeamPrj.coupons.dao;

import com.shop.ShoppingMall_TeamPrj.coupons.vo.CouponVO;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.UserCouponVO;
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
 * CouponDAOImpl는 CouponDAO 인터페이스의 구현체로, JDBC를 통해 쿠폰 데이터를 처리합니다.
 * (Java 1.6 환경에 맞게 try-with-resources 대신 전통적인 try-catch-finally 블록을 사용합니다.)
 */
@Repository  // Spring 컨테이너가 이 클래스를 DAO 빈으로 인식하게 합니다.
public class CouponDAOImpl implements CouponDAO {

    // 데이터베이스 연결 정보 (실제 운영시 외부 설정 파일로 분리)
    private final String jdbcURL = "jdbc:mysql://localhost:3306/shoppingmalldb";
    private final String jdbcUsername = "yourUsername";
    private final String jdbcPassword = "yourPassword";

    /**
     * 데이터베이스 연결을 생성하여 반환합니다.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public CouponVO getCouponById(int couponId) {
        String sql = "SELECT * FROM coupons WHERE coupon_id = ?";
        CouponVO coupon = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, couponId);
            rs = ps.executeQuery();
            if(rs.next()){
                coupon = extractCouponFromResultSet(rs);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(connection != null) {
                try { connection.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
        }
        return coupon;
    }

    @Override
    public List<CouponVO> getAllCoupons() {
        String sql = "SELECT * FROM coupons";
        List<CouponVO> coupons = new ArrayList<CouponVO>(); // Java 1.6에서는 다이아몬드 연산자 사용 불가
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                CouponVO coupon = extractCouponFromResultSet(rs);
                coupons.add(coupon);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(stmt != null) {
                try { stmt.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(connection != null) {
                try { connection.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
        }
        return coupons;
    }

    @Override
    public int insertCoupon(CouponVO coupon) {
        String sql = "INSERT INTO coupons (coupon_code, description, discount_type, discount_value, min_purchase, valid_from, valid_to, usage_limit, used_count, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, coupon.getCouponCode());
            ps.setString(2, coupon.getDescription());
            ps.setString(3, coupon.getDiscountType());
            ps.setDouble(4, coupon.getDiscountValue());
            ps.setDouble(5, coupon.getMinPurchase());
            ps.setTimestamp(6, coupon.getValidFrom());
            ps.setTimestamp(7, coupon.getValidTo());
            if(coupon.getUsageLimit() != null) {
                ps.setInt(8, coupon.getUsageLimit());
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }
            ps.setInt(9, coupon.getUsedCount());
            ps.setString(10, coupon.getStatus());
            result = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(connection != null) {
                try { connection.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }

    @Override
    public int updateCoupon(CouponVO coupon) {
        String sql = "UPDATE coupons SET coupon_code = ?, description = ?, discount_type = ?, discount_value = ?, min_purchase = ?, valid_from = ?, valid_to = ?, usage_limit = ?, used_count = ?, status = ? WHERE coupon_id = ?";
        int result = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, coupon.getCouponCode());
            ps.setString(2, coupon.getDescription());
            ps.setString(3, coupon.getDiscountType());
            ps.setDouble(4, coupon.getDiscountValue());
            ps.setDouble(5, coupon.getMinPurchase());
            ps.setTimestamp(6, coupon.getValidFrom());
            ps.setTimestamp(7, coupon.getValidTo());
            if(coupon.getUsageLimit() != null) {
                ps.setInt(8, coupon.getUsageLimit());
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }
            ps.setInt(9, coupon.getUsedCount());
            ps.setString(10, coupon.getStatus());
            ps.setInt(11, coupon.getCouponId());
            result = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(connection != null) {
                try { connection.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }

    @Override
    public int deleteCoupon(int couponId) {
        String sql = "DELETE FROM coupons WHERE coupon_id = ?";
        int result = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, couponId);
            result = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(connection != null) {
                try { connection.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }

    @Override
    public List<UserCouponVO> getUserCouponsByUserId(int userId) {
        String sql = "SELECT * FROM user_coupons WHERE user_id = ?";
        List<UserCouponVO> userCoupons = new ArrayList<UserCouponVO>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while(rs.next()){
                UserCouponVO userCoupon = new UserCouponVO();
                userCoupon.setId(rs.getInt("id"));
                userCoupon.setUserId(rs.getInt("user_id"));
                userCoupon.setCouponId(rs.getInt("coupon_id"));
                userCoupon.setStatus(rs.getString("status"));
                userCoupon.setUsedAt(rs.getTimestamp("used_at"));
                userCoupons.add(userCoupon);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(connection != null) {
                try { connection.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
        }
        return userCoupons;
    }

    @Override
    public int insertUserCoupon(UserCouponVO userCoupon) {
        String sql = "INSERT INTO user_coupons (user_id, coupon_id, status, used_at) VALUES (?, ?, ?, ?)";
        int result = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userCoupon.getUserId());
            ps.setInt(2, userCoupon.getCouponId());
            ps.setString(3, userCoupon.getStatus());
            ps.setTimestamp(4, userCoupon.getUsedAt());
            result = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
            if(connection != null) {
                try { connection.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }

    /**
     * ResultSet에서 CouponVO 객체를 추출합니다.
     */
    private CouponVO extractCouponFromResultSet(ResultSet rs) throws SQLException {
        CouponVO coupon = new CouponVO();
        coupon.setCouponId(rs.getInt("coupon_id"));
        coupon.setCouponCode(rs.getString("coupon_code"));
        coupon.setDescription(rs.getString("description"));
        coupon.setDiscountType(rs.getString("discount_type"));
        coupon.setDiscountValue(rs.getDouble("discount_value"));
        coupon.setMinPurchase(rs.getDouble("min_purchase"));
        coupon.setValidFrom(rs.getTimestamp("valid_from"));
        coupon.setValidTo(rs.getTimestamp("valid_to"));
        coupon.setUsageLimit(rs.getObject("usage_limit") != null ? rs.getInt("usage_limit") : null);
        coupon.setUsedCount(rs.getInt("used_count"));
        coupon.setStatus(rs.getString("status"));
        return coupon;
    }
}
