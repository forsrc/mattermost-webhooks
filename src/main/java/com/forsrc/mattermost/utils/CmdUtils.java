package com.forsrc.mattermost.utils;


import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

public class CmdUtils {

    public static String exec(String[] cmd) {
        System.out.println("exec: " + Arrays.asList(cmd));
        StringBuilder message = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            int rc = process.waitFor();
            message.append(IOUtils.toString(process.getInputStream(), Charset.forName("UTF-8"))).append("\n");
            message.append(IOUtils.toString(process.getErrorStream(), Charset.forName("UTF-8"))).append("\n");
        } catch (Exception e) {
            return e.getMessage();
        }
        return message.toString();
    }

    public static void main(String[] args) {
        System.out.println(CmdUtils.exec(new String[] {"echo", "OK"}));
        System.out.println(CmdUtils.exec(new String[] {"cmd", "/c", "echo", "OK"}));
    }
}
