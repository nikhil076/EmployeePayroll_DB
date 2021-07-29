package EmployeePayroll_DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
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

	public Map<String, Double> retiveAverageOfSalaryGroupByGender() {
		String sql = "SELECT gender,avg(salary) as avg_salary from employee_payroll GROUP BY gender";
		Map<String, Double> genderToAverageSalaryMap = new HashMap<String, Double>();
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String gender = resultSet.getString("gender");
				double salary = resultSet.getDouble("avg_salary");
				genderToAverageSalaryMap.put(gender, salary);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genderToAverageSalaryMap;
	}

	public EmpPayrollData addEmployeeToPayroll(String name, double salary, LocalDate startDate, String gender) {
		int employeeId = -1;
		Connection connection = null;
		EmpPayrollData empPayrollData = null;
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement statement = connection.createStatement();
			String sql = String.format(
					"INSERT into employee_payroll (name,gender,salary,start) VALUES" + "('%s','%s','%s','%s')", name,
					gender, salary, Date.valueOf(startDate));
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					employeeId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			Statement statement = connection.createStatement();
			double deductions = salary * 0.2;
			double taxablePay = salary - deductions;
			double tax = taxablePay * 0.1;
			double netPay = salary - tax;

			String sql = String.format("INSERT INTO payroll_details"
					+ "(employee_id , basc_pay,deducations,taxable_pay,tax,net_pay) VALUES" + "('%s','%s','%s','%s')",
					employeeId, salary, deductions, taxablePay, tax, netPay);
			int rowAffected = statement.executeUpdate(sql);
			if (rowAffected == 1) {
				empPayrollData = new EmpPayrollData(employeeId, name, salary, startDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empPayrollData;
	}
}
