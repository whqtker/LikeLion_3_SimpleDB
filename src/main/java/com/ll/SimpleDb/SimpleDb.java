package com.ll.SimpleDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SimpleDb {
    private final Sql sql = new Sql();
    Connection conn = null;
    PreparedStatement prst = null;
    public SimpleDb(String hostName, String userName, String password, String dbName) {
        String url = "jdbc:mysql://" + hostName + "/" + dbName;

        try {
            // MySQL JDBC Driver 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password); // DB 연결
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDevMode(boolean b) {
    }

    public void run(String sql) {
    }

    public Sql genSql() {
    }

    public void close() {
    }

    public void startTransaction() {
    }

    public void rollback() {
    }

    public void commit() {
    }
}
