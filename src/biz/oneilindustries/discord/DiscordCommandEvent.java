package biz.oneilindustries.discord;

import biz.oneilindustries.discord.commands.Command;
import biz.oneilindustries.discord.commands.CommandManager;
import java.util.ArrayList;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DiscordCommandEvent {

    private MessageReceivedEvent message;

    public DiscordCommandEvent(MessageReceivedEvent message) {
        this.message = message;
        if (Rank.getDiscordServerRoles() == null) Rank.setDiscordServerRoles(message.getGuild().getRoles());
    }

    public void processEvent() {
        String[] commandDetails = message.getMessage().getContentRaw().split(" +");

        //Create the command manager object to determine if the command entered is defined
        CommandManager commandManager = new CommandManager();
        Command command = commandManager.isCommand(commandDetails[0]);

        //Creates array of the roles of the person calling the command
        ArrayList<String> invokerRoles = new ArrayList<>();

        for (Role role : message.getMember().getRoles()) {
            invokerRoles.add(role.getName().toLowerCase());
        }

        //Determines if the command exists. If not displays help
        if (command != null) {
            //Holds the user's name and unique id and the service the command was called from
            String[] usernameDetails = {message.getAuthor().getName(), message.getMessage().getAuthor().getId()};

            //Runs the command and gets return message if the command returns one
            String returnMessage = command.run(invokerRoles,commandDetails,usernameDetails);

            if (returnMessage != null) {
                writeDiscordMessage(returnMessage);
            }
        }else {
            // display help message info
            boolean isOfficer = Rank.isOfficer(invokerRoles);

            //Generate help command from existing commands
            String helpMessage = commandManager.generateHelp("user");
            if (isOfficer) {
                helpMessage += commandManager.generateHelp("officer");
            }
            writeDiscordMessage(helpMessage);
        }
    }

    private void writeDiscordMessage(String text) {
        this.message.getChannel().sendMessage(text).queue();
    }
}