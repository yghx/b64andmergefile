package com.ygpx.www.b64.api;

import java.io.File;

/**
 * 重构代码:使用单一职责原则,里氏替换原则,依赖倒置,接口隔离,迪米特法则,开闭原则
 */
public interface IX2B64 {
    /**
     * 文件转化成b64文件
     * @param parPath String:处理文件的目录路径
     */
    @SuppressWarnings("unused")
    void x2B64(String parPath);

    /**
     * 文件转化成b64文件
     * @param file File:处理文件的目录文件
     */
    void x2B64(File file);
}
