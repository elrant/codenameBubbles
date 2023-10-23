package team.elrant.bubbles.XMPP;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.java7.Java7SmackInitializer;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.dns.minidns.MiniDnsResolver;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 * Initializes a new Profile with the provided credentials.
 *
 * @param username    The username for the XMPP connection.
 * @param password    The password for the XMPP connection.
 * @param serviceName The service name for the XMPP connection.
 * 
 */
public class Profile {
    private String username;
    private String password;
    private String serviceName;
    private XMPPTCPConnectionConfiguration connectionConfiguration;
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
        new Java7SmackInitializer().initialize();
        this.username = username;
        this.password = password;
        this.serviceName = serviceName;
        this.connectionConfiguration = configure();

        System.out.println("Profile instantiated with the following information:");
        System.out.println("Username: " + this.username);
        System.out.println("Service name: " + this.serviceName);
        System.out.println("Password: " + this.password);

        // setup minidns resolver
        MiniDnsResolver.setup();

        boolean status = connect();
        System.out.println("Profile: " + this);
        System.out.println("Connection: " + connection);
        System.out.println("isSecure: " + connection.isSecureConnection());
        System.out.println("isConnected: " + status);

        disconnect();
    }

    /**
     * Establishes a connection to the XMPP server using the provided credentials.
     * @return True if the connection is successful, false otherwise.
     */
    public boolean connect() {
        try {

            AbstractXMPPConnection connection = new XMPPTCPConnection(connectionConfiguration);
            connection.connect().login();
            this.connection = connection;
            return connection.isConnected();
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            return connection.isConnected();
        }
    }

    /**
     * Configures the XMPP connection.
     *
     * @return The XMPP connection configuration.
     */
    public XMPPTCPConnectionConfiguration configure() {
        XMPPTCPConnectionConfiguration config;
        try {
            config = XMPPTCPConnectionConfiguration.builder()
                    .setXmppDomain(serviceName)
                    .setUsernameAndPassword(username, password)
                    .setHostnameVerifier(
                            (hostname, session) -> true)
                    .setCompressionEnabled(false).build();
            return config;
        } catch (XmppStringprepException e) {
            e.printStackTrace();
            System.out.println("Failed to run Profile.configure()");
            return null;
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

    /**
     * Disconnects the XMPP connection.
     */
    public void disconnect() {
        connection.disconnect();
    }

    /**
     * Returns the username associated with the profile.
     *
     * @return The username associated with the profile.
     */
    public String getUsername() {
        return username;
    }
}
