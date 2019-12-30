package com.ygpx.www.merge;

import com.ygpx.www.utils.FileConstant;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 合并.ts文件,使用nio特性
 * 使用了lambda表达式简化代码
 * 使用了try简化流代码
 */
public class MergeInfo {

    public static final String ROOT_PATH = "E:\\video\\";

    public static void generateFile(String parPath) {
        MapInfo.obtainKeyMap(parPath);
        generate(parPath);
    }

    private static void generate(File file) {
        if (file.exists() && file.isDirectory()) {
            String parent = file.getName();
            boolean matches = Pattern.matches(FileConstant.UNIX_DIR, parent);
            if (matches) {
                FilenameFilter filter = (file1, s) -> s.endsWith(FileConstant.FILE_SUFFIX_TS);
                File[] files = file.listFiles(filter);
                assert files != null;
                int[] fName = new int[files.length];
                for (int i = 0; i < files.length; i++) {
                    String name = files[i].getName();
                    String s = name.replaceAll(".ts", "");
                    try {
                        // 之所以使用parseInt替换ValueOf.是因为ValueOf也是利用parseInt实现
                        // 并且返回的是一个应用对象
                        fName[i] = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                Arrays.sort(fName);
                try {
                    mergeToOne(fName, parent, file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                FilenameFilter filter = (file12, s) -> Pattern.matches(FileConstant.UNIX_DIR, s);
                File[] files = file.listFiles(filter);
                if (files != null) {
                    for (File f : files) {
                        generate(f);
                    }
                }
            }
        }
    }

    private static void mergeToOne(int[] fName, String parent, String dirPath) {
        String p = MapInfo.KEY_MAP.get(parent);
        String newName = p == null ? UUID.randomUUID().toString() : p;
        newName += ".ts";
        if (!dirPath.endsWith(File.separator)) {
            dirPath += File.separator;
        }
        String path = ROOT_PATH + newName;
        try (FileChannel fos = new FileOutputStream(path).getChannel()) {
            for (int value : fName) {
                File file = new File(dirPath + value + ".ts");
                try (FileChannel fis = new FileInputStream(file).getChannel()) {
                    fis.transferTo(0, fis.size(), fos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用重载String方法进入
     *
     * @param parPath String
     */
    private static void generate(String parPath) {
        File file = new File(parPath);
        generate(file);
    }

}
