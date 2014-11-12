package org.tomat.cli;

import io.airlift.command.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomat.TomatVersion;
import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.graphs.printer.AgnosticGraphJGraphPrinter;
import org.tomat.agnostic.graphs.printer.AgnosticGraphPrinter;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaProcessor;
import org.tomat.translate.brooklyn.BrooklynTranslator;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

import java.io.IOException;

/**
 * Created by Kiuby88 on 30/10/14.
 */
public class Main {

   static Logger logger = LoggerFactory.getLogger(Main.class);

   final static private  String TOMAT_BANNER=
            " _______                        _______          \n" +
                    "|__   __|___  __  __  ___   ___|__   __|         \n"+
                    "   | |  / _ \\|  |/  \\/   \\ / _ \\  | |        \n"+
                    "   | | | (_) |   /|   /\\  | (_) | | |           \n"+
                    "   |_|  \\___/|__| |__| |__|\\__/\\_\\|_|"+(new TomatVersion()).getVersion();

    public static void main(String[] args) {

        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("tomat")
                .withDescription("the stupid content tracker")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, Translate.class, Info.class);

        Cli<Runnable> gitParser = builder.build();
        gitParser.parse(args).run();
    }

    public static class TomatCommand implements Runnable {
        @Option(type = OptionType.GLOBAL, name = {"-v", "--verbose"}, description = "Verbose mode")
        public boolean verbose;

        public void run() {
            System.out.println(getClass().getSimpleName());
        }
    }

    @Command(name = "translate", description = "Translate a TOSCA topology to CAMP using the Brooklyn API")
    public static class Translate extends TomatCommand {
        @Option(name = {"-o", "--out"}, description = "Output CAMP (Brooklyn) yaml file ")
        public String output;

        @Arguments(description = "Patterns of files to be added")
        public String inputFile;

        private ToscaProcessor toscaProcessor;
        private AgnosticApplication agnosticApplication;
        private BrooklynTranslator brooklynTranslator = null;
        private BrooklynApplicationEntity brooklynApplicationEntity;

        public void run() {
            try {
                parsin();
                generateAgnostic();
                pringGraph();
                translate();
                print();
            }
            catch (Exception e){
                logger.error("Translating error: \n",e);
            }
        }

        private void parsin()
                throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException {
            System.out.println("InputFile: -- "+inputFile);
            toscaProcessor = new ToscaProcessor();
            toscaProcessor
                    .parsingApplicationTopology(inputFile);
        }

        private void generateAgnostic()
                throws AgnosticPropertyException, TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
            toscaProcessor.buildAgnostics();
            agnosticApplication = new AgnosticApplication(toscaProcessor);
        }

        private void translate() throws NotSupportedTypeByTechnologyException {
            brooklynTranslator = new BrooklynTranslator(agnosticApplication)
                    .translate();
            brooklynApplicationEntity = brooklynTranslator.getBrooklynApplicationEntity();
        }

        private void print() throws IOException {
            brooklynTranslator.print(output);
        }

        private void pringGraph(){
            if(verbose){
                System.out.println("printing graph...");
                AgnosticGraphPrinter a = new AgnosticGraphJGraphPrinter(agnosticApplication.getAgnosticGraph());
                a.printGraph();
            }
        }
    }

    @Command(name = "info", description = "Show tomat information")
    public static class Info extends TomatCommand {

        private TomatVersion tomatVersion=new TomatVersion();

        public void run() {

            System.out.println(TOMAT_BANNER+"\n\n");


        }
    }






}

