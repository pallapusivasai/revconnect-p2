package com.revconnect.service;
import com.revconnect.model.User;
import com.revconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private UserRepository repo;
    public String register(String e,String p){
        if(repo.findByEmail(e).isPresent()) return "❌ User already exists";
        User u=new User();u.setEmail(e);u.setPassword(p);repo.save(u);
        return "✅ Registered successfully";
    }
    public User login(String e,String p){
        return repo.findByEmailAndPassword(e,p).orElse(null);
    }
}
