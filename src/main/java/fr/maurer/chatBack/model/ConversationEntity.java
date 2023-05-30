package fr.maurer.chatBack.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.time.Instant;

@Entity
@Table(name = "CONVERSATION")
@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor
public class ConversationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private UserEntity userEntity1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private UserEntity userEntity2;

    @Column(length = 250)
    private String message;
    private String date;
}