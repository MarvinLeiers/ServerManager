package org.leiers.minecraft.commands.commands;

import org.leiers.minecraft.server.ServerManager;
import org.leiers.minecraft.commands.Command;
import org.leiers.minecraft.commands.CommandInfo;

@CommandInfo (
        name = "disable",
        description = "Disables program"
)
public class DisableCommand extends Command
{
    ServerManager serverManager = ServerManager.getInstance();

    @Override
    protected void execute(String[] args)
    {
        System.out.println("Disabling system...");
        serverManager.setEnabled(false);
    }
}
