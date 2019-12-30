package com.ygpx.www.b64.impl;

import com.ygpx.www.b64.B64Template;
import com.ygpx.www.b64.api.IB642X;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class B642X implements IB642X {
    @Override
    public void b642X(String parPath) {
        Path path = Paths.get(parPath);
        b642X(path.toFile());
    }

    @Override
    public void b642X(File file) {
        String path = B64Template.dealDecodeFileName(file);
        if (path == null) return;
        try (FileInputStream fis = new FileInputStream(file);
             FileOutputStream fos = new FileOutputStream(new File(path))) {
            byte[] buf = new byte[1200];
            int i;
            while ((i = fis.read(buf)) != -1) {
                byte[] bs = new byte[i];
                System.arraycopy(buf, 0, bs, 0, i);
                // 使用字节流写解密数据
                fos.write(Base64.getDecoder().decode(bs));
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
