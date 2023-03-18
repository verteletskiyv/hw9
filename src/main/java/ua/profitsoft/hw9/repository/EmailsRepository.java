package ua.profitsoft.hw9.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.profitsoft.hw9.data.EmailData;
import ua.profitsoft.hw9.data.EmailStatus;
import java.util.List;

@Repository
public interface EmailsRepository extends CrudRepository<EmailData, String> {
    List<EmailData> findAll();
    List<EmailData> findAllByStatus(EmailStatus status);
}
