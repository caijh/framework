package com.github.caijh.framework.core.lock;

import java.util.ArrayList;
import java.util.List;

import com.github.caijh.framework.core.lock.annotation.EnableLocking;
import com.github.caijh.framework.core.lock.config.LockingConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class LockingConfigurationSelector extends AdviceModeImportSelector<EnableLocking> {
    @Nullable
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return switch (adviceMode) {
            case PROXY -> getProxyImports();
            case ASPECTJ -> getAspectJImports();
        };
    }

    private String[] getAspectJImports() {
        return new String[0];
    }

    private String[] getProxyImports() {
        List<String> result = new ArrayList<>(3);
        result.add(LockingConfiguration.class.getName());
        return StringUtils.toStringArray(result);
    }
}
