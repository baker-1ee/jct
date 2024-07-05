package org.example.jct.rule.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.jct.rule.Rule;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnknownRule implements Rule {

    private String unknownKeyword;

    public static Rule of(String unknownKeyword) {
        return UnknownRule.builder().unknownKeyword(unknownKeyword).build();
    }

    @Override
    public String getFrom() {
        return unknownKeyword;
    }

    @Override
    public String getTo() {
        return null;
    }

    @Override
    public Boolean isAvailAutoConversion() {
        return false;
    }

    @Override
    public String getNotice() {
        return String.format("[%s] %s", unknownKeyword, "등록되지 않은 키워드이며, 확인 후 룰 셋팅 해주세요.");
    }

    @Override
    public Boolean needToNotify() {
        return true;
    }


}
