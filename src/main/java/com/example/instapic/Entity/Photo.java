package com.example.instapic.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String name;
    @Lob
    private byte[] image;
    @JsonIgnore
    private  Long usersId;
    @JsonIgnore
    private  Long postId;

}
