package com.ll.SimpleDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sql {
    private final Connection conn;
    private final StringBuilder query;
    private final List<Object> params;

    public Sql(Connection conn) {
        this.conn = conn;
        this.query = new StringBuilder();
        this.params = new ArrayList<>();
    }

    public Sql append(String sqlParts, Object... args) {
        query.append(sqlParts).append(" ");
        for (Object arg : args) {
            params.add(arg);
        }

        return this;
    }

    public long insert() {
        try {
            // PreparedStatement: SQL 쿼리를 실행하기 위한 객체
            // RETURN_GENERATED_KEYS: 자동으로 생성된 키를 반환하도록 지정하는 상수
            PreparedStatement prst = conn.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            setParameters(prst); // setParameters(): PreparedStatement에 파라미터 설정
            prst.executeUpdate(); // executeUpdate(): SQL 쿼리 실행

            // getGeneratedKeys(): 자동으로 생성된 키를 반환
            try (ResultSet rs = prst.getGeneratedKeys()) {
                // ResultSet.next(): 다음 행으로 이동
                // 다음 행이 존재한다는 것은 성공적으로 데이터가 추가되었음을 의미함
                if (rs.next()) {
                    // ResultSet.getLong(1): 첫 번째 열(id)의 값을 long 타입으로 반환
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int update() {
        try {
            PreparedStatement prst = conn.prepareStatement(query.toString());
            setParameters(prst);
            return prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int delete() {
        try {
            PreparedStatement prst = conn.prepareStatement(query.toString());
            setParameters(prst);
            return prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
//
//    public List<Map<String, Object>> selectRows() {
//    }
//
//    public Map<String, Object> selectRow() {
//    }
//
//    public LocalDateTime selectDatetime() {
//    }
//
//    public Long selectLong() {
//    }
//
//    public String selectString() {
//    }
//
//    public Boolean selectBoolean() {
//    }
//
//    public List<Long> selectLongs() {
//    }

    // setParameters(): PreparedStatement에 파라미터 설정
    private void setParameters(PreparedStatement prst) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            prst.setObject(i + 1, params.get(i));
        }
    }
}
