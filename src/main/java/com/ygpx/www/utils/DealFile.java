package com.ygpx.www.utils;

import java.io.File;
import java.io.IOException;

public abstract class DealFile implements IDealFile {
    public abstract void dealFile(String filePath) throws IOException;

    public abstract void dealFile(File file) throws IOException;
}
