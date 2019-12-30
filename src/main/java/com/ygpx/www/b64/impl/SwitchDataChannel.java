package com.ygpx.www.b64.impl;

import com.ygpx.www.b64.B64Template;
import com.ygpx.www.b64.api.ISwitchDataChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Base64;

/**
 * 内存映射方式不适合做数据转化,非常时候大数据文件的拷贝工作(或者是数据读取工作)
 */
public class SwitchDataChannel implements ISwitchDataChannel {
    @Override
    public void x2B64ByChannel(File file) {
        String path = B64Template.dealEncodeFileName(file);
        if (path == null) return;
        try (
                FileChannel fis = new FileInputStream(file).getChannel();
                FileChannel fos = new FileOutputStream(path).getChannel()
        ) {
            byte[] buf = new byte[1200];
            ByteBuffer byteBuff = ByteBuffer.allocate(1200);
            int len;
            while ((len = fis.read(byteBuff)) != -1) {
                byteBuff.flip(); // 准备缓冲器
                byteBuff.get(buf, 0, len); // len保证最后一次读取也不会出错
                byte[] bytes;
                if (len <= buf.length) {
                    byte[] bs = new byte[len];
                    System.arraycopy(buf, 0, bs, 0, len); // 使用系统方法拷贝数组
                    bytes = Base64.getEncoder().encode(bs);
                } else {
                    bytes = Base64.getEncoder().encode(buf);
                }
                fos.write(ByteBuffer.wrap(bytes)); // 将一个字节数组包装成字节缓冲器
                byteBuff.clear(); // 对缓冲器的内部指针重新安排,一遍下一次read操作
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void b642XByChannel(File file) {
        String path = B64Template.dealDecodeFileName(file);
        if (path == null) return;
        try (FileChannel fis = new FileInputStream(file).getChannel();
             FileChannel fos = new FileOutputStream(path).getChannel()
        ) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1200);
            byte[] buf = new byte[1200];
            int len;
            while ((len = fis.read(byteBuffer)) != -1) {
                byteBuffer.flip(); // 准备
                byteBuffer.get(buf, 0, len); // 获取字节
                // 解码自己
                byte[] bytes;
                if (len <= buf.length) {
                    byte[] bs = new byte[len];
                    System.arraycopy(buf, 0, bs, 0, len); // 使用系统方法拷贝数组
                    bytes = Base64.getDecoder().decode(bs);
                } else {
                    bytes = Base64.getDecoder().decode(buf);
                }
                fos.write(ByteBuffer.wrap(bytes)); // 包装.写出
                byteBuffer.clear(); // 清理
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
