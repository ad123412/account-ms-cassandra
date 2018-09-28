package com.digital.api.repository;

import com.digital.api.model.FinancialAccount;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialAccountRepository extends ReactiveCassandraRepository<FinancialAccount, String> {
}
