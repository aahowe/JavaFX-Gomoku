package gomoku.database;

import gomoku.ConfigService;

import java.sql.*;

public class Connect {
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public Connection dbConnection() {
        URL = ConfigService.config.getDatabaseURL();
        USER = ConfigService.config.getDatabaseName();
        PASSWORD = ConfigService.config.getDatabasePassword();
        Connection con = null;
        try {
            // 加载驱动类
            Class.forName(DRIVER);
            long start = System.currentTimeMillis();
            // 建立连接
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            long end = System.currentTimeMillis();
            System.out.println(con);
            System.out.println("建立连接耗时： " + (end - start) + " ms");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }
}
