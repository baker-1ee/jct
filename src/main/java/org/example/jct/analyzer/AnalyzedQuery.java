package org.example.jct.analyzer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.jct.parser.ParsedQuery;
import org.example.jct.rule.Rule;

import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
@Builder
public class AnalyzedQuery {
    private ParsedQuery query;
    private Set<Rule> rules;
    private final String TAB = "\t\t\t";
    private final String ENTER = "\n";

    public static AnalyzedQuery of(ParsedQuery query, Set<Rule> rules) {
        return AnalyzedQuery.builder()
                .query(query)
                .rules(rules)
                .build();
    }

    public ParsedQuery getKey() {
        return query;
    }

    public String getFilePath() {
        return query.getFilePath();
    }

    public String getQueryId() {
        return query.getQueryId();
    }

    public String getSql() {
        return query.getSql();
    }

    public String getRules() {
        return rules.stream()
                .map(Rule::getFrom)
                .collect(Collectors.joining(", "));
    }

    public Boolean isAbleAutoConversion() {
        return rules.stream()
                .allMatch(Rule::isAvailAutoConversion);
    }

    public String getGuidelines() {
        return rules.stream()
                .filter(Rule::needToNotify)
                .map(Rule::getNotice)
                .collect(Collectors.joining("\n"));
    }

    public String convert() {
        String sql = applyRules(query.getSql());
        return addSqlComment(sql);
    }

    private String applyRules(String sql) {
        for (Rule rule : rules) {
            if (rule.isAvailAutoConversion()) {
                // 대소문자 구분 없이 전체 단어 일치
                sql = sql.replaceAll("(?i)\\b" + rule.getFrom() + "\\b", rule.getTo());
            }
        }
        return sql;
    }

    private String addSqlComment(String sql) {
        // 이미 변환 완료건은 그대로 반환
        if (sql.contains("/** [JCT-AUTO-CONVERSION-SUCCESS] **/")) {
            return sql;
        } else {
            if (isAbleAutoConversion()) {
                return "/** [JCT-AUTO-CONVERSION-SUCCESS] **/" + ENTER + TAB + sql;
            } else {
                String originSql = sql.replaceAll("(?s)/\\*\\* \\[JCT-AUTO-CONVERSION-FAIL\\].*?\\*/\\s*", "");
                StringBuilder comment = new StringBuilder(ENTER)
                        .append(TAB).append("/** [JCT-AUTO-CONVERSION-FAIL]").append(ENTER);
                rules.stream()
                        .filter(Rule::needToNotify)
                        .forEach(rule -> comment.append(TAB).append(rule.getNotice()).append(ENTER));
                comment.append(TAB).append("**/").append(ENTER);
                return comment.append(TAB).append(originSql).toString();
            }
        }
    }
}
