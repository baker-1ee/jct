package org.example.jct.rule.impl;

import lombok.*;
import org.example.jct.rule.Rule;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
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
