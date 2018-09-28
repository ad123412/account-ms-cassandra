package com.digital.api.service;

import com.digital.api.model.SettlementAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SettlementAccountService {

    public Mono<SettlementAccount> getSettlementAccountById(String id);

    public Flux<SettlementAccount> getAllSettlementAccounts();
}
