package server;

import sun.rmi.runtime.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class LogSave {
    private static Logger logger = Logger.getLogger(Log.class.getName());
    private static SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy" + "\t" +  "HH:mm:ss");
    static {
        try {
            String date = fmt.format(new Date().getTime());

            Handler handler = new FileHandler("com.myChat/src/logs/mylog.log",true);
            handler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return date + "\t" + record.getLevel() + "\t" + record.getMessage() + "\n";
                }
            });

            logger.addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addLog(String msg){
        addLog(msg, Level.INFO);
    }

    public static void addLog(String msg, Level level){
        logger.setLevel(Level.ALL);
        logger.getHandlers()[0].setLevel(Level.ALL);
        logger.log(level, msg);
    }
}
