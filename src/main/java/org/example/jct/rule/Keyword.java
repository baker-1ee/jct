package org.example.jct.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keyword implements Rule {

    private String oracle;
    private String mysql;
    private boolean availAutoConversion;
    private String notice;

    @Override
    public boolean needToNotify() {
        return !notice.isEmpty();
    }

    @Override
    public String getNotice() {
        return String.format("[%s] %s", oracle, notice);
    }

}
