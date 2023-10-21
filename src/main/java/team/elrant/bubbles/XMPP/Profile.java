package team.elrant.bubbles.XMPP;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;


public class Profile {
    private String username;
    private String password;
    private String serviceName;
    private AbstractXMPPConnection connection;

    public Profile(String username, String password, String serviceName) {
        this.username = username;
        this.password = password;
        this.serviceName = serviceName;
        System.out.println("Profile instantiated with the following information:");

        System.out.println("Username: " + username);
        System.out.println("Service name: " + serviceName);
        System.out.println("Password: " + password);

        connect();
        System.out.println("Profile: " + this);


    }

    public void connect() {
        try {
            AbstractXMPPConnection connection = new XMPPTCPConnection(username, password, serviceName);
            connection.connect().login();
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }

    public void sendMessage(Stanza message) {
        try {
            connection.sendStanza(message);
        } catch (NotConnectedException | InterruptedException e) {
            e.printStackTrace();
        }

    }
 
    public AbstractXMPPConnection getConnection() {
        return connection;
    }
}
