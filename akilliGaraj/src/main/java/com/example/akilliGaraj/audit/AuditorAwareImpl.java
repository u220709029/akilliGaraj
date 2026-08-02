package com.example.akilliGaraj.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // İleride buraya Spring Security eklenecek:
        // return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
        return Optional.of("SYSTEM");
    }
}