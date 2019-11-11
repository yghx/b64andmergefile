package com.ygpx.www.b64;

import java.io.*;
import java.util.Base64;

public class B64 {

    private static int p_mode;

    public static void main(String[] args) {
        // 编码
//        x2B64("", B64Constant.ENCODE);
        // 解码
        x2B64("", B64Constant.DECODE);
    }

    public static void x2B64(String parPath, int mode) {
        p_mode = mode;
        File file = new File(parPath);
        dealFile(file);

    }

    private static void dealFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                // 递归遍历
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    dealFile(files[i]);
                }
            } else {
                // 开始转码
                try {
                    startX2B64(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
        }
    }

    private static void startX2B64(File file) throws IOException {
        String originName = file.getName();
        String parPath = file.getParent();
        String b64Name = null;

        if (p_mode != B64Constant.ENCODE) {
            if (originName != null ) {
                originName = originName.replaceAll(" ","/");
            }
        }

        if (p_mode == B64Constant.ENCODE) {
            b64Name = new String(x2B64Encoder(originName), B64Constant.ENCODING_UTF_8);
            if (!originName.contains(".")) { // 必须有扩展名文件
                return;
            }
        } else {
            b64Name = new String(b642XDecoder(originName), B64Constant.ENCODING_UTF_8);
            if (originName.contains(".")) {
                return;
            }
        }
        // windows文件名不能包含特殊字符 base64只有'\'不能作为文件名称,但是base64码表是'/' ? 疑惑
        if (p_mode == B64Constant.ENCODE) {
            if (b64Name != null ) {
                b64Name = b64Name.replaceAll("/"," ");
            }
        }

        // 输入
        FileInputStream fis = new FileInputStream(file);
//        FileChannel fisChannel = fis.getChannel();
        // 输出
        FileOutputStream fos = new FileOutputStream(new File(parPath + File.separator + b64Name));
//        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fos));
//        FileChannel fosChannel = fos.getChannel();
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] buf = new byte[1200];
        int i = -1;
        while ((i = fis.read(buf)) != -1) {
            byte[] bs = new byte[i];
            for (int j = 0; j < i; j++) {
                bs[j] = buf[j];
            }
            if (p_mode == B64Constant.ENCODE) {
                byte[] bytes = x2B64Encoder(bs);
                fos.write(bytes);
                fos.flush();
            } else {
                byte[] bytes = b642XDecoder(bs);
                // 使用字节流写解密数据
                fos.write(bytes);
                fos.flush();
            }
        }

    }

    public static byte[] x2B64Encoder(String originStrg) {
        Base64.Encoder encoder = Base64.getEncoder()/*.withoutPadding()*/;
        return encoder.encode(originStrg.getBytes());
    }

    public static byte[] x2B64Encoder(byte[] originBytes) {
        Base64.Encoder encoder = Base64.getEncoder()/*.withoutPadding()*/;
        return encoder.encode(originBytes);
    }

    public static byte[] b642XDecoder(byte[] b64Byte) {
        return Base64.getDecoder().decode(b64Byte);
    }

    public static byte[] b642XDecoder(String b64Strg) {
        return Base64.getDecoder().decode(b64Strg);
    }

}
