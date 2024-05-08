package com.codeWithSrb.BookYourSlot.Service;

import com.codeWithSrb.BookYourSlot.Model.UserInfo;
import com.codeWithSrb.BookYourSlot.Repository.UserRepository;
import com.codeWithSrb.BookYourSlot.config.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userRepository.findByUserName(username);
        return userInfo.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found :" + username));
    }

    public void registerNewUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        UserInfo response = userRepository.save(userInfo);
        System.out.printf("New user has been created with name :%s", userInfo.getUserName());
    }
}
