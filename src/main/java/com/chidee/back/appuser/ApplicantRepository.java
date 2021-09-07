package com.chidee.back.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<AppUser> findByEmail(String email);
    long countByAppUserRole(AppUserRole AppUserRole);

    void deleteAllById(Long id);
}
