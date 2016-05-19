/**
 * 
 */
package com.youzan.nsq.client.core.command;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxi (linzuxiong)
 * @email linzuxiong1988@gmail.com
 *
 */
public class Magic implements NSQCommand {
    private static final Logger logger = LoggerFactory.getLogger(Magic.class);
    private static final String MAGIC = "  V2";
    private static final byte[] ASCII_MAGIC;

    static {
        byte[] tmp;
        try {
            tmp = MAGIC.getBytes(ASCII);
        } catch (UnsupportedEncodingException e) {
            tmp = MAGIC.getBytes();
        }
        ASCII_MAGIC = tmp;
    }

    private static class Instance {
        // final
        private static final Magic instance = new Magic();
    }

    public static Magic getInstance() {
        return Instance.instance;
    }

    private Magic() {
    }

    @Override
    public String getString() {
        return MAGIC;
    }

    @Override
    public byte[] getBytes() {
        return ASCII_MAGIC;
    }
}
