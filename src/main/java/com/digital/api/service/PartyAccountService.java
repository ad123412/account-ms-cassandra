package com.digital.api.service;

import com.digital.api.model.PartyAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PartyAccountService {

    public Mono<PartyAccount> getPartyAccountById(String id);

    public Flux<PartyAccount> getAllPartyAccounts();
}
