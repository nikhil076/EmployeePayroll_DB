package EmployeePayroll_DB;

import java.sql.SQLException;
import java.util.List;

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
	public void givenNewSalaryForEmployee_ShouldMatchWhenUpdated() throws SQLException
	{
		EmployeePayrollService  employeePayrollService = new EmployeePayrollService();
		List<EmpPayrollData> empPayrollDataList = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmpSalary("Mark", 6000000);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
		Assert.assertTrue(result);
	}
	
	@Test
	public void givenDateBetweeen_shouldReturnNumberOfvalues()
	{
		EmployeePayrollDBService dbService = new EmployeePayrollDBService();
		int result = dbService.retriveDataBetweenGivenDate("2018-01-01", "2020-01-01");
		Assert.assertEquals(1, result);
	}
}
