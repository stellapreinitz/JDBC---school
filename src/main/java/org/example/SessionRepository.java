package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
