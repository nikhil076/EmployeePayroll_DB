package EmployeePayroll_DB;

public class ExceptionDB {

	public enum ExceptionType {
		SQL_ERROR, UPDATE_FAIL, CONNECTION_FAILURE, INVALID_DATA
	}

	ExceptionType exceptionType;

	public ExceptionDB(String message, ExceptionType exceptionType) {
		super();
		this.exceptionType = exceptionType;
	}
}
