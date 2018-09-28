package com.digital.api.service;

import com.digital.api.model.SettlementAccount;
import com.digital.api.repository.SettlementAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SettlementAccountServiceImpl implements SettlementAccountService {

    @Autowired
    private SettlementAccountRepository settlementAccountRepository;

    @Override
    public Mono<SettlementAccount> getSettlementAccountById(String id) {
        return settlementAccountRepository.findById(id);
    }

    @Override
    public Flux<SettlementAccount> getAllSettlementAccounts() {
        return settlementAccountRepository.findAll();
    }
}
