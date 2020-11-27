package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.authentication.AuthRequestDto;
import com.skillbox.devpub.dto.authentication.AuthResponseFactory;
import com.skillbox.devpub.dto.authentication.EmailRequestDto;
import com.skillbox.devpub.dto.authentication.PasswordChangeRequestDto;
import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import com.skillbox.devpub.repository.PostRepository;
import com.skillbox.devpub.repository.UserRepository;
import com.skillbox.devpub.service.AuthService;
import com.skillbox.devpub.service.MailService;
import com.skillbox.devpub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MailService mailService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserService userService,
                           UserRepository userRepository,
                           PostRepository postRepository,
                           MailService mailService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.mailService = mailService;
    }

    @Override
    public Response login(AuthRequestDto request) {

        try {
            Authentication auth = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

            User result = userRepository
                    .findByEmail(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User " + user.getUsername() + " not found"));

            return AuthResponseFactory.getAuthResponse(true, result, getModerationCount(result));
        } catch (AuthenticationException exception) {

            return new BaseResponse(false);
        }
    }

    @Override
    public Response authCheck(Principal principal) {

        if (principal == null) {

            return new BaseResponse(false);
        }

        User result = userRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User " + principal.getName() + " not found"));

        return AuthResponseFactory.getAuthResponse(true, result, getModerationCount(result));
    }

    @Override
    public Response passwordRecovery(EmailRequestDto request, String link) {

        User user;

        try {
            user = userService.findByEmail(request.getEmail());
        } catch (Exception ex) {

            return new BaseResponse(false);
        }

        String confirmationCode = UUID.randomUUID().toString().replace("-", "");
        user.setCode(confirmationCode);

        userRepository.save(user);

        String fullLink = link + confirmationCode;

        mailService.sendPasswordRecovery(request.getEmail(), user.getName(), fullLink);

        return new BaseResponse(true);
    }

    @Override
    public Response changePassword(PasswordChangeRequestDto request) {

        return userService.changePassword(request);
    }

    private int getModerationCount(User result) {

        int moderationCount = 0;

        if (result.getIsModerator()) {
            moderationCount = (int) postRepository.findAll().stream().filter(Post::getIsActive)
                    .filter(p -> p.getModerationStatus() == ModerationStatus.NEW)
                    .filter(p -> p.getTime().isBefore(LocalDateTime.now())).count();
        }

        return moderationCount;
    }
}
