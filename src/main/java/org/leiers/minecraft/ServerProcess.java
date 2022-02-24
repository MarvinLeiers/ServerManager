package org.leiers.minecraft;

import org.leiers.minecraft.server.settings.ServerSettings;

import java.io.*;
import java.util.Scanner;

public class ServerProcess
{
    private static int nextServerId = 1;

    private final ServerSettings settings;
    private final int serverId;
    private Process process;
    private BufferedReader in;
    private BufferedWriter out;
    private boolean running;

    public ServerProcess(ServerSettings settings)
    {
        this.settings = settings;
        this.serverId = nextServerId++;
        this.running = false;
    }

    public void start()
    {
        this.running = true;
        this.process = runProcess();

        try (Scanner scanner = new Scanner(this.in))
        {
            scanner.useDelimiter("\\n");
            boolean done = false;

            while (!done && scanner.hasNext())
            {
                String line = scanner.nextLine();

                if (line.toLowerCase().contains("done"))
                {
                    scanner.close();
                    done = true;
                    System.out.println("Server " + serverId + " started successfully");
                    this.running = true;
                }
                else
                {
                    System.out.println(line);
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void stop()
    {
        runCommand("stop");
    }

    public boolean isRunning()
    {
        return running;
    }

    public Process getProcess()
    {
        return process;
    }

    public void runCommand(String command)
    {
        try
        {
            out.write(command);
            out.write("\n");
            out.flush();

            if (command.equalsIgnoreCase("stop"))
            {
                process.waitFor();
                running = false;
            }

            System.out.println("Successfully ran command '" + command + "' on server instance " + serverId);
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private Process runProcess()
    {
        ProcessBuilder builder = new ProcessBuilder(settings.getArgs());
        builder.directory(this.settings.getJar().getParentFile());
        
        Process process = null;

        try
        {
            process = builder.start();
        }
        catch (IOException ex)
        {
            System.out.println("Failed to start Process for " + serverId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (process != null)
        {
            this.out = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        }

        return process;
    }
}