package com.oms.logger;

import com.oms.model.Order;
import java.io.FileWriter;

public class FileLogger {

    private static final String FILE = "orders.log";

    public static synchronized void log(Order order) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(order.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
