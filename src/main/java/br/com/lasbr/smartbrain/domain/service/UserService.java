package br.com.lasbr.smartbrain.domain.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lasbr.smartbrain.domain.dto.UserRequest;
import br.com.lasbr.smartbrain.domain.dto.UserResponse;
import br.com.lasbr.smartbrain.domain.model.User;
import br.com.lasbr.smartbrain.domain.repositories.UserRepository;
import br.com.lasbr.smartbrain.infra.exception.RegistrationException;
import br.com.lasbr.smartbrain.infra.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

    @Service
    @Slf4j
    public class UserService implements UserDetailsService {

        private final UserRepository repository;
        private final BCryptPasswordEncoder passwordEncoder;
        
        @Autowired
        public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
            this.repository = repository;
            this.passwordEncoder = passwordEncoder;
        }

        public UserResponse registerUser(UserRequest request) {
            try {
                validateRegistrationData(request);
                User user = new User(request);
                user.setPassword(passwordEncoder.encode(request.password()));
                repository.save(user);
                log.info("Usuário registrado com sucesso: {}", user.getName());
                return new UserResponse(user);
            } catch (RegistrationException e) {
                log.warn("Erro ao registrar usuário: {}", e.getMessage());
                throw e;
            } catch (Exception e) {
                log.error("Erro ao registrar usuário: {}", e.getMessage());
                throw e;
            }
        }

        private void validateRegistrationData(UserRequest request) throws RegistrationException {
            String email = request.email();

            if (email == null || !isValidEmail(email)) {
                throw new RegistrationException("Endereço de e-mail inválido.");
            }
        }

        private boolean isValidEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

        public UserResponse getUserById(Long id) {
            Optional<User> optionalUser = repository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                log.info("Perfil do usuário obtido com sucesso: {}", user.getName());
                return new UserResponse(user);
            } else {
                log.warn("Usuário com o ID {} não encontrado", id);
                throw new UserNotFoundException("Usuário não encontrado");
            }
        }
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return repository.findByEmail(username);
        }
    }
