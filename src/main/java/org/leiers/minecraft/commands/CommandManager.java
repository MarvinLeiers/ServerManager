package org.leiers.minecraft.commands;

import org.leiers.minecraft.server.ServerManager;

import java.util.HashMap;
import java.util.Scanner;

public class CommandManager extends Thread
{
    private static final HashMap<String, Command> commands = new HashMap<>();

    private final ServerManager serverManager;
    private final Scanner scanner;

    public CommandManager(Scanner scanner)
    {
        serverManager = ServerManager.getInstance();
        this.scanner = scanner;
    }

    public void addCommand(Command command)
    {
        CommandInfo commandInfo = command.getClass().getAnnotation(CommandInfo.class);

        if (commandInfo == null)
            throw new UnsupportedOperationException("Command not annotated with @CommandInfo.");

        commands.put(commandInfo.name(), command);
    }

    @Override
    public void run()
    {

        while (serverManager.isEnabled())
        {
            if (!scanner.hasNext())
                continue;

            String line = scanner.nextLine();
            String name = line.split(" ")[0].toLowerCase();
            Command command = commands.get(name);

            if (command != null)
                command.execute(line.split(" "));

        }
    }
}
