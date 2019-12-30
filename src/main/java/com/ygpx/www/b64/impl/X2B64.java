package com.ygpx.www.b64.impl;

import com.ygpx.www.b64.B64Template;
import com.ygpx.www.b64.api.IX2B64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 重构代码:使用单一职责原则,里氏替换原则,依赖倒置,接口隔离,迪米特法则,开闭原则
 */
public class X2B64 implements IX2B64 {
    @Override
    public void x2B64(String parPath) {
        File file = new File(parPath);
        x2B64(file);
    }

    @Override
    public void x2B64(File file) {
        String path = B64Template.dealEncodeFileName(file);
        if (path == null) return;
        try (
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(new File(path))) {
            byte[] buf = new byte[1200];
            int i;
            while ((i = fis.read(buf)) != -1) {
                byte[] bs = new byte[i];
                System.arraycopy(buf, 0, bs, 0, i); // 使用系统方法拷贝数组
                fos.write(Base64.getEncoder().encode(bs));
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
