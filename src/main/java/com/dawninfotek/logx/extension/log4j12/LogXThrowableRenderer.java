package com.dawninfotek.logx.extension.log4j12;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogXThrowableRenderer {

    public static String[] doRender(Throwable t) {
        if (t == null) {
            return new String[0];
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.close();

        return new String[] {sw.toString()};
    }
}
