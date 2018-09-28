package com.digital.api.service;

import com.digital.api.model.AccountTaxExemption;
import com.digital.api.model.FinancialAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FinancialAccountService {

    public Mono<FinancialAccount> getFinancialAccountById(String id);

    public Flux<FinancialAccount> getAllFinancialAccount();
}
