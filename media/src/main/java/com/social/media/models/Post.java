package com.social.media.models;

import jakarta.persistence.*;

@Entity

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SocialUser socialUser;
}
