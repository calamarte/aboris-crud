package es.aboris.crud.controllers.auth;

import com.google.common.base.Strings;
import es.aboris.crud.security.JWTUtils;
import es.aboris.crud.services.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private UserService service;

    @RequestMapping(
            value = "/auth",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AuthResponse login(@RequestParam Map<String, String> body){

        String username = body.get("username");
        String pass = body.get("password");

        if(!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(pass)) {

            Optional<es.aboris.crud.model.User> result = service.findByUsername(username);

            if (result.isPresent() && result.get().getPassword().equals(pass)) {
                return new User(username, JWTUtils.getPrefix(), JWTUtils.getToken(username));
            }
        }

        return new Error(username);
    }

    //Respuestas personalizadas para la auntenticación
    private interface AuthResponse{ }

    private class User implements AuthResponse{
        private String username;
        private String prefix;
        private String token;

        User(String username, String prefix, String token) {
            this.username = username;
            this.prefix = prefix;
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    private class Error implements AuthResponse{
        private String message;

        Error(String username) {
            this.message = "No ha sido posible loguear al usuario " + username;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
