package fr.maurer.chatBack.controller;

import fr.maurer.chatBack.model.ConversationEntity;
import fr.maurer.chatBack.service.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conversation")
@AllArgsConstructor
//@CrossOrigin(origins = {"http://localhost:42*"})
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201", "http://localhost:4202", "http://localhost:4203"})
public class ConversationController {
    private final ConversationService conversationService;

    //    @PostMapping("/create")
    @PostMapping()
    public ConversationEntity create(@RequestBody ConversationEntity conversationEntity){
        return conversationService.createConversation(conversationEntity);
    }

    @GetMapping()
    // @GetMapping("/read")
    public List<ConversationEntity> read(){
        return conversationService.getConversation();
    }

    /**
     * N'est pas utilise car besoin du groupp by
     * @param id
     * @return
     */
//    @GetMapping("{id}")
//    // @GetMapping("/read")
//    public List<ConversationEntity> getAllByUserId(@PathVariable Long id){
//        return conversationService.getAllByUserId(id);
//    }

    /**
     * Liste uniquemement les conversations pour un id (pas le detail)
     * @param id
     * @return
     */
    @GetMapping("{id}")
    // @GetMapping("/read")
    public List<ConversationEntity> getAllByUserIdGroupByUser2(@PathVariable Long id){
        return conversationService.getAllByUserIdGroupByUser2(id);
    }

    @GetMapping("/unique/{id}")
    // @GetMapping("/read")
    public List<ConversationEntity> getAllByUserIdGroupByUser2(@PathVariable Long id, @RequestParam Long id2){
        return conversationService.getAllConversByid1AndId2(id, id2);
    }

    @PutMapping("{id}")
    public ConversationEntity update(@PathVariable Long id, @RequestBody ConversationEntity conversationEntity){
        return conversationService.updateConversation(id, conversationEntity);
    }

    // @DeleteMapping("/delete/{id}")
    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        return conversationService.deleteConversation(id);
    }
}
