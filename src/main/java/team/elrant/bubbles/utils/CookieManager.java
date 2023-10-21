package team.elrant.bubbles.utils;


import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import java.util.logging.Logger;

import jakarta.servlet.http.Cookie;
import team.elrant.bubbles.XMPP.Profile;

public class CookieManager {
    private static final Logger LOGGER = Logger.getLogger(CookieManager.class.getName());

        // Method to save a user-specific cookie
    public void saveUserCookie(Profile user, VaadinRequest request, VaadinSession session) {
        VaadinRequest currentRequest = request;
        LOGGER.info("Current request: " + currentRequest);
        VaadinSession currentSession = session;
        LOGGER.info("Current session: " + currentSession);
        Cookie userCookie = new Cookie("user", user.toString());
        userCookie.setMaxAge(3600);
        userCookie.setPath("/");
        
        VaadinService.reinitializeSession(currentRequest);

        currentSession.setAttribute("user", user);
        
        VaadinService.getCurrentResponse().addCookie(userCookie);
        LOGGER.info("Cookie saved");
    }

    public Profile getProfileFromCookie(){
        VaadinSession currentSession = VaadinSession.getCurrent();
        Profile user = (Profile) currentSession.getAttribute("user");
        // log
        LOGGER.info("User retrieved from cookie: " + user);
        return user;
    }
}
