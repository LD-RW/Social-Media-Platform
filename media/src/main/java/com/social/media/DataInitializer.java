package com.social.media;

import com.social.media.repositories.PostRepository;
import com.social.media.repositories.SocialProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.social.media.models.Post;
import com.social.media.models.SocialGroup;
import com.social.media.models.SocialProfile;
import com.social.media.models.SocialUser;
import com.social.media.repositories.SocialGroupRepository;
import com.social.media.repositories.SocialUserRepository;

@Configuration
public class DataInitializer {

    private final SocialUserRepository userRepository;
    private final SocialGroupRepository groupRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final PostRepository postRepository;

    public DataInitializer(SocialUserRepository userRepository, SocialGroupRepository groupRepository, SocialProfileRepository socialProfileRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Persist groups first — they need an id before users reference them
            SocialGroup group1 = new SocialGroup();
            SocialGroup group2 = new SocialGroup();
            groupRepository.save(group1);
            groupRepository.save(group2);

            // Build users with all associations set on the owning side, then save once each.
            // Profiles are created transient — SocialUser.socialProfile has CascadeType.ALL
            // so saving the user cascades a persist to the profile.
            SocialUser user1 = new SocialUser();
            user1.setSocialProfile(new SocialProfile());
            user1.getSocialGroups().add(group1);

            SocialUser user2 = new SocialUser();
            user2.setSocialProfile(new SocialProfile());
            user2.getSocialGroups().add(group1);
            user2.getSocialGroups().add(group2);

            SocialUser user3 = new SocialUser();
            user3.setSocialProfile(new SocialProfile());
            user3.getSocialGroups().add(group2);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            // Posts can be saved after users have ids
            Post post1 = new Post();
            Post post2 = new Post();
            Post post3 = new Post();
            post1.setSocialUser(user1);
            post2.setSocialUser(user2);
            post3.setSocialUser(user3);
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);
        };
    }
}
