package com.testconfig.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/4/25, MarkHuang,new
 * </ul>
 * @since 2018/4/25
 */
public class TestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtil.class);

    private TestUtil() {
    }

    public static boolean checkHost(String url, int port) {
        boolean status = false;
        InetSocketAddress address = new InetSocketAddress(url, port);
        try (Socket sock = new Socket();) {
            sock.connect(address, 3000);
            if (sock.isConnected()) status = true;
        } catch (Exception e) {
            status = false;
            LOGGER.warn("", e);
        }
        return status;
    }
}
