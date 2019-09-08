package com.ygpx.www.utils;

import java.io.File;
import java.io.IOException;

public interface IDealFile {

    void dealFile(String filePath) throws IOException;

    void dealFile(File file) throws IOException;
}
