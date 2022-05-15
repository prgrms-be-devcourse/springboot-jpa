package com.kdt.springbootjpa.common.entity;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountAuditAware implements AuditorAware<String> {

    private static final String USER_INFO = "TEST";

    @Override
    public Optional<String> getCurrentAuditor() {
        //TODO: spring security 학습후 추가 할것
        return Optional.of(USER_INFO);
    }
}
