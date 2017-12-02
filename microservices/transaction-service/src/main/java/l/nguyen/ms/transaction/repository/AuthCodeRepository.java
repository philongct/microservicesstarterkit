package l.nguyen.ms.transaction.repository;

import java.util.List;

import l.nguyen.ms.common.model.transaction.GeneratedAuthCode;
import lnguyen.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthCodeRepository extends BaseRepository<GeneratedAuthCode, Long> {
    List<GeneratedAuthCode> findAllByRequestBankAndAuthCodeBetween(String requestBank, int from, int to);
}
