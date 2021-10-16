package gomoku;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.*;


/**
 * 游戏总配置
 */

public class ConfigService {
    public static int viewportWidth;
    public static int viewportHeight;
    public static Config config = new Config();
    public static final String configURL = "config.xml";

    public static void configLoad() {
        readConfigure();
        viewportWidth = config.getViewPortWidth();
        viewportHeight = config.getViewPortHeight();
    }

    public static String getResource(String name) {
        return ConfigService.class.getResource("/" + name).toString();
    }
    //取指定资源文件路径

    public static void readConfigure() {
        File conf = new File(configURL);
        if (conf.exists()) {
            XStream xstream = new XStream();
            xstream.addPermission(AnyTypePermission.ANY);
            config = (Config) xstream.fromXML(conf);
        } else {
            System.out.println("配置文件已损坏，初始化配置文件");
            config = new Config();
            writeConfigure();
        }
    }

    public static void writeConfigure() {
        XStream xstream = new XStream();
        String xml = xstream.toXML(config);
        xstream.alias("config", Config.class);
        try {
            java.io.FileWriter fw = new java.io.FileWriter(configURL);
            fw.write(xml);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
