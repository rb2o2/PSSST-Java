package ru.foxsoft.pssstjava;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Utils {

    public static String conv(String text) {
//        String res = "Encoding not supported";
        return new String(text.getBytes(StandardCharsets.UTF_8), Charset.forName("windows-1251"));
    }

    public static String conv(int[] chars) {
        byte[] bytes = new byte[chars.length * 2];
        for (int i = 0; i < chars.length; i++) {
            bytes[2*i] =  (byte)(chars[i]);
            bytes[2*i + 1] = (byte)(chars[i] << 8);
        }
        return new String(bytes, Charset.forName("windows-1251"));
    }
}
