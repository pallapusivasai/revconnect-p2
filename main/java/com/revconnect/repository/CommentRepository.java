package com.revconnect.repository;
import com.revconnect.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<Comment,Long>{}
