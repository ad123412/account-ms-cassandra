package com.digital.api.service;

import com.digital.api.model.FinancialAccount;
import com.digital.api.repository.FinancialAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FinancialAccountServiceImpl implements FinancialAccountService {

    @Autowired
    private FinancialAccountRepository financialAccountRepository;

    @Override
    public Mono<FinancialAccount> getFinancialAccountById(String id) {
        return financialAccountRepository.findById(id);
    }

    @Override
    public Flux<FinancialAccount> getAllFinancialAccount() {
        return financialAccountRepository.findAll();
    }

}
