package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.dto.AccessTokenDto;
import com.financemobile.fmassets.exception.AuthenticationException;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Slf4j
@Service("user_detail_service")
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Value("${fmassetui.client.id}")
    private String clientId;

    @Value("${fmassetui.client.secret}")
    private String clientSecret;

    private RestTemplate restTemplate;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    translate(user.getRole()));
        }

        log.info(">>> USER NOT FOUND : ", email);
        throw new UsernameNotFoundException("Email: " + email + " not found");
    }

    private Collection<? extends GrantedAuthority> translate(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String name = role.getName();
        if (!name.startsWith("ROLE_")) {
                name = "ROLE_" + name;
        }
        authorities.add(new SimpleGrantedAuthority(name));
        return authorities;
    }

    public AccessTokenDto login(String username, String password)
            throws AuthenticationException {

        MultiValueMap login = new HttpHeaders();

        login.set("grant_type", "password");
        login.set("username", username);
        login.set("password", password);

        StringJoiner credentialsJoiner = new StringJoiner(":");
        credentialsJoiner.add(clientId);
        credentialsJoiner.add(clientSecret);

        String url = "http://localhost:8084/fmasset/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " +
                Base64.getEncoder().encodeToString(credentialsJoiner.toString().getBytes()));

        HttpEntity<?> httpEntity = new HttpEntity<Object>(login, headers);
        ResponseEntity<AccessTokenDto> response = null;
        try {
             response = restTemplate.postForEntity(url, httpEntity, AccessTokenDto.class);
        } catch (Exception e) {
            throw new AuthenticationException("bad credentials");
        }
        AccessTokenDto accessTokenDto = response.getBody();

        if(accessTokenDto != null && !accessTokenDto.getAccess_token().isEmpty()){
            User user = userRepository.findByEmail(username).get();
            accessTokenDto.setUser(user);
            return accessTokenDto;
        }

        throw new AuthenticationException("bad credentials");
    }
}
