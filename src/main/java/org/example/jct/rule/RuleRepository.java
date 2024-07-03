package org.example.jct.rule;

import java.util.List;
import java.util.Optional;

public interface RuleRepository {

    void saveAll(List<? extends Rule> keywords);

    List<Rule> findAll();

    Optional<Rule> findByOracleKeyword(String oracleKeyword);

}
