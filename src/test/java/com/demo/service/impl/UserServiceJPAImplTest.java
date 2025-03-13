package com.demo.service.impl;

import com.demo.repository.UserRepository;
import com.demo.service.CommentService;
import com.demo.service.SellerOfferService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@SpringBootTest
public class UserServiceJPAImplTest {
    @Mock
    private final UserRepository userRepository;
    @Mock
    private final SellerOfferService sellerOfferService;
    @Mock
    private final CommentService commentService;
    @Mock
    private final PasswordEncoder passwordEncoder;

    private final UserServiceJPAImpl userServiceJPA = new UserServiceJPAImpl(
            userRepository,
            sellerOfferService,
            commentService,
            passwordEncoder
    );

    @BeforeEach
    void setup(){
//        Mockito.when()
    }
//    @Test
//    void shouldSaveSeller(){
//        userServiceJPA.saveSeller();
//        Mockito.verify(userRepository).save(user);
//    }
}
