package com.demo.service;

import com.demo.dto.GameDTO;
import com.demo.dto.RegisterRequestDTO;
import com.demo.dto.UserDTO;
import com.demo.entity.Game;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.GameRepository;
import com.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceJPAImpl implements UserServiceJPA {
    private final UserRepository userRepository;
    private final GameServiceJPA gameServiceJPA;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJPAImpl(
            UserRepository userRepository,
            GameRepository gameRepository, GameServiceJPA gameServiceJPA,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.gameServiceJPA = gameServiceJPA;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(RegisterRequestDTO registerRequestDTO) {
        User user = new User();
        user.setFirstName(registerRequestDTO.firstName());
        user.setLastName(registerRequestDTO.lastName());
        user.setEmail(registerRequestDTO.email());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        user.setRole(Role.valueOf(registerRequestDTO.role()));
//        user.setVerified("ADMIN".equals(user.getRole().toString()));
        user.setVerified(false);

        List<Game> games = new ArrayList<>();

        for (GameDTO gameDTO : registerRequestDTO.games()){
            Game gameFromDB = gameServiceJPA.findByNameNormalized(gameDTO.getName());
            if (gameFromDB == null){
//                Game game = new Game();
//                game.setName(gameDTO.getName());
//                gameServiceJPA.saveGame(gameDTO);
                games.add(gameServiceJPA.saveGame(gameDTO));
            } else {
                games.add(gameFromDB);
            }
        }

        user.setGames(games);

        return userRepository.save(user);
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
