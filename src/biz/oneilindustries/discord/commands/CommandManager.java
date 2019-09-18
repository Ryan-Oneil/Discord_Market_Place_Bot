package biz.oneilindustries.discord.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<Command> commands;

    public CommandManager() {
        commands = new ArrayList<>();
        commands.add(new Register());
        commands.add(new Details());
        commands.add(new Verify());
        commands.add(new UnVerify());
    }

    public Command isCommand(String name) {

        for (Command command : this.commands) {
            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }

    public String generateHelp(String role) {
        StringBuilder helpMessage = new StringBuilder();

        for (Command command : this.commands) {
            if (command.getRequiredRole().equals(role)) {
                helpMessage.append(command.getName());
                helpMessage.append(":\n\tDescription: ");
                helpMessage.append(command.getHelp());
                helpMessage.append("\n\tUsage: ");
                helpMessage.append(command.getArgs());
                helpMessage.append("\n");
            }
        }
        return helpMessage.toString();
    }
}