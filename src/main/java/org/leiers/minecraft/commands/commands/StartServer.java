package org.leiers.minecraft.commands.commands;

import org.leiers.minecraft.ServerProcess;
import org.leiers.minecraft.commands.Command;
import org.leiers.minecraft.commands.CommandInfo;
import org.leiers.minecraft.server.settings.ServerSettings;

import java.util.Arrays;

@CommandInfo (
        name = "startserver",
        description = "Starts a papermc server instance"
)
public class StartServer extends Command
{
    @Override
    protected void execute(String[] args)
    {
        ServerSettings.ServerSettingsBuilder builder = ServerSettings.newBuilder()
                .jar("C:\\Users\\Marvin\\MC Server\\1.18.1 paper\\paper.jar")
                .minRam(128)
                .maxRam(1024);

        if (args.length > 1)
            builder.args(Arrays.copyOfRange(args, 1, args.length));

        ServerSettings settings = builder.build();

        System.out.println(Arrays.toString(settings.getArgs()));

        ServerProcess serverProcess = new ServerProcess(settings);
        serverProcess.start();
    }
}
