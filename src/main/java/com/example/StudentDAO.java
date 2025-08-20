package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static final String URL  = "jdbc:mysql://localhost:3306/me";
    private static final String USER = "root";
    private static final String PASS = "3773";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found", e);
        }
    }

    public void addStudent(int id,String name, int age) {
        String sql = "INSERT INTO students(emp_id,ename, age) VALUES(?,?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1,id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting student", e);
        }
    }

    public List<Student> listStudents() {
        String sql = "SELECT emp_id, ename, age FROM students ORDER BY emp_id DESC";
        List<Student> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("emp_id"),
                    rs.getString("ename"),
                    rs.getInt("age")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching students", e);
        }
        return list;
    }
}
