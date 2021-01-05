package e_comerce.Connection;
import e_comerce.common.AppConfig;

import java.sql.*;

public class MyConnection {

    public static Connection connection = null;
    public static void driverTest() throws ClassNotFoundException {
        try {
            Class.forName(AppConfig.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("JDBC Driver not found" + e.getMessage());
        }
    }

    public static Connection connectDB() throws ClassNotFoundException, SQLException {
        driverTest();
        try {
            // sử dụng DriverManager.getConnection để tạo liên liên kết đến
            //database hàm yêu cầu 3 tham số đường dẫn database, tài khoản, mất khẩu
            //database
            connection = DriverManager.getConnection(AppConfig.URL_DATABASE,
                    AppConfig.USERNAME, AppConfig.PASSWORD);
            System.out.println("Connect DB successfully");
        } catch (SQLException throwables) {
            throw new SQLException("Connect DB fail " + throwables.getMessage());
        }
        return connection;
    }
    public PreparedStatement prepare(String sql) {
        System.out.println(">> " + sql);
        try {
            //đối với các câu lệnh tìm kiếm thì hàm prepareStatement
            //cần truyền thêm một tham số như sau ResultSet.TYPE_SCROLL_INSENSITIVE
            //ResultSet.TYPE_SCROLL_INSENSITIVE cho phép con trỏ ResultSet chạy từ
            //bản ghi đầu tiền xuống bản ghi cuối cùng.
            return connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    public PreparedStatement prepareUpdate(String sql) {
        System.out.println(">> " + sql);
        try {

            return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    public static void closeConnection(Connection connection) throws SQLException {
        if(connection != null) {
            connection.close();
            System.out.println("Connection is closed");
        }
    }
    public static void rollBackConnection(Connection connection) throws SQLException {
        if(connection != null) {
            connection.rollback();
        }
    }



}
