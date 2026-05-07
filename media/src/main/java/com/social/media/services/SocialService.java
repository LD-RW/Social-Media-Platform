package com.social.media.services;

import com.social.media.models.Post;
import com.social.media.models.SocialGroup;
import com.social.media.models.SocialUser;
import com.social.media.repositories.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialService {

    @Autowired
    private SocialUserRepository socialUserRepository;

    public List<SocialUser> getAllUsers() {
        return socialUserRepository.findAll();
    }

    public SocialUser saveUser(SocialUser socialUser) {
        // Explicitly wire up bidirectional relationships because
        // Jackson deserialization bypasses custom setters.
        if (socialUser.getSocialProfile() != null) {
            socialUser.getSocialProfile().setSocialUser(socialUser);
        }
        if (socialUser.getPosts() != null) {
            for (Post post : socialUser.getPosts()) {
                post.setSocialUser(socialUser);
            }
        }
        if (socialUser.getSocialGroups() != null) {
            for (SocialGroup group : socialUser.getSocialGroups()) {
                group.getSocialUsers().add(socialUser);
            }
        }
        return socialUserRepository.save(socialUser);
    }

    public SocialUser deleteUser(Long id) {
        SocialUser socialUser = socialUserRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Social user with id " + id + " not found."));
        socialUserRepository.delete(socialUser);
        return socialUser;
    }
}
