package com.digital.api.repository;

import com.digital.api.model.SettlementAccount;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementAccountRepository extends ReactiveCassandraRepository<SettlementAccount, String> {
}
