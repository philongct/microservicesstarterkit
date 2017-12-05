package l.nguyen.ms.settlement.repository;

import java.util.Date;

import l.nguyen.ms.settlement.model.SettlementBalance;
import lnguyen.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends BaseRepository<SettlementBalance, Long> {
}
