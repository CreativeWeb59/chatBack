package fr.maurer.chatBack.service;

import fr.maurer.chatBack.model.UserEntity;
import fr.maurer.chatBack.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }


    public List<UserEntity> getUser() {
        return userRepository.findAll();
    }

    /**
     * Teste un utilisateur s'il existe dans la base
     * @param userName
     * @param password
     * @return id + username de l'utilisateur
     */
    public Map<String, Object> getAuth(String userName, String password) {
        UserEntity userEntity = userRepository.findByUserNameAndPassword(userName, password);
        Map<String, Object> response = new HashMap<>();
        if (userEntity == null) {
            // La requête est vide, aucun utilisateur trouvé avec ces informations
            // Effectuez les actions appropriées ici (par exemple, lever une exception, renvoyer null, etc.)
            response.put("id", "not");
            response.put("userName", "not");
            return response;
        }
        response.put("id", userEntity.getId());
        response.put("userName", userEntity.getUserName());
        return response;
    }

//    public UserEntity getAuth(String userName, String password) {
//        return userRepository.findByUserNameAndPassword(userName, password);
//    }

    public UserEntity updateUser(Long id, UserEntity userEntity) {
        return userRepository.findById(id)
                .map(user-> {
                    user.setUserName(userEntity.getUserName());
                    user.setEmail(userEntity.getEmail());
                    user.setPassword(userEntity.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(()-> new RuntimeException("Utilisateur non trouvé !"));
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "Utilisateur suppprimé !";
    }
}
