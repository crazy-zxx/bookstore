package com.me.web;

import java.sql.Connection;

public class ConnectionContext {

    //单例模式
    private ConnectionContext() {
    }

    private static ConnectionContext instance = new ConnectionContext();

    public static ConnectionContext getInstance() {
        return instance;
    }


    /**
     * 线程本地化：线程绑定数据库连接
     */
    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public void bind(Connection connection) {
        connectionThreadLocal.set(connection);
    }

    public Connection get() {
        return connectionThreadLocal.get();
    }

    public void remove() {
        connectionThreadLocal.remove();
    }

}
