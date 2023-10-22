package team.elrant.bubbles.XMPP;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.util.dns.minidns.MiniDnsResolver;

/**
 * Initializes a new Profile with the provided credentials.
 *
 * @param username    The username for the XMPP connection.
 * @param password    The password for the XMPP connection.
 * @param serviceName The service name for the XMPP connection.
 */
public class Profile {
    private String username;
    private String password;
    private String serviceName;
    public AbstractXMPPConnection connection;

    /**
     * Constructs a new Profile with the provided username, password, and service
     * name.
     * This constructor initializes the Profile object with the given information,
     * sets up the MiniDNS resolver,
     * and establishes a connection.
     *
     * @param username    The username associated with the profile.
     * @param password    The password associated with the profile.
     * @param serviceName The FQDN we're logging into.
     */
    public Profile(String username, String password, String serviceName) {
        this.username = username;
        this.password = password;
        this.serviceName = serviceName;
        System.out.println("Profile instantiated with the following information:");
        System.out.println("Username: " + username);
        System.out.println("Service name: " + serviceName);
        System.out.println("Password: " + password);

        // setup minidns resolver
        MiniDnsResolver.setup();

        connect();
        System.out.println("Profile: " + this);
        System.out.println("Connection: " + connection);
    }

    /**
     * Establishes a connection to the XMPP server using the provided credentials.
     */
    public void connect() {
        try {
            AbstractXMPPConnection connection = new XMPPTCPConnection(username, password, serviceName);
            connection.connect().login();
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }

    /**
     * Sends a message to the XMPP server.
     *
     * @param message The message to be sent.
     */
    public void sendMessage(Stanza message) {
        try {
            connection.sendStanza(message);
        } catch (NotConnectedException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
