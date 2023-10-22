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

    public ChatView() {
        VaadinSession currentSession = VaadinSession.getCurrent();
        user = cookieMonster.getProfileFromCookie(currentSession);
        
        AbstractXMPPConnection connection = user.connection;
        chatMessages = new Div();
        chatMessages.addClassName("chat-messages");

        inputMessage = new TextField("Type a message...");

        sendButton = new Button("Send");

        sendButton.addClickListener(e -> {

            try {
                sendMessage(inputMessage.getValue());
            } catch (NotConnectedException | XmppStringprepException | InterruptedException e1) {
                e1.printStackTrace();
            }

        });

        add(chatMessages, inputMessage, sendButton);
        this.connection = connection;
    }

    private void sendMessage(String messageText)
            throws NotConnectedException, InterruptedException, XmppStringprepException {

        // Display the sent message in the chat interface
        Div messageDiv = new Div(new Text("You: " + messageText));
        messageDiv.addClassName("message");
        chatMessages.add(messageDiv);

        Message message = connection.getStanzaFactory()
                .buildMessageStanza()
                .to("jsmith@igniterealtime.org")
                .setBody("Howdy! How are you?")
                .build();

        connection.sendStanza(message);

        connection.disconnect();
    }

    public void setUser(Profile user) {
        this.user = user;
    }

}
