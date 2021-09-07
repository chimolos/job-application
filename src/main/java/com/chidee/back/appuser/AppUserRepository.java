package com.chidee.back.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
//    @Query("SELECT u FROM AppUser u WHERE u.email = ?1")
    Optional<AppUser> findByEmail(String email);
//    AppUser findAppUserById(Long id);
//    List<AppUser> findAllByEnabledFalse();
//    List<AppUser> findAllByAppUserRole(AppUserRole AppUserRole);
    long countByAppUserRole(AppUserRole AppUserRole);

//    String deleteAllById(Long id);
//    Boolean existsByEmail(String email);
}
