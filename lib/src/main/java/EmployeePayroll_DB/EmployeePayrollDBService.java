package EmployeePayroll_DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

public class EmployeePayrollDBService {
	public static List<EmpPayrollData> empPayrollDataList = new ArrayList<>();

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_services?characterEncoding=utf8";
		String userName = "root";
		String password = "1640";
		Connection connection;
		System.out.println("Database Connecting :" + jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Successfully Connected..!!" + connection);
		return connection;
	}

	public long readData() {
		long count = 0 ;
        String sql = "SELECT * FROM employee_payroll;";
        try(Connection connection = this.getConnection();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate start = resultSet.getDate("start").toLocalDate();
                empPayrollDataList.add(new EmpPayrollData(id, name,salary,start));
                count = empPayrollDataList.stream().count();
                
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return count;
    }
}
