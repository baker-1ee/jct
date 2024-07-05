package org.example.jct.rule.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.jct.rule.Rule;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MigrationRule implements Rule {

    private String oracle;
    private String mysql;
    private Boolean isAuto;
    private String notice;

    @Override
    public String getFrom() {
        return oracle;
    }

    @Override
    public String getTo() {
        return mysql;
    }

    @Override
    public Boolean isAvailAutoConversion() {
        return isAuto;
    }

    @Override
    public Boolean needToNotify() {
        return !notice.isEmpty();
    }

    @Override
    public String getNotice() {
        return String.format("[%s] %s", oracle, notice);
    }

}
