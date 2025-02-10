package com.shop.ShoppingMall_TeamPrj.events.dao;

import com.shop.ShoppingMall_TeamPrj.events.vo.EventVO;
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
 * EventDAOImpl는 EventDAO 인터페이스의 구현체로, JDBC를 통해 데이터베이스와 상호작용합니다.
 * (Java 1.6 환경에 맞게 try-with-resources 대신 전통적인 try-catch-finally 블록을 사용합니다.)
 */
@Repository  // Spring이 이 클래스를 데이터 접근용 빈(DAO)으로 관리하도록 합니다.
public class EventDAOImpl implements EventDAO {

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
    public EventVO getEventById(int eventId) {
        String sql = "SELECT * FROM events WHERE event_id = ?";
        EventVO event = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, eventId);
            rs = ps.executeQuery();
            if (rs.next()) {
                event = extractEventFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅 프레임워크 사용 권장
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return event;
    }

    @Override
    public List<EventVO> getAllEvents() {
        String sql = "SELECT * FROM events";
        List<EventVO> events = new ArrayList<EventVO>();  // Java 1.6에서는 다이아몬드 연산자 사용 불가
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                EventVO event = extractEventFromResultSet(rs);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return events;
    }

    @Override
    public int insertEvent(EventVO event) {
        String sql = "INSERT INTO events (event_name, event_description, event_type, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        int result = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getEventDescription());
            ps.setString(3, event.getEventType());
            ps.setTimestamp(4, event.getStartDate());
            ps.setTimestamp(5, event.getEndDate());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }

    @Override
    public int updateEvent(EventVO event) {
        String sql = "UPDATE events SET event_name = ?, event_description = ?, event_type = ?, start_date = ?, end_date = ? WHERE event_id = ?";
        int result = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getEventDescription());
            ps.setString(3, event.getEventType());
            ps.setTimestamp(4, event.getStartDate());
            ps.setTimestamp(5, event.getEndDate());
            ps.setInt(6, event.getEventId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }

    @Override
    public int deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE event_id = ?";
        int result = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, eventId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }

    /**
     * ResultSet에서 이벤트 데이터를 추출하여 EventVO 객체를 생성합니다.
     */
    private EventVO extractEventFromResultSet(ResultSet rs) throws SQLException {
        EventVO event = new EventVO();
        event.setEventId(rs.getInt("event_id"));
        event.setEventName(rs.getString("event_name"));
        event.setEventDescription(rs.getString("event_description"));
        event.setEventType(rs.getString("event_type"));
        event.setStartDate(rs.getTimestamp("start_date"));
        event.setEndDate(rs.getTimestamp("end_date"));
        event.setCreatedAt(rs.getTimestamp("created_at"));
        return event;
    }
}
