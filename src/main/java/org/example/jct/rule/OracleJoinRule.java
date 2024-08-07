package org.example.jct.rule;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OracleJoinRule {

    private static String convert(String oracleJoinSql) {

        String oracleJoinSql1 = "select * from team t, member m where t.team_id = m.team_id";
        String oracleJoinSql2 = "select * from team t, member m where t.team_id = m.team_id and t.team_no = m.team_no and m.del_yn = 'N'";
        String oracleJoinSql3 = "select m.* from team t, member m, dept d where t.team_id = m.team_id and t.team_no = m.team_no and m.del_yn = 'N' and t.dept_id = d.dept_id";

        // 정규 표현식을 사용하여 FROM 절과 WHERE 절을 분리
        Pattern fromPattern = Pattern.compile("from\\s+(.*?)\\s+where\\s+(.*)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher fromMatcher = fromPattern.matcher(oracleJoinSql);

        if (!fromMatcher.find()) {
            return oracleJoinSql; // 변환할 필요가 없으면 원본 SQL 반환
        }

        String fromPart = fromMatcher.group(1).trim();
        String wherePart = fromMatcher.group(2).trim();

        // 테이블과 별칭 추출
        Map<String, String> tableAliasMap = new HashMap<>();
        String[] tables = fromPart.split(",\\s*");
        for (String table : tables) {
            String[] parts = table.trim().split("\\s+");
            if (parts.length == 2) {
                tableAliasMap.put(parts[1].trim(), parts[0].trim());
            } else {
                tableAliasMap.put(parts[0].trim(), parts[0].trim());
            }
        }

        // WHERE 절에서 조인 조건과 필터 조건을 분리
        Pattern joinPattern = Pattern.compile("(\\w+\\.\\w+\\s*=\\s*\\w+\\.\\w+)");
        Matcher joinMatcher = joinPattern.matcher(wherePart);

        List<String> joinConditions = new ArrayList<>();
        List<String> filterConditions = new ArrayList<>();

        while (joinMatcher.find()) {
            joinConditions.add(joinMatcher.group());
        }

        // 조인 조건을 제거한 나머지 WHERE 절은 필터 조건으로 간주
        String remainingWhere = wherePart.replaceAll(joinPattern.pattern(), "").trim();
        if (!remainingWhere.isEmpty()) {
            for (String condition : remainingWhere.split("and")) {
                String trimmedCondition = condition.trim();
                if (!trimmedCondition.isEmpty()) {
                    filterConditions.add(trimmedCondition);
                }
            }
        }

        // 테이블 간의 조인 조건을 그룹화
        Map<String, List<String>> joinMap = new LinkedHashMap<>();
        for (String condition : joinConditions) {
            String[] parts = condition.split("=");
            String leftTable = parts[0].split("\\.")[0].trim();
            String rightTable = parts[1].split("\\.")[0].trim();

            String key = leftTable + "-" + rightTable;
            joinMap.computeIfAbsent(key, k -> new ArrayList<>()).add(condition);
        }

        // ANSI 조인 구문 구성
        StringBuilder ansiJoinSql = new StringBuilder("select * from ");
        String baseTable = tables[0].trim();
        ansiJoinSql.append(baseTable);

        Set<String> joinedTables = new HashSet<>();
        joinedTables.add(baseTable.split("\\s+")[1].trim()); // 첫 번째 테이블의 별칭 추가

        for (Map.Entry<String, List<String>> entry : joinMap.entrySet()) {
            String[] tableAliases = entry.getKey().split("-");
            String leftAlias = tableAliases[0].trim();
            String rightAlias = tableAliases[1].trim();

            if (!joinedTables.contains(rightAlias)) {
                ansiJoinSql.append(" inner join ")
                        .append(tableAliasMap.get(rightAlias)).append(" ").append(rightAlias)
                        .append(" on (")
                        .append(String.join(" and ", entry.getValue()))
                        .append(")");
                joinedTables.add(rightAlias);
            }
        }

        if (!filterConditions.isEmpty()) {
            ansiJoinSql.append(" where ").append(String.join(" and ", filterConditions));
        }

        // 불필요한 "where  and" 제거
        String resultSql = ansiJoinSql.toString().replaceAll("\\s+where\\s+and", " where").replaceAll("\\s+where\\s+$", "");

        return resultSql;
    }

}
