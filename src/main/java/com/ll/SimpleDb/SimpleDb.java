package com.ll.SimpleDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SimpleDb {
    Connection conn = null;
    PreparedStatement prst = null;
    private boolean devMode = false;

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
    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public void run(String sql, Object... args) {
        if(devMode) {
            System.out.println("[SQL Query] " + sql);
        }
    }

    public Sql genSql() {
        return new Sql(conn);
    }

    // prst.close() -> conn.close() 순서 중요 !
    public void close() {
        try {
            if (prst != null) {
                prst.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTransaction() {
    }

    public void rollback() {
    }

    public void commit() {
    }
}
