package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main
{
    public static void main (String[] args)
    {
        try (Connection conn = Db.getConnection())
        {
            System.out.println("Connected to " + conn.getCatalog());
        }

        catch (SQLException e)
        {
            System.out.println("Could not connect " + e.getMessage());
        }

        // Creating Repository objects
        SessionRepository sessionRepo = new SessionRepository();
        MemberRepository memberRepo = new MemberRepository();
        InstructorRepository instructorRepo = new InstructorRepository();

        try
        {
            for (SessionRepository.Session s : sessionRepo.findAll())
            {
                System.out.println(s.name() + " " + s.date() + " " + s.maxParticipants());
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database error: " + e.getMessage());
        }

        try
        {
            for (MemberRepository.Member m : memberRepo.findAll())
            {
                System.out.println(m.id() + " " + m.name() + " " + m.email());
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database error: " + e.getMessage());
        }

        try
        {
            for (InstructorRepository.Instructor i : instructorRepo.findAll())
            {
                System.out.println(i.name() + " " + i.sessionCount());
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database error: " + e.getMessage());
        }

        try
        {
            System.out.println("Spinning " + sessionRepo.findMembersNames("Spinning"));
            System.out.println("Spinning " + sessionRepo.findMembersNames("'OR' '1=1'"));
        }
        catch (SQLException e)
        {
            System.out.println("Database error: " + e.getMessage());
        }

        try
        {
            MemberRepository.Member m = memberRepo.findByEmail("omar@example.com");
            if (m != null)
            System.out.println("Member id: " + m.id() + "\n" + m.name() + "\n" + m.email());
            else
            {
                System.out.println("No member matching email");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database error " + e.getMessage());
        }

        try
        {
            MemberRepository.Member m = memberRepo.findByEmail("x@example.com");
            if (m != null)
                System.out.println("Member id: " + m.id() + "\n" + m.name() + "\n" + m.email());
            else
            {
                System.out.println("No member matching email");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database error " + e.getMessage());
        }

        try
        {
            for (SessionRepository.InstructorSession s : instructorRepo.findByInstructor("Maja Ek"))
            {
                System.out.println(s.name() + " " + s.date());
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database error " + e.getMessage());
        }
    }
}
