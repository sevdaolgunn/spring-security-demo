package pinecone.springsecuritydemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pinecone.springsecuritydemo.entity.User;
import pinecone.springsecuritydemo.exception.GenericException;
import pinecone.springsecuritydemo.model.enums.Role;
import pinecone.springsecuritydemo.model.request.UserLoginRequest;
import pinecone.springsecuritydemo.model.request.UserRegisterRequest;
import pinecone.springsecuritydemo.model.response.AuthResponse;
import pinecone.springsecuritydemo.repository.UserRepository;
import pinecone.springsecuritydemo.utils.TokenUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenUtils jwtService;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRegisterRequest request) {
//        if (userRepository.findByEmail(request.getEmail()).isEmpty()) {
//            throw new GenericException(HttpStatus.BAD_REQUEST, "User has already exists");
//        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(UserLoginRequest request) {
        return userRepository.findByEmail(request.getEmail()).map(user -> AuthResponse.builder()
                        .token(jwtService.generateToken(user.getId(), user.getRole()))
                        .build())
                .orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "User not found!"));
    }

    public String test(){
        return "doFilterInternalHasWorking.";
    }

}
