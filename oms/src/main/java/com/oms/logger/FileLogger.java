package com.oms.logger;

import com.oms.model.Order;
import java.io.FileWriter;

public class FileLogger {
//    static → one shared file for entire application
//    final → cannot be changed

    private static final String FILE = "orders.log";
//2️⃣ Static + Synchronized Method
//    public static synchronized void log(Order order)
//    Why static?
//    So you can call directly:
//            FileLogger.log(order);
//    Without creating object:
//            new FileLogger().log(order); ❌
    public static synchronized void log(Order order) {
//        Why synchronized?
//        To prevent data corruption in multi-thread environment.
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(order.toString() + "\n");
//            Converts Order object → string
//            Writes into file
//            Adds new line
        } catch (Exception e) {
            e.printStackTrace();
//            we can also used getMessege() -> but it will only print error messege
            Method	Output
//            e.getMessage() -> Only error message
//            e.printStackTrace() ->	Full error details + call stack

        }
    }
}
