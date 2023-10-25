package com.hkhr.synonymsapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Synonyms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String word;

    @Column(columnDefinition = "TEXT")
    private String synonyms;

}
