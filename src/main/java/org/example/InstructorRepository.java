package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InstructorRepository
{
    public record Instructor (String name, int sessionCount)
    {
    }

    public List<InstructorRepository.Instructor> findAll() throws SQLException
    {
        String sql = """
                SELECT i.name, count(s.id) AS session_count
                
                FROM instructors i
                LEFT JOIN sessions s ON i.id = s.instructor_id
                GROUP BY i.name
                ORDER BY name ASC;
                """;
        List<InstructorRepository.Instructor> instructors = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql))
        {
            while (rs.next())
            {
                instructors.add(new InstructorRepository.Instructor(
                        rs.getString("name"),
                        rs.getInt("session_count")));
            }
        }
        return instructors;
    }
}
