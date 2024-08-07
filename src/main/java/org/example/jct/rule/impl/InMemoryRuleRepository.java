package org.example.jct.rule.impl;

import org.example.jct.rule.Rule;
import org.example.jct.rule.RuleRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryRuleRepository implements RuleRepository {

    private final Map<String, Rule> fromRuleMap = new LinkedHashMap<>();
    private final Map<String, Rule> toRuleMap = new LinkedHashMap<>();

    @Override
    public void saveAll(List<? extends Rule> rules) {
        rules.forEach(rule -> fromRuleMap.put(rule.getFrom().toUpperCase(), rule));
        rules.forEach(rule -> toRuleMap.put(rule.getTo().toUpperCase(), rule));
    }

    @Override
    public List<Rule> findAll() {
        return new ArrayList<>(fromRuleMap.values());
    }

    @Override
    public Optional<Rule> findByFrom(String from) {
        return Optional.ofNullable(fromRuleMap.get(from.toUpperCase()));
    }

    @Override
    public Optional<Rule> findByTo(String to) {
        return Optional.ofNullable(toRuleMap.get(to.toUpperCase()));
    }
}
