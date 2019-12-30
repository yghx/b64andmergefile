package com.ygpx.www.merge;

import com.ygpx.www.utils.FileConstant;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapInfo {

    public static final Map<String, String> KEY_MAP = new HashMap<>();

    public static void obtainKeyMap(String parPath) {

        File file = new File(parPath);
        if (file.exists() && file.isDirectory()) {
            FilenameFilter filter = (file1, s) -> s.endsWith(FileConstant.FILE_SUFFIX_M3U8);
            File[] files = file.listFiles(filter);
            for (int i = 0; files != null && i < files.length; i++) {
                try {
                    dealFile(files[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("you input is error!!!");
        }

    }

    @SuppressWarnings("unused")
    public static void dealFile(String filePath) throws IOException {
        File file = new File(filePath);
        dealFile(file);
    }

    public static void dealFile(File file) throws IOException {
        String name = file.getName();
        if (name.endsWith(FileConstant.FILE_SUFFIX_M3U8)) {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String tmp;
            while ((tmp = br.readLine()) != null) {
                Pattern p = Pattern.compile(FileConstant.UUID_EXPRESS);
                Matcher m = p.matcher(tmp);
                if (m.find()) {
                    String key = m.group(1);
                    name = name.replaceAll("\\s", "_");
                    KEY_MAP.put(key, name.replaceAll(".m3u8", ""));
                    return;
                }
            }
        }
    }
}
