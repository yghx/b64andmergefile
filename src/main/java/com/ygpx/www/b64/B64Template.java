package com.ygpx.www.b64;

import com.ygpx.www.b64.api.TransferDataFI;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 使用lambda表达式消除冗余代码:
 * lambda表达式的参数有两种传入方式,外部传入(非闭包)
 * 调用方法内部传入,闭包方式,根据接口确定参数类型,根据参数由调用方决定
 */
public class B64Template {

    public static void dealFile(File file, TransferDataFI transferDataFI) {
        if (file.exists()) {
            if (file.isDirectory()) {
                // 递归遍历
                File[] files = file.listFiles();
                // 需要进行空判断,保证代码健壮性
                for (int i = 0; files != null && i < files.length; i++) {
                    dealFile(files[i], transferDataFI);
                }
            } else {
                // 开始转码
                transferDataFI.transferData(file);
            }
        } else {
            System.out.printf("文件 %s 不存在", file.getPath());
        }
    }

    public static String dealEncodeFileName(final File file) {
        String originName = file.getName();
        String parPath = file.getParent();
        byte[] encode = Base64.getEncoder().encode(originName.getBytes());
        String b64Name = new String(encode, StandardCharsets.UTF_8);
        if (!originName.contains(".")) { // 必须有扩展名文件
            return null;
        }
        // windows文件名不能包含特殊字符 base64只有'\'不能作为文件名称,但是base64码表是'/' ? 疑惑
        b64Name = b64Name.replaceAll("/", " ");
        return parPath + File.separator + b64Name;
    }

    public static String dealDecodeFileName(final File file) {
        String originName = file.getName();
        String parPath = file.getParent();
        String b64Name;

        originName = originName.replaceAll(" ", "/");
        byte[] decode = Base64.getDecoder().decode(originName);
        b64Name = new String(decode, StandardCharsets.UTF_8);
        if (originName.contains(".")) {
            return null;
        }
        return parPath + File.separator + b64Name;
    }

}
