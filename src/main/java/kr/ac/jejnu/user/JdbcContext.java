package kr.ac.jejnu.user;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    User jdbcContextForGet(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);
//            preparedStatement = connection.prepareStatement("select id, name, password from userinfo where id = ?");
//            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    void jdbcContextForInsert(User user, StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);
//            preparedStatement = connection.prepareStatement("insert into userinfo (name, password) values (?,?)", Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setString(1, user.getName());
//            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            user.setId(resultSet.getLong(1));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void jdbcContextForUpdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    User get(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        };
        return jdbcContextForGet(statementStrategy);
    }

    void insert(User user, Object[] params, String sql) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for(int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        };
        jdbcContextForInsert(user, statementStrategy);
    }

    void update(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
//            preparedStatement.setObject(1, id);
            return preparedStatement;
        };
        jdbcContextForUpdate(statementStrategy);
    }
}