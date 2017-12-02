package l.nguyen.ms.transaction.repository;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import lnguyen.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<CreditCardTransaction, Long> {
}
