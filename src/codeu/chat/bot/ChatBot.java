package codeu.chat.bot;

import codeu.chat.client.ClientContext;
import codeu.chat.client.Controller;
import codeu.chat.client.View;

/**
 * ChatBot is very similar to the Chat class, it handles commands. The difference is there's no
 * user on the other side giving the commands to be handled, so we feed specific
 * inputs to it instead of letting a user decide. We also return things as strings
 * instead of printing them so we have output to work with
 **/
public final class ChatBot{
    private final ClientContext clientContext;

    public ChatBot(Controller controller, View view){
        clientContext = new ClientContext(controller,view);
    }

    // Returns a formatted string of all users in the system
    // Used for the equivalent u-list-all command
    public String getAllUsers(){
        return clientContext.user.getAllUsers();
    }
    // Returns each conversation name, in order, by name on new lines
    // Used for the equivalent to c-list-all command
    public String getAllConvos(){
        return clientContext.conversation.getAllConversations();
    }

    // User commands
    // Sign in User
    public void signInUser(String name){
        if (!clientContext.user.signInUser(name)) {
            System.out.println("Error: sign in failed (invalid name?)");
        }
    }
    // Sign out user
    public void signOutUser() {
        if (!clientContext.user.signOutUser()) {
            System.out.println("Error: sign out failed (not signed in?)");
        }
    }
    // Add a new user.
    public void addUser(String name) {
        clientContext.user.addUser(name);
    }

    // Adds a new conversation with the name convoname
    public void addConvo(String convoname){
        if (!clientContext.user.hasCurrent()) {
            System.out.println("ERROR: Not signed in.");
        } else {
                clientContext.conversation.startConversation(convoname, clientContext.user.getCurrent().id);
            }
    }

    public void joinConvo(String convoname){
        return;
    }

    // (TODO for highlighting)
    // TODO implement commands
    // exit (Same as client)
    // TODO current
    // c-add <title> (Same as client)
    // c-select <index> (Same as client)
    // m-add <body> (Same as client)
    // TODO ONE of the following: (?)
        // m-list-all
        // m-next <index>
        // m-show <count>
}
