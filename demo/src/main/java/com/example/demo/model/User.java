package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity //models
@Getter @Setter 
@NoArgsConstructor //obligatoire pour JPA
@AllArgsConstructor //constructeur avec tous les args
@Builder //créé l'objet
@Table(name = "\"user\"")
public class User {

    @Id //clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) //on laisse la clé primaire être incrementee par la BDD
    private Long id;

    @Column(unique = true, nullable = false) //pas deux users les mêmes, pas null non plus
    private String username;
    
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    //si on supprime un user, ses tâches disparaissent
    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

  
    
    
    
    
}
