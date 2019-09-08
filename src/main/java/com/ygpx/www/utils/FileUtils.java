package com.ygpx.www.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    private static IDealFile dealFile;

    public FileUtils(IDealFile dealFile) {
        this.dealFile = dealFile;
    }

    public void traverseFile(String filePath) {
        File file = new File(filePath);
        traverseFile(file);
    }

    public void traverseFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                // 递归遍历
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    traverseFile(files[i]);
                }
            } else {
                // 开始转码
                try {
                    dealFile.dealFile(file);
                } catch (IOException e) {
                    LOG.error("ygpx_error:[io] {}", e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            LOG.error("ygpx_error:[file] The file do not exists!");
        }
    }
}
