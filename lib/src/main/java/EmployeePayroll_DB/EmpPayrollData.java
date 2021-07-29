package EmployeePayroll_DB;

import java.time.LocalDate;
import java.util.Arrays;

public class EmpPayrollData {

	public int id;
    public String name;
    public double salary;
    public LocalDate date;
    public Character gender;
    public String companyId;
    public String department[];
    private boolean isActive = true;

    public EmpPayrollData(Integer id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    
    public EmpPayrollData(int id, String name, double salary, LocalDate date, Character gender, String companyId,
			String[] department, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.date = date;
		this.gender = gender;
		this.companyId = companyId;
		this.department = department;
		this.isActive = isActive;
	}


	public EmpPayrollData(Integer id, String name, Double salary,LocalDate date) {
        this(id,name,salary);
        this.date = date;
    }


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public Character getGender() {
		return gender;
	}


	public void setGender(Character gender) {
		this.gender = gender;
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String[] getDepartment() {
		return department;
	}


	public void setDepartment(String[] department) {
		this.department = department;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	@Override
	public String toString() {
		return "EmpPayrollData [id=" + id + ", name=" + name + ", salary=" + salary + ", date=" + date + ", gender="
				+ gender + ", companyId=" + companyId + ", department=" + Arrays.toString(department) + ", isActive="
				+ isActive + "]";
	}
    
}
