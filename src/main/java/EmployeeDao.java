import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    public EmployeeDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long createEmployee(String name) {
//        jdbcTemplate.update("insert into employees(emp_name) values (?)", name);
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into emplyees(emp_name) value (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            return ps;
        }, holder);

        return holder.getKey().longValue();
    }
//        public long createEmployee(String name) {
////        jdbcTemplate.update("insert into employees(emp_name) values (?)", name);
//        KeyHolder holder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement ps = connection.prepareStatement("insert into emplyees(emp_name) value (?)", Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, name);
//                return ps;
//            }
//        }, holder);
//
//        return holder.getKey().longValue();
//    }

    public List<String> listEmployeeNames() {
        return jdbcTemplate.query("select emp_name from employees order by emp_name", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("emp_name");
            }
        });
    }

    public String findEmployeeNameById(Long id) {
        return jdbcTemplate.queryForObject("Select emp_name from employees where id = ?",
                new Object[]{id}, (rs, rowNum) -> rs.getString("emp_name"));
    }

//    public String findEmployeeNameById(Long id) {
//        return jdbcTemplate.queryForObject("Select emp_name from employees where id = ?",
//                new Object[]{id}, new RowMapper<String>() {
//                    @Override
//                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//                        return rs.getString("emp_name");
//                    }
//                });
//    }

}
