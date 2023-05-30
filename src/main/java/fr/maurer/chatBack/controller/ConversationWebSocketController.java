package fr.maurer.chatBack.controller;

import fr.maurer.chatBack.dto.ConversationRequestDto;
import fr.maurer.chatBack.model.ConversationEntity;
import fr.maurer.chatBack.service.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@AllArgsConstructor
// @CrossOrigin(origins = {"http://localhost:42*"})
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201", "http://localhost:4202", "http://localhost:4203"})
public class ConversationWebSocketController {

    private final ConversationService conversationService;
    private SimpMessagingTemplate messagingTemplate;

    /**
     * recupere le message
     * l'enregistre en bdd
     * et renvoi à tous les utilisateurs
     * @param conversationRequestDto
     * @return
     */
    @MessageMapping("/send-message") // Mapping du point de terminaison pour recevoir les messages du client
    @SendTo("/topic/messages") // Envoi des messages aux abonnés sur "/topic/messages"
    public ConversationEntity handleMessage(ConversationRequestDto conversationRequestDto) {
        // Traitez le message reçu et renvoyez une réponse
        System.out.println("Creation par hanleMessage");
        return conversationService.createConversationWithUsers(conversationRequestDto);
    }

    /**
     * Recupere en socket le message d'un utilisateur
     * cree une conversation après vérification de l'existence des id
     * en cours : ne doit renvoyer le message qu'a l'utilisateur concerné
     * on passe par @SendTo pour choisir l'id
     * @param conversationRequestDto
     * @return
     */
    @MessageMapping("/private-message/{id}")
    @SendTo("/private/message/{id}")
    public ConversationEntity receivePrivateMessage(@Payload ConversationRequestDto conversationRequestDto, @DestinationVariable String id) {
        // Traitez le message reçu et renvoyez une réponse
        System.out.println("Creation par receivePrivateMessage, id : " + id);
        return conversationService.createConversationWithUsers(conversationRequestDto);
    }

//    @MessageMapping("/messages")
//    public void handleMessage(String message) {
//        // Traitez le message ici
//        String response = "Response message";
//        messagingTemplate.convertAndSend("/messages", response);
//    }

    /**
     * Recupere les messages venants du socket (Chat)
     * Ajoute un message dans la table Conversation
     * @return
     */
    @MessageMapping("/message")
    @SendTo("/topic/new-message")
    public ConversationEntity createBySocket(ConversationEntity conversationEntity) {
        System.out.println("Message reçu de createBySocket : " + conversationEntity);
        messagingTemplate.convertAndSend("/notifier/new-message", "test");
        return conversationService.createConversation(conversationEntity);
    }

//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/message")
//    @SendTo("/chatroom/public")
//    public ConversationEntity reveivePublicMessage(@Payload ConversationEntity conversationEntity){
//        return conversationEntity;
//    }
//
//    @MessageMapping("/private-message")
//    public ConversationEntity reveivePrivateMessage(@Payload ConversationEntity conversationEntity){
//        return conversationEntity;
//    }


}
