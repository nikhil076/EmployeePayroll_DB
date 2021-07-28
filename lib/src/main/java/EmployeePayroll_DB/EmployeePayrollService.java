package EmployeePayroll_DB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollService {

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<EmpPayrollData> empPayrollDataList;

	public EmployeePayrollService() {
		this.empPayrollDataList = new ArrayList<EmpPayrollData>();
	}

	public EmployeePayrollService(List<EmpPayrollData> empPayrollDataList) {
		this.empPayrollDataList = empPayrollDataList;
	}

	public int sizeOfEmpList() {
		return this.empPayrollDataList.size();
	}

	public List<EmpPayrollData> readEmployeePayrollData(IOService ioService) {
		if (ioService.equals(IOService.DB_IO))
			this.empPayrollDataList = new EmployeePayrollDBService().readData();
		return this.empPayrollDataList;
	}

	public void updateEmpSalary(String name, double salary) {
		int result = new EmployeePayrollDBService().updateEmpData(name, salary);
		if (result == 0) {
			try {
				throw new SQLException("Query is falied");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		EmpPayrollData empPayrollData = this.getEmpPayrollData(name);
		if(empPayrollDataList!=null)
			empPayrollData.salary=salary;
	}
	
	private EmpPayrollData getEmpPayrollData(String name) {
        return this.empPayrollDataList.stream().filter(empPayrollDataItem -> empPayrollDataItem.name.equals(name))
                .findFirst().orElse(null);
    }

	public boolean checkEmployeePayrollInSyncWithDB(String name) throws SQLException {
        List<EmpPayrollData> empPayrollDataList = new EmployeePayrollDBService().getEmpPayrollData(name);
        return empPayrollDataList.get(0).equals(getEmpPayrollData(name));
    }

}
