package com.youzan.nsq.client.core.lookup;

import com.youzan.nsq.client.entity.Address;
import com.youzan.nsq.client.exception.NSQLookupException;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import java.util.SortedSet;

/**
 * One lookup cluster
 * 
 * @author <a href="mailto:my_email@email.exmaple.com">zhaoxi (linzuxiong)</a>
 *
 * 
 */
public interface LookupService extends java.io.Serializable, Closeable {
    Random _r = new Random(10000);

    void start() throws IOException;

    /**
     *
     * lookup the writable NSQd (DataNode)
     * 
     * @param topic
     *            a topic name
     * @return ordered NSQd-Server's addresses
     * @throws NSQLookupException
     *             if an error occurs
     */
    SortedSet<Address> lookup(String topic) throws NSQLookupException;

    /**
     * lookup the writable/non-writable NSQd (DataNode)
     * 
     * @param topic
     *            a topic name
     * @param writable
     *            set it boolean value
     * @return ordered NSQd-Server's addresses
     * @throws NSQLookupException
     *             if an error occurs
     */
    SortedSet<Address> lookup(String topic, boolean writable) throws NSQLookupException;

    /**
     * Perform the action quietly. No exceptions.
     */
    @Override
    void close();
}
