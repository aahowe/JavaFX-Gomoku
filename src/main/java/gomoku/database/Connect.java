package gomoku.database;

import java.sql.*;

public class Connect {
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost:3306/gomoku";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public Connection dbConnection() {
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
