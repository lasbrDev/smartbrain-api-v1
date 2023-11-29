package br.com.lasbr.smartbrain.domain.repositories;

import br.com.lasbr.smartbrain.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface UserRepository extends JpaRepository<User, Long> {
    }
