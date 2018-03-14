package com.epam.brest.course.dao;

import com.epam.brest.course.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * CRUD for Department.
 */
public class DepartmentDaoImpl implements DepartmentDao {

    /**
     * Constant variable for logs.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constant variable.
     */
    public static final String DEPARTMENT_ID = "departmentId";
    /**
     * Constant variable.
     */
    public static final String DEPARTMENT_NAME = "departmentName";
    /**
     * Constant variable.
     */
    public static final String DESCRIPTION = "description";

    /**
     * Query for select.
     */
    @Value("${department.select}")
    private String select;
    /**
     * Query for select by id.
     */
    @Value("${department.selectById}")
    private String selectByID;
    /**
     * Query for count of number.
     */
    @Value("${department.checkDepartment}")
    private String checkDepartment;
    /**
     * Query for inserting.
     */
    @Value("${department.insert}")
    private String insert;
    /**
     * Query for updating.
     */
    @Value("${department.update}")
    private String update;
    /**
     * Query for deleting.
     */
    @Value("${department.delete}")
    private String delete;

    /**
     * Class NamedParameterJdbcTemplate from spring JDBC.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor.
     *
     * @param namedParameterJdbcTemplate new namedParameterJdbcTemplate.
     */
    public DepartmentDaoImpl(
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Collection<Department> getDepartments() {
        LOGGER.debug("getDepartments()");
        List<Department> departments =
                namedParameterJdbcTemplate.getJdbcOperations().query(
                        select, new DepartmentRowMapper());
        return departments;

    }

    @Override
    public Department getDepartmentById(final Integer departmentId) {
        LOGGER.debug("getDepartmentById({})", departmentId);
        SqlParameterSource namedParametrs =
                new MapSqlParameterSource(DEPARTMENT_ID, departmentId);
        Department department = namedParameterJdbcTemplate.queryForObject(
                selectByID, namedParametrs,
                BeanPropertyRowMapper.newInstance(Department.class));

        return department;
    }

    @Override
    public Department addDepartment(final Department department) {
        LOGGER.debug("addDepartment({})", department);
        MapSqlParameterSource namedParametrs = new MapSqlParameterSource(
                DEPARTMENT_NAME, department.getDepartmentName());

        Integer result = namedParameterJdbcTemplate.queryForObject(
                checkDepartment, namedParametrs, Integer.class);

        LOGGER.debug("result({})", result);
        if (result == 0) {
            namedParametrs = new MapSqlParameterSource();
            namedParametrs.addValue(
                    DEPARTMENT_NAME, department.getDepartmentName());
            namedParametrs.addValue(DESCRIPTION, department.getDescription());

            KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

            namedParameterJdbcTemplate.update(
                    insert, namedParametrs, generatedKeyHolder);
            department.setDepartmentId(generatedKeyHolder.getKey().intValue());
        } else {
            throw new IllegalArgumentException(
                    "Department with the same name already exists");
        }

        return department;
    }

    @Override
    public void updateDepartment(final Department department) {
        LOGGER.debug("updateDepartment({})", department);
        SqlParameterSource namedParameter =
                new BeanPropertySqlParameterSource(department);
        namedParameterJdbcTemplate.update(update, namedParameter);
    }

    @Override
    public void deleteDepartmentById(final Integer departmentId) {
        LOGGER.debug("deleteDepartmentById({})", departmentId);
        namedParameterJdbcTemplate.getJdbcOperations().update(
                delete, departmentId);
    }

    /**
     * Class DepartmentRowMapper is implementation
     * of interface RowMapper<Department>.
     */
    private static class DepartmentRowMapper implements RowMapper<Department> {

        @Override
        public Department mapRow(final ResultSet resultSet, final int i)
                throws SQLException {
            LOGGER.debug("mapRow({}, {})", resultSet, i);
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt(DEPARTMENT_ID));
            department.setDepartmentName(resultSet.getString(DEPARTMENT_NAME));
            department.setDescription(resultSet.getString(DESCRIPTION));
            return department;
        }
    }
}
