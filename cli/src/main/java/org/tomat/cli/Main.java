package org.tomat.cli;

import io.airlift.command.*;

import java.util.List;

/**
 * Created by Jose on 30/10/14.
 */
public class Main
{
    public static void main( String[] args )
    {

        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("tomat")
                .withDescription("the stupid content tracker")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, Translate.class);

        Cli<Runnable> gitParser = builder.build();
        gitParser.parse(args).run();
    }


    public static class TomatCommand implements Runnable
    {
        @Option(type = OptionType.GLOBAL, name = {"-v", "--verbose"}, description = "Verbose mode")
        public boolean verbose;

        public void run()
        {
            System.out.println(getClass().getSimpleName());
        }
    }

    @Command(name = "translate", description = "Add file contents to the index")
    public static class Translate extends TomatCommand
    {
        @Arguments(description = "Patterns of files to be added")
        public List<String> patterns;

        @Option(name = "-i", description = "Add modified contents interactively.")
        public boolean interactive;
    }

}

