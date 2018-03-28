package com.epam.brest.course.service;

import com.epam.brest.course.dao.EmployeeDao;
import com.epam.brest.course.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * Employee Service.
 */
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * Constant variable for logs.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * DepartmentDao.
     */
    private EmployeeDao employeeDao;

    /**
     * Constructor.
     *
     * @param employeeDao new employeeDao.
     */
    public EmployeeServiceImpl(final EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Collection<Employee> getEmployees() {
        LOGGER.debug("getEmployees()");
        return employeeDao.getEmployees();
    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        LOGGER.debug("getEmployeeById({})", employeeId);
        return employeeDao.getEmployeeById(employeeId);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        LOGGER.debug("addEmployee({})", employee);
        return employeeDao.addEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        LOGGER.debug("updateEmployee({})", employee);
        employeeDao.updateEmployee(employee);
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        LOGGER.debug("deleteEmployeeById({})", id);
        employeeDao.deleteEmployeeById(id);
    }
}
