package team.elrant.bubbles.views.chat;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.stringprep.XmppStringprepException;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import team.elrant.bubbles.XMPP.Profile;
import team.elrant.bubbles.utils.CookieManager;
import team.elrant.bubbles.views.MainLayout;

/**
 * ChatView is the view that allows users to chat with their contacts.
 * It is the view that users are redirected to after they log in.
 */
@Route(value = "chat", layout = MainLayout.class)
@PageTitle("Chat")
public class ChatView extends VerticalLayout {

    private Div chatMessages;
    private TextField inputMessage;
    private Button sendButton;
    private AbstractXMPPConnection connection;

    // get the user's connection from the cookie
    private Profile user;
    private CookieManager cookieMonster = new CookieManager();

    /**
     * Constructs a new ChatView.
     * This constructor initializes the ChatView object with the profile
     * retrieved from session storage and establishes a connection.
     */
    public ChatView() {
        VaadinSession currentSession = VaadinSession.getCurrent();
        System.out.println("ChatView: currentSession: " + currentSession);
        user = cookieMonster.getProfileFromCookie(currentSession);
        System.out.println("ChatView: user: " + user);
        
        AbstractXMPPConnection connection = user.connection;
        System.out.println("ChatView: connection: " + connection);
        chatMessages = new Div();
        chatMessages.addClassName("chat-messages");

        inputMessage = new TextField("Type a message...");

        sendButton = new Button("Send");

        sendButton.addClickListener(e -> {

            try {
                sendMessage(inputMessage.getValue(), user.getUsername());
            } catch (NotConnectedException | XmppStringprepException | InterruptedException e1) {
                e1.printStackTrace();
            }

        });

        add(chatMessages, inputMessage, sendButton);
        this.connection = connection;
    }

    /**
     * Sends a message to the user's contact.
     * @param messageText
     * @throws NotConnectedException
     * @throws InterruptedException
     * @throws XmppStringprepException
     */
    private void sendMessage(String messageText, String recipient)
            throws NotConnectedException, InterruptedException, XmppStringprepException {

        // Display the sent message in the chat interface
        Div messageDiv = new Div(new Text("You: " + messageText));
        messageDiv.addClassName("message");
        chatMessages.add(messageDiv);

        Message message = connection.getStanzaFactory()
                .buildMessageStanza()
                .to(recipient)
                .setBody(messageText)
                .build();

        connection.sendStanza(message);

        connection.disconnect();
    }

    public void setUser(Profile user) {
        this.user = user;
    }

}
