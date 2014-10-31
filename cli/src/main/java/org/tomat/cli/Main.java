package org.tomat.cli;

import io.airlift.command.*;
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
 * Created by Jose on 30/10/14.
 */
public class Main {
    public static void main(String[] args) {

        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("tomat")
                .withDescription("the stupid content tracker")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, Translate.class);

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

    @Command(name = "translate", description = "Add file contents to the index")
    public static class Translate extends TomatCommand {
        @Option(name = {"-o", "--out"}, description = "outputfile")
        public String output;

        @Arguments(description = "Patterns of files to be added")
        public String inputFile;

        private ToscaProcessor toscaProcessor;
        private AgnosticApplication agnosticApplication;
        private BrooklynTranslator brooklynTranslator = null;
        private BrooklynApplicationEntity brooklynApplicationEntity;

        public void run() {
            try {
                if (verbose){
                    System.out.println("verbose");
                }
                parsin();
                generateAgnostic();
                pringGraph();
                translate();
                print();
            }
            catch (NotSupportedTypeByTechnologyException e) {
                e.printStackTrace();
            }
            catch (NodeTemplateTypeNotSupportedException e) {
                e.printStackTrace();
            }
            catch (TopologyTemplateFormatException e) {
                e.printStackTrace();
            }
            catch (AgnosticPropertyException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
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
                System.out.println("printing... graph");
            }
            AgnosticGraphPrinter a=new AgnosticGraphJGraphPrinter(agnosticApplication.getAgnosticGraph());
            a.printGraph();
        }
    }

}

