package stackoversearch.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class Config {

    private static Logger Log = Logger.getLogger(Config.class.getName());
    private static Properties prop = null;
    private static final String CONFIG_PATH = "src\\main\\java\\stackoversearch\\config\\";

    static{
        File dir = new File(CONFIG_PATH);
        FilenameFilter filter = (dir1, name) -> name.endsWith(".properties");
        String files[] = dir.list(filter);
        prop = new Properties();

        for(String file : files ){
            File localFile = new File(CONFIG_PATH + file);
            if(localFile.isDirectory())
                continue;

            try {
                FileInputStream fis = new FileInputStream(localFile.getAbsolutePath());
                prop.load(fis);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(String propertyName)  {
//        Log.info(String.format("Loading property %s from %s.", propertyName, new File(CONFIG_PATH).getAbsolutePath()));
        return prop.getProperty(propertyName);
    }
}
