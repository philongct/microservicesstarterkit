package l.nguyen.ms.transaction.repository;

import java.util.Date;
import java.util.List;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import lnguyen.jpa.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<CreditCardTransaction, Long> {
//    @Query("select t1 from CreditCardTransaction t1 join fetch t1.authCode"
//            + " where t1.authCode.requestBank=?"
//            + " and t1.authCode.generatedDt between ? and ?")
//    public List<CreditCardTransaction> findAllByBankAndDate(String bankId, Date from);
}
