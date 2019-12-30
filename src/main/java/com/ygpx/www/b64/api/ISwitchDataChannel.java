package com.ygpx.www.b64.api;

import java.io.File;

/**
 * 使用通道内存映射处理文件
 */
public interface ISwitchDataChannel {

    void x2B64ByChannel(File file);

    void b642XByChannel(File file);
}
