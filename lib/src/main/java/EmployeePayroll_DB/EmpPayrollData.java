package EmployeePayroll_DB;

import java.time.LocalDate;

public class EmpPayrollData {

	public int id;
    public String name;
    public double salary;
    public LocalDate date;

    public EmpPayrollData(Integer id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public EmpPayrollData(Integer id, String name, Double salary,LocalDate date) {
        this(id,name,salary);
        this.date = date;
    }

    @Override
    public String toString() {
        return "EmpPayrollData{" +
                "employeeId=" + id + ", employeeName=" + name + ", employeeSalary" + salary + ", startDate=" + date + "}";
    }
}
