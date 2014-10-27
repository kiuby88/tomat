package org.tomat.translate.brooklyn.print;

import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.JBossBrooklynService;
import org.tomat.translate.brooklyn.entity.MySQLBrooklynService;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Jose on 27/10/14.
 */
public class BrooklynYamlPrinter {

    private Yaml yaml =null;


    public BrooklynYamlPrinter(){

        DumperOptions options= getDumperOptions();
        Representer representer= getEmptyAndNullRepresenter();
        configurePropertiesOrder(representer);
        configureTags(representer);

        yaml=new Yaml(representer, options);
    }

    private DumperOptions getDumperOptions(){
        DumperOptions options= new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        return options;
    }

    private Representer getEmptyAndNullRepresenter(){
        SkipEmptyAndNullRepresenter skipEmptyAndNullRepresenter=new SkipEmptyAndNullRepresenter();
        skipEmptyAndNullRepresenter.setPropertyUtils(new PropertyOrderDefinition());
        return skipEmptyAndNullRepresenter;
    }
    
    private Representer configurePropertiesOrder(Representer representer){
        representer.setPropertyUtils(new PropertyOrderDefinition());
        return representer;
    }

    private Representer configureTags(Representer representer){
        representer.addClassTag(JBossBrooklynService.class, Tag.MAP);
        representer.addClassTag(MySQLBrooklynService.class, Tag.MAP);
        representer.addClassTag(BrooklynApplicationEntity.class, Tag.MAP);
        return representer;
    }


    public String print(Object o)
            throws IOException {
        StringWriter writer= new StringWriter();
        print(o,writer);
        return writer.toString();
    }

    public void print(Object o, String file)
            throws IOException {
        FileWriter fW=new FileWriter(file);
        print(o, fW);
        fW.close();
    }

    public void print(Object o, Writer writer)
            throws IOException {
        dumping(o,writer);
        //yaml.dump(o, writer);
    }

    private void dumping(Object o, Writer writer)
            throws IOException {
        StringWriter writeForChanging = new StringWriter();
        yaml.dump(o,writeForChanging);
        String bufferForChanging=writeForChanging.toString();
        bufferForChanging=bufferForChanging.replace("brooklynConfigProperties:","brooklyn.config:");
        writer.write(bufferForChanging);
    }


}
