package com.revconnect.repository;
import com.revconnect.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PostRepository extends JpaRepository<Post,Long>{
    List<Post> findByUser(User u);
    List<Post> findAllByOrderByIdDesc();
}
