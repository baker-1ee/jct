package org.example.jct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jct.rule.Keyword;
import org.example.jct.rule.Rule;
import org.example.jct.rule.RuleRepository;
import org.example.jct.rule.UnknownKeyword;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleService {

    private final RuleRepository ruleRepository;

    public void initialize(String rulePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Keyword> rules = mapper.readValue(new File(rulePath), new TypeReference<List<Keyword>>() {
            });
            ruleRepository.saveAll(rules);
        } catch (IOException e) {
            log.error("Failed to load rule from file", e);
        }
    }

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Pattern getOraclePattern() {
        return Pattern.compile(
                "\\b(" + findAll().stream()
                        .map(Rule::getOracle)
                        .collect(joining("|")) + ")\\b",
                Pattern.CASE_INSENSITIVE);
    }

    /**
     * 사전에 등록되지 않은 키워드는 UnknownKeyword 로 생성
     */
    public Rule findByOracleKeyword(String oracleKeyword) {
        return ruleRepository.findByOracleKeyword(oracleKeyword)
                .orElseGet(() -> UnknownKeyword.from(oracleKeyword));
    }
}
