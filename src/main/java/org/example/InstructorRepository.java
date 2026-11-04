package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorRepository
{
    public record Instructor (String name, int sessionCount)
    {
    }

    public List<Instructor> findAll() throws SQLException
    {
        String sql = """
                SELECT i.name, count(s.id) AS session_count
                
                FROM instructors i
                LEFT JOIN sessions s ON i.id = s.instructor_id
                GROUP BY i.name
                ORDER BY name ASC;
                """;
        List<Instructor> instructors = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql))
        {
            while (rs.next())
            {
                instructors.add(new Instructor(
                        rs.getString("name"),
                        rs.getInt("session_count")));
            }
        }
        return instructors;
    }

    public List<SessionRepository.InstructorSession> findByInstructor(String instructorName) throws SQLException
    {
        String sql = """
                SELECT s.name, s.session_date
                FROM sessions s
                JOIN instructors i ON s.instructor_id = i.id
                WHERE i.name = ?
                ORDER BY s.session_date;
                """;
        List<SessionRepository.InstructorSession> sessions = new ArrayList<>();
        try (Connection conn = Db.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, instructorName);

            try (ResultSet rs = ps.executeQuery())
            {
                while(rs.next())
                {
                    sessions.add(new SessionRepository.InstructorSession(
                            rs.getString("name"),
                            rs.getDate("session_date").toLocalDate()));
                }
            }
        }
        return sessions;
    }
}
