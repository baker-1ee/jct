package org.example.jct.rule.impl;

import org.example.jct.rule.Rule;
import org.example.jct.rule.RuleRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryRuleRepository implements RuleRepository {

    private final Map<String, Rule> ruleMap = new LinkedHashMap<>();

    @Override
    public void saveAll(List<? extends Rule> rules) {
        rules.forEach(rule -> ruleMap.put(rule.getFrom().toUpperCase(), rule));
    }

    @Override
    public List<Rule> findAll() {
        return new ArrayList<>(ruleMap.values());
    }

    @Override
    public Optional<Rule> findByFrom(String from) {
        return Optional.ofNullable(ruleMap.get(from.toUpperCase()));
    }
}
