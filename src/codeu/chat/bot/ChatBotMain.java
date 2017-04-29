package codeu.chat.bot;

import codeu.chat.client.ClientContext;
import codeu.chat.client.Controller;
import codeu.chat.client.View;
import codeu.chat.client.commandline.Chat;
import codeu.chat.util.Logger;
import codeu.chat.util.RemoteAddress;
import codeu.chat.util.connections.ClientConnectionSource;
import codeu.chat.util.connections.ConnectionSource;
import java.io.IOException;


public class ChatBotMain {
    private static final Logger.Log LOG = Logger.newLog(ChatBotMain.class);
    static String name = "chatbot";
    public static void main(String[]args){
        try{
            Logger.enableFileOutput("chat_bot_log.log");
        }catch(IOException ex){
            LOG.error(ex, "Failed to set logger to write to file");
        }
        LOG.info("============================= START OF LOG =============================");

        LOG.info("Starting ChatBotMain . . .");

        final RemoteAddress address = RemoteAddress.parse(args[0]); // retrieve address to connect to
        final ConnectionSource source = new ClientConnectionSource(address.host,address.port);
        final Controller controller = new Controller(source);
        final View view = new View(source);


        // Not sure if this chat is necessary yet
        LOG.info("Creating ChatBotMain . . .");
        // We'll use this chatbot object (which is much like a Chat object)
        // to do the things that Chat objects normally do
        final ChatBot chatbot = new ChatBot(controller,view);
        LOG.info("ChatBotMain created.");

        // Signing in and joining conversation
        if(chatbot.getAllUsers().contains(name)){
            chatbot.signInUser(name);
        }else{
            chatbot.addUser(name);
            chatbot.signInUser(name);
        }
        if(chatbot.getAllConvos().contains(name)){
            chatbot.joinConvo(name);
        }else{
            chatbot.addConvo(name);
            chatbot.joinConvo(name);
        }


        // Debugging stuff
        System.out.println(chatbot.getAllConvos());
        System.out.println(chatbot.getAllUsers());

        // This is where the chat bot should do its main logic loop stuff (echo to start)
        // TODO ECHO
        while(true){
            // do stuff in here
            break;
        }

        LOG.info("ChatBotMain has exited");
    }
}
