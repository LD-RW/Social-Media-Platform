package com.social.media.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "social_user")
    @JsonIgnore
    private SocialUser socialUser;

    public void setSocialUser(SocialUser socialUser) {
        if (this.socialUser == socialUser) {
            return;
        }
        if(this.socialUser != null) {
            this.socialUser.setSocialProfile(null);
        }
        this.socialUser = socialUser;
        if(socialUser != null && socialUser.getSocialProfile() != this) {
            socialUser.setSocialProfile(this);
        }

    }
}
