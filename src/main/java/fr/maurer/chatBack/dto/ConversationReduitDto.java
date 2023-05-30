package fr.maurer.chatBack.dto;

import fr.maurer.chatBack.model.UserEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ConversationReduitDto {
    private Long id;
    private Date datesortie;
    private List<UserEntity> users1_id = new ArrayList<>();
    private List<UserEntity> users2_id = new ArrayList<>();
}
