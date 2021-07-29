package EmployeePayroll_DB;

import java.sql.Driver;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.sql.Connection;

public class EmpPayrollDB {
	public static List<EmpPayrollData> empPayrollDataList = new ArrayList<>();

	public static void main(String args[]) {
		String jdbcUrl = "jdbc:mysql://localhost:3306/payroll_services?characterEncoding=utf8";
		String userName = "root";
		String password = "1640";
		Connection connection;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded..");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		listDrivers();
		try {
			System.out.println("Connecting to database :" + jdbcUrl);
			connection = DriverManager.getConnection(jdbcUrl, userName, password);
			System.out.println("Connection is successful!" + connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("Driver:" + driverClass.getClass().getName());
        }
	}
}
