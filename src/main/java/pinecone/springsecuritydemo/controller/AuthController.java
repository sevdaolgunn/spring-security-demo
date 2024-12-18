package pinecone.springsecuritydemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pinecone.springsecuritydemo.model.request.UserLoginRequest;
import pinecone.springsecuritydemo.model.request.UserRegisterRequest;
import pinecone.springsecuritydemo.model.response.AuthResponse;
import pinecone.springsecuritydemo.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    private void register(@Valid @RequestBody UserRegisterRequest request) {
        userService.register(request);
    }

    @PostMapping("/login")
    private AuthResponse login(@Valid @RequestBody UserLoginRequest request) {
        return userService.login(request);
    }

    @GetMapping
    private String test() {
        return userService.test();
    }
}
