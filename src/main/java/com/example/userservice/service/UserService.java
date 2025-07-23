package com.example.userservice.service;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repository.TokenRepository;
import com.example.userservice.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;

@Service
public class UserService {
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    private UserRepo userRepo;
    private TokenRepository tokenRepository;

    UserService(BCryptPasswordEncoder bcryptPasswordEncoder, UserRepo userRepo, TokenRepository tokenRepository ){
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.userRepo = userRepo;
        this.tokenRepository = tokenRepository;
    }
    public User signUp(String email, String password, String name){
      User user = new User();
      user.setEmail(email);
      user.setVerified(false);
      user.setName(name);
      user.setHashedPassword(bcryptPasswordEncoder.encode(password));
      return userRepo.save(user);

    }

    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User with email " + email + " doesn't exist");
        }

        User user = optionalUser.get();

        if (!bcryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            //Throw some exception.
            return null;
        }

        //Login successful, generate a Token.
        Token token = generateToken(user);
        Token savedToken = tokenRepository.save(token);

        return savedToken;
    }

    private Token generateToken(User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate thirtyDaysLater = currentDate.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpiry(expiryDate);
        //128 character alphanumeric string.
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);
        token.setActive(true);
        return token;
    }

    public void logout(String tokenValue) {
        Optional<Token> optionalToken = tokenRepository.findByValueAndActive(tokenValue, false);

        if (optionalToken.isEmpty()) {
            //Throw new Exception
            return;
        }

        Token token = optionalToken.get();
        token.setActive(false);
        tokenRepository.save(token);
    }

    public User validateToken(String token) {
        Optional<Token> optionalToken = tokenRepository.findByValueAndActiveAndExpiryGreaterThan(token, true, new Date());

        if (optionalToken.isEmpty()) {
            //Throw new Exception
            return null;
        }

        return optionalToken.get().getUser();
    }
}
