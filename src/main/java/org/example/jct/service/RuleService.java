package org.example.jct.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jct.rule.Rule;
import org.example.jct.rule.RuleRepository;
import org.example.jct.rule.impl.UnknownRule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleService {

    private final RuleRepository ruleRepository;
    private final ObjectMapper objectMapper;

    public void initialize(String ruleFilePath) {
        try {
            List<Rule> rules = objectMapper.readValue(new File(ruleFilePath), new TypeReference<List<Rule>>() {
            });
            ruleRepository.saveAll(rules);
        } catch (Exception e) {
            log.error("Failed to load rule from file", e);
        }
    }

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Pattern getPattern() {
        return Pattern.compile(
                "\\b(" + findAll().stream().map(Rule::getFrom).collect(joining("|")) + ")\\b",
                Pattern.CASE_INSENSITIVE
        );
    }

    public Rule findByFrom(String from) {
        return ruleRepository.findByFrom(from)
                .orElseGet(() -> UnknownRule.of(from));
    }
}
