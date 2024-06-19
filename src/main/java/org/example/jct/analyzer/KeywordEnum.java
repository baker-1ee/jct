package org.example.jct.analyzer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
@Getter
public enum KeywordEnum {
    SELECT("SELECT", "SELECT"),
    INSERT("INSERT", "INSERT"),
    UPDATE("UPDATE", "UPDATE"),
    DELETE("DELETE", "DELETE"),
    FROM("FROM", "FROM"),
    WHERE("WHERE", "WHERE"),
    JOIN("JOIN", "JOIN"),
    LEFT("LEFT", "LEFT"),
    RIGHT("RIGHT", "RIGHT"),
    INNER("INNER", "INNER"),
    OUTER("OUTER", "OUTER"),
    GROUP_BY("GROUP BY", "GROUP BY"),
    ORDER_BY("ORDER BY", "ORDER BY"),
    HAVING("HAVING", "HAVING"),
    UNION("UNION", "UNION"),
    EXISTS("EXISTS", "EXISTS"),
    IN("IN", "IN"),
    AS("AS", "AS"),
    ON("ON", "ON"),
    AND("AND", "AND"),
    OR("OR", "OR"),
    NOT("NOT", "NOT"),
    LIKE("LIKE", "LIKE"),
    IS_NULL("IS NULL", "IS NULL"),
    IS_NOT_NULL("IS NOT NULL", "IS NOT NULL"),
    NVL("NVL", "IFNULL"),
    SYSDATE("SYSDATE", "CURRENT_DATE"),
    SYSTIMESTAMP("SYSTIMESTAMP", "CURRENT_TIMESTAMP"),
    ANY("ANY", "ANY"),
    BY("BY", "BY"),
    CASE("CASE", "CASE"),
    DATE("DATE", "DATE"),
    DECIMAL("DECIMAL", "DECIMAL"),
    DEFAULT("DEFAULT", "DEFAULT"),
    DESC("DESC", "DESC"),
    DISTINCT("DISTINCT", "DISTINCT"),
    ELSE("ELSE", "ELSE"),
    FLOAT("FLOAT", "FLOAT"),
    FOR("FOR", "FOR"),
    INTEGER("INTEGER", "INTEGER"),
    INTO("INTO", "INTO"),
    LONG("LONG", "LONG"),
    NULL("NULL", "NULL"),
    NUMBER("NUMBER", "NUMBER"),
    OF("OF", "OF"),
    SET("SET", "SET"),
    THEN("THEN", "THEN"),
    VALUES("VALUES", "VALUES"),
    VARCHAR("VARCHAR", "VARCHAR", false, "Characterset 확인하여 한글의 바이트 수 확인 필요합니다."),
    VARCHAR2("VARCHAR2", "VARCHAR2", false, "Characterset 확인하여 한글의 바이트 수 확인 필요합니다."),
    WITH("WITH", "WITH", false, "수작업 필요합니다."),
    // Functions
    ABS("ABS", "ABS"),
    CEIL("CEIL", "CEIL"),
    FLOOR("FLOOR", "FLOOR"),
    MOD("MOD", "MOD"),
    POWER("POWER", "POWER"),
    ROUND("ROUND", "ROUND"),
    CONCAT("CONCAT", "CONCAT"),
    INSTR("INSTR", "INSTR"),
    LENGTH("LENGTH", "LENGTH"),
    LOWER("LOWER", "LOWER"),
    LPAD("LPAD", "LPAD"),
    LTRIM("LTRIM", "LTRIM"),
    REPLACE("REPLACE", "REPLACE"),
    RPAD("RPAD", "RPAD"),
    RTRIM("RTRIM", "RTRIM"),
    SUBSTR("SUBSTR", "SUBSTR"),
    UPPER("UPPER", "UPPER"),
    ADD_MONTHS("ADD_MONTHS", "DATE_ADD"),
    CURRENT_DATE("CURRENT_DATE", "CURRENT_DATE"),
    CURRENT_TIMESTAMP("CURRENT_TIMESTAMP", "CURRENT_TIMESTAMP"),
    CAST("CAST", "CAST"),
    CONVERT("CONVERT", "CONVERT"),
    TO_CHAR("TO_CHAR", "CAST"),
    TO_DATE("TO_DATE", "STR_TO_DATE"),
    TO_NUMBER("TO_NUMBER", "CAST"),
    TO_TIMESTAMP("TO_TIMESTAMP", "STR_TO_DATE"),
    AVG("AVG", "AVG"),
    COUNT("COUNT", "COUNT"),
    MAX("MAX", "MAX"),
    MIN("MIN", "MIN"),
    SUM("SUM", "SUM"),
    COALESCE("COALESCE", "COALESCE"),
    DECODE("DECODE", "IF"),
    GREATEST("GREATEST", "GREATEST"),
    LEAST("LEAST", "LEAST"),
    NVL2("NVL2", "IF", false, "NVL2(expr1, expr2, expr3)를 IF(expr1 IS NOT NULL, expr2, expr3)로 변환합니다."),
    // 의문의 키워드
    MYSTERIOUS_KEYWORD("의문의 키워드", "확인필요", false, "의문의 키워드가 추출되었습니다. 확인이 필요합니다.");

    private final String oracle;
    private final String mysql;
    private final boolean availAutoConversion; // 자동 변환 가능 여부
    private final String guideline; // 안내사항

    KeywordEnum(String oracle, String mysql) {
        this(oracle, mysql, true, "");
    }

    private static final Map<String, KeywordEnum> ORACLE_TO_ENUM_MAP = Arrays.stream(values())
            .collect(toMap(KeywordEnum::getOracle, t -> t));

    public static KeywordEnum findByOracleKeyword(String oracleKeyword) {
        return ORACLE_TO_ENUM_MAP.getOrDefault(oracleKeyword, MYSTERIOUS_KEYWORD);
    }

    public static final Pattern ORACLE_PATTERN = Pattern.compile(
            "\\b(" +
                    Arrays.stream(KeywordEnum.values())
                            .map(KeywordEnum::getOracle)
                            .collect(joining("|"))
                    + ")\\b",
            Pattern.CASE_INSENSITIVE
    );

}
