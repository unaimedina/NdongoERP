package org.projectndongo.ndongo.security.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.projectndongo.ndongo.config.SerializeOAuthUser;
import org.projectndongo.ndongo.domain.auth.AuthType;
import org.projectndongo.ndongo.domain.auth.User;
import org.projectndongo.ndongo.domain.auth.service.RoleService;
import org.projectndongo.ndongo.domain.auth.service.UserService;
import org.projectndongo.ndongo.security.oauth.CustomOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serial;

@Component
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SerializeOAuthUser serializeOAuthUser;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String oauth2ClientName = oauth2User.getOauth2ClientName();
        String email = oauth2User.getEmail();

        if (userService.userExistsByEmail(email)) {
            userService.updateAuthenticationType(email, "GOOGLE");
            ((CustomOAuth2User) authentication.getPrincipal()).setOauth2ClientName(email.split("@")[0]);
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            System.out.println("User does not exist. Creating new user. (" + email + ")");

            User user = new User();
            user.setEmail(email);
            // Set the username to be the email address
            user.setUsername(email);
            user.setAuthType(AuthType.valueOf(oauth2ClientName.toUpperCase()));
            user.setRole(roleService.getRoleByName("USER"));
            userService.save(user);

            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
