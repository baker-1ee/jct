package org.example.jct.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnknownKeyword implements Rule {

    private String oracle;
    private String mysql;
    private boolean availAutoConversion;
    private String notice;

    public static Rule from(String oracleKeyword) {
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
    public String getNotice() {
        return String.format("[%s] %s", oracle, notice);
    }


}
