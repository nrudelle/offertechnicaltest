package fr.nrude.offertechnicaltest.dao.repository;

import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Query("SELECT u FROM UserAccount u WHERE TRIM(u.userName) = TRIM(:username)")
    Optional<UserAccount> getByUsername(String username);
}
