package com.revconnect.repository;
import com.revconnect.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface LikeRepository extends JpaRepository<LikeEntity,Long>{
    Optional<LikeEntity> findByPostAndUser(Post p,User u);
}
