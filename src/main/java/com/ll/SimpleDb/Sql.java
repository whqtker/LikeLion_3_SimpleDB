package com.ll.SimpleDb;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sql {
    private final Connection conn;
    private final StringBuilder query; // query: SQL 쿼리
    private final List<Object> params; // params: SQL 쿼리의 파라미터

    // 생성자, Connection 객체를 받아와서 conn에 저장, query와 params 초기화
    public Sql(Connection conn) {
        this.conn = conn;
        this.query = new StringBuilder();
        this.params = new ArrayList<>();
    }

    // append(): SQL 쿼리에 sqlParts와 args를 추가, 메서드 체이닝 사용
    // sqlParts: SQL 쿼리의 일부분
    // args: SQL 쿼리의 파라미터
    public Sql append(String sqlParts, Object... args) {
        // sqlParts를 query에 추가
        query.append(sqlParts).append(" ");

        // args를 params에 추가
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

    // selectRows(): SELECT 쿼리를 실행하고 결과를 List 형태로 리턴
    public List<Map<String, Object>> selectRows() {
        List<Map<String, Object>> rows = new ArrayList<>();

        try (PreparedStatement prst = conn.prepareStatement(query.toString())) {
            setParameters(prst);

            try (ResultSet rs = prst.executeQuery()) {
                // ResultSetMetaData: ResultSet의 메타데이터를 가져오는 인터페이스
                // getMetaData(): ResultSet의 메타데이터를 가져옴
                ResultSetMetaData rsmd = rs.getMetaData();

                // getColumnCount(): ResultSet의 열 수를 반환
                int columnCount = rsmd.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>(); // 각 행을 저장하기 위한 객체

                    // 각 열을 순회하며 열의 이름과 데이터를 가져온 후, row에 저장
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(rsmd.getColumnName(i), rs.getObject(i));
                    }
                    rows.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    public Map<String, Object> selectRow() {
        List<Map<String, Object>> rows = selectRows(); // selectRow() 재활용
        if (rows.isEmpty()) {
            return new HashMap<>();
        }
        return rows.get(0);
    }

    public LocalDateTime selectDatetime() {
        try (PreparedStatement prst = conn.prepareStatement(query.toString())) {
            setParameters(prst);
            try (ResultSet rs = prst.executeQuery()) {
                if (rs.next()) {
                    // 첫 번째 열의 값을 Timestamp 타입으로 반환한 후 LocalDateTime으로 변환
                    return rs.getTimestamp(1).toLocalDateTime();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Long selectLong() {
        try (PreparedStatement prst = conn.prepareStatement(query.toString())) {
            setParameters(prst);
            try (ResultSet rs = prst.executeQuery()) {
                if (rs.next()) {
                    // 첫 번째 열의 값을 Timestamp 타입으로 반환한 후 LocalDateTime으로 변환
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1L;
    }

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
