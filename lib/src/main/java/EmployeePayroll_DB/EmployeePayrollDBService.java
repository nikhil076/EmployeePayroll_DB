package EmployeePayroll_DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EmployeePayrollDBService {
	public static List<EmpPayrollData> empPayrollDataList = new ArrayList<>();
	private PreparedStatement employeePayrollDataStatement;

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

	public List<EmpPayrollData> readData() {
		long count = 0;
		String sql = "SELECT * FROM employee_payroll;";
		try (Connection connection = this.getConnection();) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate start = resultSet.getDate("start").toLocalDate();
				empPayrollDataList.add(new EmpPayrollData(id, name, salary, start));
				count = empPayrollDataList.stream().count();

			}
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
		return empPayrollDataList;
	}

	public List<EmpPayrollData> getEmpPayrollData(String name) throws SQLException {
		List<EmpPayrollData> empPayrollDataList = new ArrayList<>();
		String sql = String.format("SELECT * FROM employee_payroll WHERE name='%s';", name);
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String empName = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate start = resultSet.getDate("start").toLocalDate();
				empPayrollDataList.add(new EmpPayrollData(id, empName, salary, start));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empPayrollDataList;
	}

	private void prepareStatementEmployeeData() throws SQLException {
		Connection connection = this.getConnection();
		String sql = "SELECT * from employee_payroll WHERE name=?";
		employeePayrollDataStatement = connection.prepareStatement(sql);
	}

	public int updateEmpData(String name, double salary) {
		return this.updateDataUsingStatement(name, salary);
	}

	private int updateDataUsingStatement(String name, Double salary) {
		String sql = String.format("UPDATE employee_payroll SET salary = %.2f WHERE name = '%s';", salary, name);
		Connection connection;
		try {
			connection = this.getConnection();
			Statement statement = connection.prepareStatement(sql);
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int retriveDataBetweenGivenDate(String startDate, String endDate) {
		int entriesCount = 0;
		Connection connection;
		try {
			connection = this.getConnection();
			employeePayrollDataStatement = connection.prepareStatement(
					"select * from employee_payroll where start between cast(? as date) and cast(? as date)");
			employeePayrollDataStatement.setString(1, startDate);
			employeePayrollDataStatement.setString(2, endDate);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String empName = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate start = resultSet.getDate("start").toLocalDate();
				empPayrollDataList.add(new EmpPayrollData(id, empName, salary, start));
			}
			entriesCount = empPayrollDataList.size();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entriesCount;
	}
}
