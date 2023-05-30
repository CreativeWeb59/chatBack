package fr.maurer.chatBack.repository;

import fr.maurer.chatBack.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserNameAndPassword(String userName, String password);
}
