package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.authentication.*;
import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import com.skillbox.devpub.repository.PostRepository;
import com.skillbox.devpub.repository.UserRepository;
//import com.skillbox.devpub.security.jwt.JwtTokenProvider;
import com.skillbox.devpub.service.AuthService;
import com.skillbox.devpub.service.MailService;
import com.skillbox.devpub.service.UserService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private Map<String, Integer> loggedIn = new HashMap<>();

    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MailService mailService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager/*, JwtTokenProvider jwtTokenProvider*/,
                           UserService userService,
                           UserRepository userRepository,
                           PostRepository postRepository,
                           MailService mailService) {
        this.authenticationManager = authenticationManager;
//        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.mailService = mailService;
    }

    @Override
    public Response login(AuthRequestDto request/*, HttpServletRequest httpServletRequest*//*, String referer*/) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
            User result = userRepository
                    .findByEmail(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User " + user.getUsername() + " not found"));
//            int moderationCount = getModerationCount(result);

            return AuthResponseFactory.getAuthResponse(true, result, getModerationCount(result));
        } catch (AuthenticationException exception) {
            return new BaseResponse(false);
        }

//        try {
//            String userEmail = request.getEmail();
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, request.getPassword()));
//            User user = userService.findByEmail(userEmail);
//
//            if (user == null) {
//                throw new UsernameNotFoundException("User with email: " + userEmail + " not found");
////                return AuthenticationResponseFactory.getErrorResponse();
//            }
//
//            String token = jwtTokenProvider.createToken(userEmail);
//
//            loggedIn.put(httpServletRequest.getSession().getId(), user.getId());
//
////            Map<Object, Object> response = new HashMap<>();
////            response.put("username", userEmail);
////            response.put("token", token);
//
//            return AuthResponseFactory.getAuthResponse(true, user/*, token*/);
//
//        } catch (AuthenticationException exception) {
//            return new BaseResponse(false);
////            throw new BadCredentialsException("Invalid username or password");
//        }
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

    @Override
    public Response authCheck(Principal principal/*HttpServletRequest httpServletRequest*/) {
        if (principal == null) {
            return new BaseResponse(false);
        }
        User result = userRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User " + principal.getName() + " not found"));
//        int moderationCount = getModerationCount(result);
        return AuthResponseFactory.getAuthResponse(true, result, getModerationCount(result));

//        User user = getAuthUser(httpServletRequest);
//        if (user != null) {
//            return AuthResponseFactory.getAuthResponse(true, user);
//        }
//        return new BaseResponse(false);
//        String sessionId = httpServletRequest.getSession().getId();
//        if (loggedIn.containsKey(sessionId))
//        {
//            User user = userService.findById(loggedIn.get(sessionId));
//
//            return AuthenticationResponseFactory.getAuthResponse(user);
//        }
//
//        return new ErrorResponse(false);
    }

//    @Override
//    public void logout(HttpServletRequest httpServletRequest) {
//        String sessionId = httpServletRequest.getSession().getId();
//        if (loggedIn.containsKey(sessionId)) {
//            loggedIn.remove(sessionId);
//        }
//    }

    @Override
    public Response register(RegistrationRequestDto requestDto) {
        Response registration = userService.register(requestDto);
//        User user = userService.findByEmail(requestDto.getE_mail());
//        loggedIn.put(httpServletRequest.getSession().getId(), user.getId());
        return registration;
    }

    @Override
    public User getAuthUser(Principal principal/*HttpServletRequest servletRequest*/) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            return user;
        }
//        String sessionId = servletRequest.getSession().getId();
//        if (loggedIn.containsKey(sessionId))
//        {
//            User user = userService.findById(loggedIn.get(sessionId));
//
//            return user;
//        }

        return null;
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
//        System.out.println(confirmationCode);
//        System.out.println(fullLink);
        mailService.sendPasswordRecovery(request.getEmail(), user.getName(), fullLink);
        return new BaseResponse(true);
    }

    @Override
    public Response changePassword(PasswordChangeRequestDto request) {
        Response response = userService.changePassword(request);
        return response;
    }
}
