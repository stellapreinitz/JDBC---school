package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main
{
    public static void main (String[] args)
    {
        try (Connection conn = Db.getConnection())
        {
            System.out.println("Ansluten till " + conn.getCatalog());
        }

        catch (SQLException e)
        {
            System.out.println("Kunde inte ansluta " + e.getMessage());
        }

        SessionRepository sessionRepo = new SessionRepository();
        try
        {
            for (SessionRepository.Session s : sessionRepo.findAll())
            {
                System.out.println(s.name() + " " + s.date() + " " + s.maxParticipants());
            }
        }
        catch (SQLException e)
        {
            System.out.println("Databasfel: " + e.getMessage());
        }
    }
}
