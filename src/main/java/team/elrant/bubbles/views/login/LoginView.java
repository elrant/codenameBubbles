package team.elrant.bubbles.views.login;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

import team.elrant.bubbles.XMPP.Profile;
import team.elrant.bubbles.views.MainLayout;
import team.elrant.bubbles.utils.CookieManager;

/**
 * LoginView is the view that allows users to log in to the application.
 * It is the first view that users see when they navigate to the application.
 * It is also the view that users are redirected to if they are not logged in.
 */
@PageTitle("Login")
@RouteAlias(value = "", layout = MainLayout.class)
@Route(value = "login", layout = MainLayout.class)
public class LoginView extends VerticalLayout {

    private CookieManager cookieMonster = new CookieManager();
    private TextField usernameField;
    private TextField serviceNameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Notification errorNotification;
    public Profile user;

    /**
     * Constructs a new LoginView.
     */
    public LoginView() {
        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        serviceNameField = new TextField("Service Name");
        loginButton = new Button("Login");
        errorNotification = new Notification("Uh oh.", 3000);

        loginButton.addClickListener(e -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            String serviceName = serviceNameField.getValue();
            try {
                Profile user = new Profile(username, password, serviceName);
                boolean status = user.connect();
                if (status) {
                    // Save a cookie after successful login
                    VaadinRequest currentRequest = VaadinService.getCurrentRequest();
                    System.out.println("LoginView: currentRequest: " + currentRequest);
                    VaadinSession currentSession = VaadinSession.getCurrent();
                    System.out.println("LoginView: currentSession: " + currentSession);
                    cookieMonster.saveUserCookie(user, currentRequest, currentSession);
                    user.disconnect();
                    // If the connection is successful, navigate to the "chat" view
                    getUI().ifPresent(ui -> ui.navigate("chat"));
                }

            } catch (Exception exception) {
                errorNotification.setText("Login failed. Please check your credentials.");
                errorNotification.open();
            }
        });
        loginButton.addClickShortcut(Key.ENTER);

        add(usernameField, serviceNameField, passwordField, loginButton, errorNotification);
        setAlignItems(Alignment.CENTER);
    }

}
