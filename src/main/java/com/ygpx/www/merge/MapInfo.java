package com.ygpx.www.merge;

import com.ygpx.www.utils.FileConstant;
import com.ygpx.www.utils.FileUtils;
import com.ygpx.www.utils.IDealFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapInfo {

    public static final Map<String, String> KEY_MAP = new HashMap<>();

    public static Map<String, String> obtainKeyMap(String parPath) {

        File file = new File(parPath);
        if(file.exists() && file.isDirectory()){
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    if (s.endsWith(FileConstant.FILE_SUFFIX_M3U8)) {
                        return true;
                    }
                    return false;
                }
            };
            File[] files = file.listFiles(filter);
            for (int i = 0; i < files.length; i++) {
                try {
                    dealFile(files[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("you input is error!!!");
        }

        return null;
    }

    public static void dealFile(String filePath) throws IOException {
        File file = new File(filePath);
        dealFile(file);
    }

    public static void dealFile(File file) throws IOException {
        String name = file.getName();
        if (name.endsWith(FileConstant.FILE_SUFFIX_M3U8)) {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String tmp = null;
            while ((tmp = br.readLine()) != null) {

                Pattern p = Pattern.compile(FileConstant.UUID_EXPRESS);
                Matcher m = p.matcher(tmp);
                if (m.find()) {
                    String key = m.group(1);
                    name = name.replaceAll("\\s","_");
                    KEY_MAP.put(key, name.replaceAll(".m3u8",""));
                    return;
                }
            }
        }
    }
}
