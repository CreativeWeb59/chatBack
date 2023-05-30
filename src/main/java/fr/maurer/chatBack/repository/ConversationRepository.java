package fr.maurer.chatBack.repository;

import fr.maurer.chatBack.model.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {
    // ConversationEntity findConversationEntityByUserEntity1OrderByDate(Long user1_id);

    // liste et détail des conversations pour un id
    List<ConversationEntity> findByUserEntity1Id(Long user1_id);
    List<ConversationEntity> findByUserEntity2Id(Long user2_id);

    // lite des conversations pour un id avec tous les autres
    // @Query("SELECT c FROM ConversationEntity c WHERE c.userEntity1.id = :user1Id GROUP BY c.userEntity2")
    //@Query("SELECT c FROM ConversationEntity c WHERE c.userEntity1.id = :user1Id OR c.userEntity2.id = :user1Id")
    @Query("SELECT c FROM ConversationEntity c WHERE c.userEntity1.id = :user1Id OR c.userEntity2.id = :user1Id GROUP BY c.userEntity1.id, c.userEntity2.id")
    List<ConversationEntity> findConversationGroupByUser2(Long user1Id);

    // recherche toutes les conversations avec deux id données
    // verifie chaque id dans les deux colones user_id
    @Query("SELECT c FROM ConversationEntity c WHERE (c.userEntity1.id = :user1Id OR c.userEntity2.id = :user1Id) AND (c.userEntity1.id = :user2Id OR c.userEntity2.id = :user2Id) ORDER BY c.date")
    List<ConversationEntity> findByUserEntity1IdAndUserEntity2Id(Long user1Id, Long user2Id);

}
