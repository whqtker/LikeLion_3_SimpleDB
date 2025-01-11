package com.ll.SimpleDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

//    public long insert() {
//    }
//
//    public int update() {
//    }
//
//    public int delete() {
//    }
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
}
