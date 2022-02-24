package org.leiers.minecraft.server.settings;

import java.io.File;

public class ServerSettings
{
    private final File jar;
    private final int minRam;
    private final int maxRam;
    private final String[] args;

    protected ServerSettings(File jar, int minRam, int maxRam, String... args)
    {
        this.jar = jar;
        this.minRam = minRam;
        this.maxRam = maxRam;
        this.args = args;
    }

    public File getJar()
    {
        return jar;
    }

    public int getMinRam()
    {
        return minRam;
    }

    public int getMaxRam()
    {
        return maxRam;
    }

    public String[] getArgs()
    {
        return args;
    }

    public static ServerSettings newServerSettings()
    {
        return newBuilder().build();
    }

    public static ServerSettingsBuilder newBuilder()
    {
        return new ServerSettingsBuilder();
    }

    public static class ServerSettingsBuilder
    {
        private static final int DEFAULT_MIN_RAM = 1024;
        private static final int DEFAULT_MAX_RAM = 2048;
        private static final boolean DEFAULT_SHOW_GUI = true;
        private static final String[] DEFAULT_ARGS = {"java", "-Xms%dM", "-Xmx%dM","-jar", "%s"};
        private static final String[] DEFAULT_CUSTOM_ARGS = {};
        private static final File DEFAULT_JAR = new File(System.getProperty("user.dir") + File.separator + "/spigot" +
                ".jar");

        private ServerSettings serverSettings;
        private int minRam = DEFAULT_MIN_RAM;
        private int maxRam = DEFAULT_MAX_RAM;
        private boolean showGui = DEFAULT_SHOW_GUI;
        private String[] customArgs = DEFAULT_CUSTOM_ARGS;
        private File jar = DEFAULT_JAR;

        public ServerSettingsBuilder jar(String path)
        {
            this.jar = new File(path);
            return this;
        }

        public ServerSettingsBuilder hideGui()
        {
            showGui = false;
            return this;
        }

        public ServerSettingsBuilder minRam(int minRam)
        {
            this.minRam = minRam;
            return this;
        }

        public ServerSettingsBuilder maxRam(int maxRam)
        {
            this.maxRam = maxRam;
            return this;
        }

        public ServerSettingsBuilder args(String... args)
        {
            this.customArgs = appendToArray(customArgs, args);
            return this;
        }

        public ServerSettingsBuilder port(int port)
        {
            this.customArgs = appendToArray(customArgs, "--port", String.valueOf(port));
            return this;
        }

        public ServerSettings build()
        {
            DEFAULT_ARGS[1] = DEFAULT_ARGS[2].formatted(minRam);
            DEFAULT_ARGS[2] = DEFAULT_ARGS[2].formatted(maxRam);
            DEFAULT_ARGS[4] = DEFAULT_ARGS[4].formatted(jar.getAbsolutePath());

            String[] args = DEFAULT_ARGS;

            if (!showGui)
                args = appendToArray(args, "nogui");

            args = appendToArray(args, customArgs);

            return new ServerSettings(jar, minRam, maxRam, args);
        }

        private String[] appendToArray(String[] arr, String... append)
        {
            String[] newArr = new String[arr.length + append.length];

            System.arraycopy(arr, 0, newArr, 0, arr.length);
            System.arraycopy(append, 0, newArr, arr.length, append.length);

            return newArr;
        }
    }
}
