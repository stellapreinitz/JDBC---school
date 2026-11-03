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
    }
}
