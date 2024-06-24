package org.example.jct.analyzer;

import com.sun.tools.javac.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
@Getter
public enum KeywordEnum implements Keyword {
    NVL("NVL", "IFNULL", true, "[자동 변환 가능] NVL -> IFNULL"),
    NVL2("NVL2", "IF", false, "NVL2(expr1, expr2, expr3)는 IF(expr1 IS NOT NULL, expr2, expr3)로 변환할 수 있습니다."),
    SYSDATE("SYSDATE", "NOW()", true, "[자동 변환 가능] SYSDATE -> NOW()"),

    SELECT("SELECT", "SELECT", true, ""),
    INSERT("INSERT", "INSERT", true, ""),
    UPDATE("UPDATE", "UPDATE", true, ""),
    DELETE("DELETE", "DELETE", true, ""),
    FROM("FROM", "FROM", true, ""),
    WHERE("WHERE", "WHERE", true, ""),
    JOIN("JOIN", "JOIN", true, ""),
    LEFT("LEFT", "LEFT", true, ""),
    RIGHT("RIGHT", "RIGHT", true, ""),
    INNER("INNER", "INNER", true, ""),
    OUTER("OUTER", "OUTER", true, ""),
    GROUP_BY("GROUP BY", "GROUP BY", true, ""),
    ORDER_BY("ORDER BY", "ORDER BY", true, ""),
    HAVING("HAVING", "HAVING", true, ""),
    UNION("UNION", "UNION", true, ""),
    EXISTS("EXISTS", "EXISTS", true, ""),
    IN("IN", "IN", true, ""),
    AS("AS", "AS", true, ""),
    ON("ON", "ON", true, ""),
    AND("AND", "AND", true, ""),
    OR("OR", "OR", true, ""),
    NOT("NOT", "NOT", true, ""),
    LIKE("LIKE", "LIKE", true, ""),
    IS_NULL("IS NULL", "IS NULL", true, ""),
    IS_NOT_NULL("IS NOT NULL", "IS NOT NULL", true, ""),
    SYSTIMESTAMP("SYSTIMESTAMP", "CURRENT_TIMESTAMP", false, "확인중"),
    ANY("ANY", "ANY", true, ""),
    BY("BY", "BY", true, ""),
    CASE("CASE", "CASE", true, ""),
    DATE("DATE", "DATE", true, ""),
    DECIMAL("DECIMAL", "DECIMAL", true, ""),
    DEFAULT("DEFAULT", "DEFAULT", true, ""),
    DESC("DESC", "DESC", true, ""),
    DISTINCT("DISTINCT", "DISTINCT", true, ""),
    ELSE("ELSE", "ELSE", true, ""),
    FLOAT("FLOAT", "FLOAT", true, ""),
    FOR("FOR", "FOR", true, ""),
    INTEGER("INTEGER", "INTEGER", true, ""),
    INTO("INTO", "INTO", true, ""),
    LONG("LONG", "LONG", true, ""),
    NULL("NULL", "NULL", true, ""),
    NUMBER("NUMBER", "NUMBER", true, ""),
    OF("OF", "OF", true, ""),
    SET("SET", "SET", true, ""),
    THEN("THEN", "THEN", true, ""),
    VALUES("VALUES", "VALUES", true, ""),
    VARCHAR("VARCHAR", "VARCHAR", false, "Characterset 확인하여 한글의 바이트 수 확인 필요합니다."),
    VARCHAR2("VARCHAR2", "VARCHAR2", false, "Characterset 확인하여 한글의 바이트 수 확인 필요합니다."),
    WITH("WITH", "WITH", false, "전체 쿼리를 확인 후 작업 필요합니다."),
    // Functions
    ABS("ABS", "ABS", true, ""),
    CEIL("CEIL", "CEIL", true, ""),
    FLOOR("FLOOR", "FLOOR", true, ""),
    MOD("MOD", "MOD", true, ""),
    POWER("POWER", "POWER", true, ""),
    ROUND("ROUND", "ROUND", true, ""),
    CONCAT("CONCAT", "CONCAT", true, ""),
    INSTR("INSTR", "INSTR", true, ""),
    LENGTH("LENGTH", "LENGTH", true, ""),
    LOWER("LOWER", "LOWER", true, ""),
    LPAD("LPAD", "LPAD", false, "LPAD(str, len, padstr) 에서 Oracle의 경우에는 len 이 byte 단위이며, MySql 에서는 byte 단위가 아닌 character 단위이므로 characterset 확인 후 작업해주세요."),
    LTRIM("LTRIM", "LTRIM", true, ""),
    REPLACE("REPLACE", "REPLACE", true, ""),
    RPAD("RPAD", "RPAD", false, "RPAD(str, len, padstr) 에서 Oracle의 경우에는 len 이 byte 단위이며, MySql 에서는 byte 단위가 아닌 character 단위이므로 characterset 확인 후 작업해주세요."),
    RTRIM("RTRIM", "RTRIM", true, ""),
    SUBSTR("SUBSTR", "SUBSTR", true, ""),
    UPPER("UPPER", "UPPER", true, ""),
    ADD_MONTHS("ADD_MONTHS", "DATE_ADD", false, "DATE_ADD(연산을 수행할 날짜, INTERVAL n 단위) 형태로 변경해주세요."),
    CURRENT_DATE("CURRENT_DATE", "CURRENT_DATE", true, ""),
    CURRENT_TIMESTAMP("CURRENT_TIMESTAMP", "CURRENT_TIMESTAMP", true, ""),
    CAST("CAST", "CAST", true, ""),
    CONVERT("CONVERT", "CONVERT", true, ""),
    TO_CHAR("TO_CHAR", "CAST", false, "TO_CHAR(value)를 CAST(value AS CHAR)로 변환해야 합니다."),
    TO_DATE("TO_DATE", "STR_TO_DATE", false, "TO_DATE(date_string, format)를 STR_TO_DATE(date_string, format)으로 변환해야 합니다."),
    TO_NUMBER("TO_NUMBER", "CAST", false, "오라클의 TO_NUMBER 함수를 MySQL의 CAST 함수로 변환할 때, TO_NUMBER(expr)를 CAST(expr AS DECIMAL)로 변환하며, 숫자 형식과 소수점 자릿수를 주의해야 합니다."),
    TO_TIMESTAMP("TO_TIMESTAMP", "STR_TO_DATE", true, ""),
    AVG("AVG", "AVG", true, ""),
    COUNT("COUNT", "COUNT", true, ""),
    MAX("MAX", "MAX", true, ""),
    MIN("MIN", "MIN", true, ""),
    SUM("SUM", "SUM", true, ""),
    COALESCE("COALESCE", "COALESCE", true, ""),
    DECODE("DECODE", "IF", false, "오라클의 DECODE 함수는 MySQL의 IF 함수로 변경할 때, DECODE(expr, search, result, default)를 IF(expr = search, result, default)로 변환해야 합니다."),
    GREATEST("GREATEST", "GREATEST", true, ""),
    LEAST("LEAST", "LEAST", true, ""),

    ;

    private final String oracle;
    private final String mysql;
    private final boolean availAutoConversion;
    private final String notice;

    public static final Pattern ORACLE_PATTERN = Pattern.compile(
            "\\b(" +
                    Arrays.stream(KeywordEnum.values())
                            .map(KeywordEnum::getOracle)
                            .collect(joining("|"))
                    + ")\\b",
            Pattern.CASE_INSENSITIVE
    );

    public static Keyword findByOracleKeyword(String oracleKeyword) {
        String upperOracleKeyword = StringUtils.toUpperCase(oracleKeyword);
        return ORACLE_TO_ENUM_MAP.getOrDefault(upperOracleKeyword, UnknownKeyword.from(upperOracleKeyword));
    }

    private static final Map<String, Keyword> ORACLE_TO_ENUM_MAP = Arrays.stream(values())
            .collect(toMap(KeywordEnum::getOracle, t -> t));

    @Override
    public boolean needToNotify() {
        return !notice.isEmpty();
    }

    @Override
    public String getGuideline() {
        return String.format("[%s] %s", oracle, notice);
    }
}
