package com.demo.service;

import com.demo.dto.SellerGameRequestDTO;
import com.demo.dto.RegisterSellerRequestDTO;
import com.demo.dto.UserDTO;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceJPAImpl implements UserService {
    private final UserRepository userRepository;
//    private final GameService gameService;
    private final SellerGameService sellerGameService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJPAImpl(
            UserRepository userRepository,
            GameService gameService,
            SellerGameService sellerGameService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
//        this.gameService = gameService;
        this.sellerGameService = sellerGameService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegisterSellerRequestDTO registerSellerRequestDTO) {
        User user = new User();
        user.setFirstName(registerSellerRequestDTO.firstName());
        user.setLastName(registerSellerRequestDTO.lastName());
        user.setEmail(registerSellerRequestDTO.email());
        user.setPassword(passwordEncoder.encode(registerSellerRequestDTO.password()));
        user.setRole(Role.valueOf(registerSellerRequestDTO.role()));
//        user.setVerified("ADMIN".equals(user.getRole().toString()));
        user.setVerified(false);
        user = userRepository.save(user);

        for (SellerGameRequestDTO sellerGameRequestDTO : registerSellerRequestDTO.games()) {
            sellerGameService.saveSellerGame(user, sellerGameRequestDTO);
        }
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserDTO> usersDTO;
        List<User> users = userRepository.findAll();
        return null;
    }

    @Override
    public Optional<UserDTO> findUserById(Integer id) {
        UserDTO userDTO;
        Optional<User> user = userRepository.findById(id);
        return null;
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
