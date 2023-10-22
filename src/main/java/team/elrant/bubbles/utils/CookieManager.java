package team.elrant.bubbles.utils;


import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import java.util.logging.Logger;

import jakarta.servlet.http.Cookie;
import team.elrant.bubbles.XMPP.Profile;

/**
 * Manages cookies for the application.
 */
public class CookieManager {

    private static final Logger LOGGER = Logger.getLogger(CookieManager.class.getName());

        // Method to save a user-specific cookie
    
    /**
     * Saves a user-specific cookie.
     *
     * @param user    The user to be saved in the cookie.
     * @param request The current request. (We reinitalize the session for security reasons.)
     * @param session The current session.
     */
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

    /**
     * Retrieves a user from the cookie.
     *
     * @param currentSession The current session.
     * @return The user retrieved from the cookie.
     */
    public Profile getProfileFromCookie(VaadinSession currentSession){
        Profile user = (Profile) currentSession.getAttribute("user");
        // log
        LOGGER.info("User retrieved from cookie: " + user);
        return user;
    }
}
