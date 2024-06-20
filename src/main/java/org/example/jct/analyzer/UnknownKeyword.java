package org.example.jct.analyzer;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class UnknownKeyword implements Keyword {

    private final String oracle;
    private final String mysql;
    private final boolean availAutoConversion;
    private final String notice;

    public static Keyword from(String oracleKeyword) {
        return UnknownKeyword.builder()
                .oracle(oracleKeyword)
                .availAutoConversion(false)
                .notice("등록되지 않은 키워드이며, 확인 후 룰 셋팅 해주세요.")
                .build();
    }

    @Override
    public boolean needToNotify() {
        return !notice.isEmpty();
    }

    @Override
    public String getGuideline() {
        return String.format("[%s] %s", oracle, notice);
    }
}
