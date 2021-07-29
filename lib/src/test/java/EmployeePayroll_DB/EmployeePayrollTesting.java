package EmployeePayroll_DB;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import EmployeePayroll_DB.EmployeePayrollService.IOService;

public class EmployeePayrollTesting {

	@Test
	public void given3EmployeesWhenReadFromFileShouldMatchNumberOfEmployeeEntries() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		int result = employeePayrollService.sizeOfEmpList();
		Assert.assertEquals(4, result);
	}

	@Test
	public void givenNewSalaryForEmployee_ShouldMatchWhenUpdated() throws SQLException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmpPayrollData> empPayrollDataList = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmpSalary("Mark", 6000000);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
		Assert.assertTrue(result);
	}

	@Test
	public void givenDateBetweeen_shouldReturnNumberOfvalues() {
		EmployeePayrollDBService dbService = new EmployeePayrollDBService();
		int result = dbService.retriveDataBetweenGivenDate("2018-01-01", "2020-01-01");
		Assert.assertEquals(1, result);
	}

	@Test
	public void givenGender_ShouldReturnAverageOfSalary() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		EmployeePayrollDBService dbService = new EmployeePayrollDBService();
		Map<String, Double> averageSalaryByGender = dbService.retiveAverageOfSalaryGroupByGender();
		Assert.assertTrue(
				averageSalaryByGender.get("M").equals(5000000.00) && averageSalaryByGender.get("F").equals(1000000.00));
	}

	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() throws SQLException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		EmployeePayrollDBService dbService = new EmployeePayrollDBService();
		dbService.addEmployeeToPayroll("John", 4000000.00, LocalDate.now(), "M");
		Boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("John");
		Assert.assertTrue(result);
	}
}
