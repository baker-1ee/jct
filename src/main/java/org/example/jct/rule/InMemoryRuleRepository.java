package org.example.jct.rule;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryRuleRepository implements RuleRepository {

    private final Map<String, Rule> keywordMap = new LinkedHashMap<>();

    @Override
    public void saveAll(List<? extends Rule> rules) {
        rules.forEach(keyword -> keywordMap.put(keyword.getOracle().toUpperCase(), keyword));
    }

    @Override
    public List<Rule> findAll() {
        return new ArrayList<>(keywordMap.values());
    }

    @Override
    public Optional<Rule> findByOracleKeyword(String oracleKeyword) {
        return Optional.ofNullable(keywordMap.get(oracleKeyword.toUpperCase()));
    }
}
