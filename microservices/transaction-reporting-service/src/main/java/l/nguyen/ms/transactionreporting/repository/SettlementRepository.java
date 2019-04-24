package l.nguyen.ms.transactionreporting.repository;

import org.springframework.stereotype.Repository;

import l.nguyen.ms.transactionreporting.model.SettlementBalance;
import lnguyen.jpa.BaseRepository;

@Repository
public interface SettlementRepository extends BaseRepository<SettlementBalance, Long> {
}
