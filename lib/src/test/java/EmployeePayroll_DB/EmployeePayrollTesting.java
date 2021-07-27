package EmployeePayroll_DB;

import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollTesting {

	@Test
    public void given3EmployeesWhenReadFromFileShouldMatchNumberOfEmployeeEntries() {
		EmployeePayrollDBService dbService = new EmployeePayrollDBService();
		long result = dbService.readData();
		Assert.assertEquals(4, result);
	}
}
