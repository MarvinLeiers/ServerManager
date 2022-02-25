package org.leiers.minecraft.server;

import org.leiers.minecraft.commands.CommandManager;
import org.leiers.minecraft.commands.commands.DisableCommand;
import org.leiers.minecraft.commands.commands.StartServer;

import java.util.Scanner;

public class ServerManager
{
    //TODO: Create server manager and shutdown servers on program end.

    private static ServerManager instance;
    private boolean enabled;

    public ServerManager()
    {
        instance = this;
        this.enabled = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Done.");

        CommandManager commandManager = new CommandManager(scanner);
        commandManager.addCommand(new DisableCommand());
        commandManager.addCommand(new StartServer());
        commandManager.start();

        while (isEnabled())
        {
            try
            {
                Thread.sleep(400);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        // TODO: stop running servers.

        System.out.println("Exiting program.");
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public synchronized void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public static ServerManager getInstance()
    {
        return instance;
    }

    public static void main(String[] args)
    {
        ServerManager serverManager = new ServerManager();
    }
}
