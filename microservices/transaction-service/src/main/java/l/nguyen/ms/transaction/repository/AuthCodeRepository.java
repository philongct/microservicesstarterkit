package l.nguyen.ms.transaction.repository;

import java.util.Date;
import java.util.List;

import l.nguyen.ms.common.model.transaction.GeneratedAuthCode;
import lnguyen.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthCodeRepository extends BaseRepository<GeneratedAuthCode, Long> {
    @Query("SELECT DISTINCT requestBank from GeneratedAuthCode t WHERE t.generatedDt BETWEEN ?1 AND ?2")
    List<String> findAllRequestedBanks(Date from, Date to);
}
