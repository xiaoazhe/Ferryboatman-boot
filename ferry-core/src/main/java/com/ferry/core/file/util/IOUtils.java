package com.ferry.core.file.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO工具类
 * @Author: 摆渡人
 * @Date: 2021/4/26
 */
public class IOUtils {

	/**
	 * 关闭对象，连接
	 * @param closeable
	 */
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }
}
