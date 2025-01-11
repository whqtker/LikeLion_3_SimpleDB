package com.ll.SimpleDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimpleDb {
    Connection conn = null;
    private boolean devMode = false;

    // 생성자, DB 연결
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

    // setDevMode(): devMode 설정
    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    // run(): SQL 쿼리 실행
    public void run(String sql, Object... args) {
        if(devMode) {
            System.out.println("[SQL Query] " + sql);
        }

        // prst에 파라미터 설정 후 실행
        try (PreparedStatement prst = conn.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                prst.setObject(i + 1, args[i]);
            }
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // genSql(): Sql 객체 생성
    public Sql genSql() {
        return new Sql(conn);
    }

    // close(): DB 연결 종료
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // startTransaction(): 트랜잭션 시작
    // setAutoCommit()으로 트랜잭션 관련 작업을 처리한다.
    // setAutoCommit(false): 자동 커밋을 비활성화 -> 개발자가 수동으로 commit, rollback을 해야 함
    public void startTransaction() {
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // rollback(): 트랜잭션 롤백
    public void rollback() {
        try {
            conn.rollback();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // commit(): 트랜잭션 커밋
    public void commit() {
        try {
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
