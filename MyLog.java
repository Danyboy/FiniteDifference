import java.util.logging.Logger;

/**
 * Created by Dany on 12.06.14
 */
public class MyLog { //extends Logger

    Logger log;
    private MyLog(Class myClass){
//          super();
//        log = Logger.getLogger(myClass.class.getName()); //TODO Fix!
    }

    public Logger getLog(Class MyClass) {
        if (log == null){
            new MyLog(MyClass);
        }
        return log;
    }

    public void myLog(String s) {
        boolean logOn = false;
        if (logOn) {
            System.out.println(s);
        }


        boolean systemLog = false;
        if (systemLog) {
            log.info(s);
        }

    }
}
