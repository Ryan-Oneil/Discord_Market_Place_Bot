package biz.oneilindustries.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordCommandListener extends ListenerAdapter {

    private static final String PREFIX = "!";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) throws NullPointerException {

        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();

        //Processes command given by user
        if (message.startsWith(PREFIX)) {
            if (event.getGuild() == null || !event.getGuild().getId().equals(DiscordBot.getGuild().getId())) {
                event.getChannel().sendMessage("Commands are not supported here!").queue();
                return;
            }
            new DiscordCommandEvent(event).processEvent();
        }
    }

}