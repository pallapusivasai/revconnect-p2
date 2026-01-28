package com.revconnect.repository;
import com.revconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByEmailAndPassword(String e,String p);
    Optional<User> findByEmail(String e);
}
