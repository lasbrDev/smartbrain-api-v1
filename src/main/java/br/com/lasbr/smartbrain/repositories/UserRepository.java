package br.com.lasbr.smartbrain.repositories;

import br.com.lasbr.smartbrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface UserRepository extends JpaRepository<User, Long> {
    }
