package fr.maurer.chatBack.controller;

import fr.maurer.chatBack.model.UserEntity;
import fr.maurer.chatBack.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
// @CrossOrigin(origins = {"http://localhost:42*"})
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201", "http://localhost:4202", "http://localhost:4203"})
//@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;

//    @PostMapping("/create")
    @PostMapping()
    public UserEntity create(@RequestBody UserEntity userEntity){
        return userService.createUser(userEntity);
    }

    @GetMapping()
    // @GetMapping("/read")
    public List<UserEntity> read(){
        return userService.getUser();
    }


    /**
     * Utiliser @reqyestparam a la place de @pathvariable
     * @param userName
     * @param password
     * @return
     */
    @GetMapping("{userName}/{password}")
    // @GetMapping("/read")
    public Map<String, Object> readUserName(@PathVariable String userName, @PathVariable String password){
        return userService.getAuth(userName, password);
    }

//    @GetMapping("{userName}/{password}")
//    // @GetMapping("/read")
//    public UserEntity readUserName(@PathVariable String userName, @PathVariable String password){
//        return userService.getAuth(userName, password);
//    }

    // @PutMapping("/update/{id}")
    @PutMapping("{id}")
    public UserEntity update(@PathVariable Long id, @RequestBody UserEntity userEntity){
        return userService.updateUser(id, userEntity);
    }

    // @DeleteMapping("/delete/{id}")
    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
