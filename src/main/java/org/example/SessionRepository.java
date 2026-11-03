package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository
{
    public record Session (int id, String name, LocalDate date, int maxParticipants)
    {
    }

    public List<Session> findAll() throws SQLException
    {
        String sql = """
                SELECT id, name, session_date, max_participants
                
                FROM sessions
                ORDER BY session_date;
                """;
        List<Session> sessions = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql))
        {
            while(rs.next())
            {
                sessions.add(new Session(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("session_date").toLocalDate(),
                        rs.getInt("max_participants")));
            }
        }
        return sessions;
    }

    public List<String> findMembersNames(String sessionName) throws SQLException
    {
        String sql = """
                SELECT m.name
                
                FROM bookings b
                JOIN members m ON m.id = b.member_id
                JOIN sessions s ON s.id = b.session_id
                Where s.name = ?
                
                ORDER BY m.name;
                """;
        List<String> names = new ArrayList<>();
        try (Connection conn = Db.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, sessionName);

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    names.add(rs.getString("name"));
                }
            }
        }
        return names;
    }
}
