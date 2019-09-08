package com.ygpx.www.merge;

import com.ygpx.www.utils.FileConstant;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class MergeInfo {

    public static final Map<String, String> KEY_MAP = new HashMap<>();

    public static final String ROOT_PATH = "E:\\video\\";

    public static void main(String[] args) {
        generateFile(ROOT_PATH);
    }

    public static Map<String, String> generateFile(String parPath) {
        MapInfo.obtainKeyMap(parPath);
        generate(parPath);
        return null;
    }

    private static void generate(File file) {
        if (file.exists() && file.isDirectory()) {
            String parent = file.getName();
            boolean matches = Pattern.matches(FileConstant.UNIX_DIR, parent);
            if (matches) {
                FilenameFilter filter = new FilenameFilter() {
                    @Override
                    public boolean accept(File file, String s) {
                        if (s.endsWith(FileConstant.FILE_SUFFIX_TS)) {
                            return true;
                        }
                        return false;
                    }
                };
                File[] files = file.listFiles(filter);
                int[] fName = new int[files.length];
                for (int i = 0; i < files.length; i++) {
                    String name = files[i].getName();
                    String s = name.replaceAll(".ts", "");
                    try {
                        fName[i] = Integer.valueOf(s);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                Arrays.sort(fName);
                try {
                    mergeToOne(fName, parent, ROOT_PATH, file.getCanonicalPath());
//                    mergeToOne(fName, MapInfo.KEY_MAP, parent, ROOT_PATH, file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                FilenameFilter filter = new FilenameFilter() {
                    @Override
                    public boolean accept(File file, String s) {
                        boolean matches = Pattern.matches(FileConstant.UNIX_DIR, s);
                        return matches;
                    }
                };
                File[] files = file.listFiles(filter);
                for (File f : files) {
                    generate(f);
                }
            }
        } else {
            return;
        }
    }

    private static void mergeToOne(int[] fName, String parent, String generatePath, String dirPath) {
        try {
            if (!generatePath.endsWith(File.separator)) {
                generatePath += File.separator;
            }

            String p = MapInfo.KEY_MAP.get(parent);
            String newName = p == null ? UUID.randomUUID().toString() : p;
            newName += ".ts";
            if (!dirPath.endsWith(File.separator)) {
                dirPath += File.separator;
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(ROOT_PATH+newName);
            System.out.println(ROOT_PATH+newName);
            for (int i = 0; i < fName.length; i++) {
                File file = new File(dirPath + fName[i] + ".ts");
                FileInputStream fis = new FileInputStream(file);
                FileChannel fisChannel = fis.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                fisChannel.transferTo(0, fisChannel.size(), fos.getChannel());
                fis.close();
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * 使用重载String方法进入
     *
     * @param parPath
     */
    private static void generate(String parPath) {
        File file = new File(parPath);
        generate(file);
    }

    private static void mergeToOne(int[] fName, Map<String, String> keyMap, String parent, String
            generatePath, String dirPath) {
        StringBuilder sb = new StringBuilder();
        if (!dirPath.endsWith(File.separator)) {
            dirPath += File.separator;
        }
        for (int i = 0; i < fName.length; i++) {
            sb.append("+").append(dirPath).append(fName[i]).append(".ts");
        }
        if (fName.length > 0) {
            sb.delete(0, 1);
        }

        if (!generatePath.endsWith(File.separator)) {
            generatePath += File.separator;
        }
        String newName = keyMap.get(parent) == null ? UUID.randomUUID().toString() : keyMap.get(parent);
        String cmd = "cmd copy /b " + sb.toString() + " " + generatePath + newName + ".ts"; //
        System.out.println(cmd);
        Runtime run = Runtime.getRuntime();
        try {
            Process exec = run.exec(cmd);
            System.out.println("over!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
