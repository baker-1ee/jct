package org.example.jct.rule;

import java.util.List;
import java.util.Optional;

public interface RuleRepository {

    void saveAll(List<? extends Rule> rules);

    List<Rule> findAll();

    Optional<Rule> findByFrom(String from);

    Optional<Rule> findByTo(String to);

}
