package com.ygpx.www.b64.api;

import java.io.File;

public interface IB642X {
    /**
     * 文件转化成b64文件
     * @param parPath String:处理文件的目路径
     */
    @SuppressWarnings("unused")
     void b642X(String parPath);

    /**
     * 文件转化成b64文件
     * @param file File:处理文件的目录文件
     */
    void b642X(File file);
}
