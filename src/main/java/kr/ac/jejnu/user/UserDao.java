package kr.ac.jejnu.user;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private final JdbcContext jdbcContext;

    protected UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User get(Long id) throws ClassNotFoundException, SQLException {
        StatementStrategy statementStrategy = new GetStatementStrategy(id);

        return jdbcContext.jdbcContextForGet(statementStrategy);
    }

    public void insert(User user) throws SQLException {
        StatementStrategy statementStrategy = new InsertStatementStrategy(user);
        jdbcContext.jdbcContextForInsert(user, statementStrategy);
    }

    //    }
//        jdbcContext.jdbcContextForUpdate(statementStrategy);
//    private void jdbcContextForUpdate(StatementStrategy statementStrategy) throws SQLException {
//
//    }
//        jdbcContext.jdbcContextForInsert(user, statementStrategy);
//    private void jdbcContextForInsert(User user, StatementStrategy statementStrategy) throws SQLException {
//    private User jdbcContextForGet(StatementStrategy statementStrategy) throws SQLException {
//        return jdbcContext.jdbcContextForGet(statementStrategy);
//    }
    public void update(User user) throws SQLException {
        StatementStrategy statementStrategy = new UpdateStatementStrategy(user);
        jdbcContext.jdbcContextForUpdate(statementStrategy);
    }


    public void delete(Long id) throws SQLException {
        StatementStrategy statementStrategy = new DeleteStatementStrategy(id);
        jdbcContext.jdbcContextForUpdate(statementStrategy);
    }

}
//    public PreparedStatement makeStatement(Long id, Connection connection) throws SQLException {
//        PreparedStatement preparedStatement;
//        preparedStatement = connection.prepareStatement("delete from userinfo where id = ?");
//        preparedStatement.setLong(1, id);
//        return preparedStatement;
//    }

//    public Connection getConnection() throws ClassNotFoundException, SQLException {
//
//        return connectionMaker.getConnection();
//    }
