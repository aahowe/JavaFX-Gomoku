package gomoku.database;

import gomoku.kernel.Save;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Operate {

    public static boolean ConnectUserDB(String loginname, String loginpassword) {
        ResultSet rs;
        Statement stmt;

        Connect databaseConnect = new Connect();
        Connection con = databaseConnect.dbConnection();

        try {
            // 创建Statement对象
            stmt = con.createStatement();

            // 执行SQL语句
            rs = stmt.executeQuery("select * from User");
            while (rs.next()) {
                if (loginname.equals(rs.getString(1)) && loginpassword.equals(rs.getString(2))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addUser(String addName, String addPassword) {
        ResultSet rs;
        Statement stmt;

        Connect databaseConnect = new Connect();
        Connection con = databaseConnect.dbConnection();

        try {
            // 创建Statement对象
            stmt = con.createStatement();

            // 执行SQL语句
            rs = stmt.executeQuery("select * from User");
            while (rs.next()) {
                if (addName.equals(rs.getString(1))) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // 创建Statement对象
            String sql1 = "insert into User value(?,?)";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            if (!Objects.equals(addName, "")) {
                ps1.setString(1, addName);
                ps1.setString(2, addPassword);
                // 执行SQL语句
                ps1.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Save> ConnectSaveDB(String name) {
        List<Save> saveList = new ArrayList<>();
        ResultSet rs;

        Connect databaseConnect = new Connect();
        Connection con = databaseConnect.dbConnection();

        try {
            PreparedStatement ps = con.prepareStatement("select * from Save");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (Objects.equals(rs.getString(1), name)) {
                    Blob inBlob = rs.getBlob(2);
                    InputStream is = inBlob.getBinaryStream();                //获取二进制流对象
                    BufferedInputStream bis = new BufferedInputStream(is);    //带缓冲区的流对象
                    byte[] buff = new byte[(int) inBlob.length()];
                    while (-1 != (bis.read(buff, 0, buff.length))) {            //一次性全部读到buff中
                        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buff));
                        Save s = (Save) in.readObject();                   //读出对象
                        saveList.add(s);
                    }
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return saveList;
    }


    public static void addSave(String name, Save data, String saveName) {

        Connect databaseConnect = new Connect();
        Connection con = databaseConnect.dbConnection();

        try {
            PreparedStatement ps = con.prepareStatement("insert into Save value(?,?,?)");
            ps.setString(1, name);
            ps.setObject(2, data);
            ps.setString(3, saveName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSave(String pname, String name) {
        Connect databaseConnect = new Connect();
        Connection con = databaseConnect.dbConnection();

        try {
            PreparedStatement ps = con.prepareStatement("update Save set savename = '" + name + "'where savename ='" + pname + "'");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSave(String saveName) {
        Connect databaseConnect = new Connect();
        Connection con = databaseConnect.dbConnection();

        try {
            PreparedStatement ps = con.prepareStatement("delete from Save where savename = '" + saveName + "'");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
