package fr.maurer.chatBack.service;

import fr.maurer.chatBack.dto.ConversationRequestDto;
import fr.maurer.chatBack.model.ConversationEntity;
import fr.maurer.chatBack.model.UserEntity;
import fr.maurer.chatBack.repository.ConversationRepository;
import fr.maurer.chatBack.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    // private String laDate = Instant.now() +"";
    // private int inputCount = 0;


    public ConversationService(SimpMessagingTemplate simpMessagingTemplate, ConversationRepository conversationRepository, UserRepository userRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "*/10 * * * * *")
    private void sendMessage() {
        // simpMessagingTemplate.convertAndSend("/notifier/message", new ConversationEntity("Received: %d messages".formatted(this.inputCount), Instant.now()));
        // A modifier les id user et reveiver en dur
        // ainsi que la date en String;
        // voir si ça passe en bdd avec Date => Instant

        Long user1_id = 1L;
        Long user2_id = 2L;
        // String laDate = Instant.now() +"";
        // simpMessagingTemplate.convertAndSend("/notifier/message", new ConversationEntity(user1_id, user2_id, "Received: %d messages".formatted(this.inputCount), laDate));
    }

    public void addMessage(ConversationEntity conversationEntity) {
        // this.inputCount++;
    }

    // Mise en place du Crud
    public ConversationEntity createConversation(ConversationEntity conversationEntity) {
        Long user1_id = 1L;
        Long user2_id = 2L;
        String laDate = Instant.now() +"";
        System.out.println("Je cree un message dans la table");
        // conversationEntity.setDate(this.laDate);
        return conversationRepository.save(conversationEntity);
    }

    /**
     * creer une conversation en utilisant des utilisateurs existants
     * @param conversationEntity
     * @return
     */
    public ConversationEntity createConversationWithUsers(ConversationRequestDto conversationRequestDto) {
        Long user1_id = conversationRequestDto.getUser1_id();
        Long user2_id = conversationRequestDto.getUser2_id();
        String laDate = Instant.now() +"";
        // conversationEntity.setDate(this.laDate);
        System.out.println("Je cree un message dans la table en utilisant les users existants");

        // Vérifie si les utilisateurs avec les IDs spécifiés existent
        UserEntity user1 = userRepository.findById(user1_id).orElse(null);
        UserEntity user2 = userRepository.findById(user2_id).orElse(null);

        // si user1 et 2 user2 pas null
        // on cree une nouvelle conversationEntity
        // sinon renvoi une erreur
        if(user1 != null  && user2 != null){
            ConversationEntity conversation = new ConversationEntity();
            conversation.setMessage(conversationRequestDto.getMessage());
            conversation.setDate(conversationRequestDto.getDate());
            conversation.setUserEntity1(user1);
            conversation.setUserEntity2(user2);
            return conversationRepository.save(conversation);
        } else {
            throw new IllegalArgumentException("L'un ou les deux utilisateurs n'existent pas.");
        }
    }

    /**
     * Liste de toutes les conversations
     * @return
     */
    public List<ConversationEntity> getConversation() {
        return conversationRepository.findAll();
    }

    /**
     * Liste des conversations pour un user
     * @param id
     * @return
     */
    public List<ConversationEntity> getAllByUserId(Long id) {
        return conversationRepository.findByUserEntity1Id(id);
    }

    /**
     * Liste des conversations d'un user groupés par user2
     * @param id
     * @return
     */
    public List<ConversationEntity> getAllByUserIdGroupByUser2(Long id) {
        return conversationRepository.findConversationGroupByUser2(id);
    }

    /**
     * Recupere tous les messages correspondants à l'user1 et l'user2
     * 1re requete sur le user1
     * 2ème requete sur le user2
     * @param id user1_id
     * @param id2 user2_id
     * @return liste des conversations par utilisateur avec un autre utilisateur
     */
    public List<ConversationEntity> getAllConversByid1AndId2(Long id1, Long id2) {
        // cree deux listes pour les ajouter ensemble
        return conversationRepository.findByUserEntity1IdAndUserEntity2Id(id1, id2);
    }


    public ConversationEntity updateConversation(Long id, ConversationEntity conversationEntity) {
        return conversationRepository.findById(id)
                .map(conversation-> {
//                    conversation.setUser1_id(conversationEntity.getUser1_id());
//                    conversation.setUser2_id(conversationEntity.getUser2_id());
                    conversation.setMessage(conversationEntity.getMessage());
                    conversation.setDate(conversationEntity.getDate());
                    return conversationRepository.save(conversation);
                }).orElseThrow(()-> new RuntimeException("Conversation non trouvée !"));
    }

    public String deleteConversation(Long id) {
        conversationRepository.deleteById(id);
        return "Message suppprimé !";
    }
}
