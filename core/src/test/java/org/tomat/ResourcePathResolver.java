package org.tomat;

/**
 * Created by Kiuby88 on 31/10/14.
 */
public class ResourcePathResolver {

    final private static String TOPOLOGY_FOLDER ="toscaTopology/";
    final private static String YAML_FOLDER ="yaml/";
    final public static String AWS_APPLICATION_DATABASE_SAMPLE_JBOSS= TOPOLOGY_FOLDER +"AWS-Application-DatabaseSample-JBoss.xml";
    final public static String AWS_APPLICATION_DATABASE_SAMPLE_JETTY= TOPOLOGY_FOLDER +"AWS-Application-DatabaseSample-Jetty.xml";
    final public static String AWS_APPLICATION_DATABASE_SAMPLE_TOMCAT= TOPOLOGY_FOLDER +"AWS-Application-DatabaseSample-Tomcat.xml";
    final public static String AWS_ARTIFACT_TEMPLATE_DEFINITION= TOPOLOGY_FOLDER +"AWS-ArtifactTemplateDefinition.xml";
    final public static String AWS_DEPLOYMENT_ARTIFACT_WITHOUT_ARTIFACT_TEMPLATE = TOPOLOGY_FOLDER +"AWS-DeploymentArtifactWithoutArtifactTemplate.xml";
    final public static String AWS_LOCATION_SAMPLE= TOPOLOGY_FOLDER +"AWS-Location-Sample.xml";
    final public static String AWS_LOCATION_SAMPLE_MALFORMED_RELATION= TOPOLOGY_FOLDER +"AWS-Location-Sample-MalFormedRelation.xml";
    final public static String AWS_LOCATION_SAMPLE_UNSUPPORTED_TYPE= TOPOLOGY_FOLDER +"AWS-Location-Sample-Unsupported-Type.xml";
    final public static String AWS_NOT_DEPLOYMENT_ARTIFACT_LIST_DEFINED= TOPOLOGY_FOLDER +"AWS-NotDeploymentArtifactListDefined.xml";
    final public static String AWS_NOT_DEPLOYMENT_ARTIFACT_DEFINED= TOPOLOGY_FOLDER +"AWS-NotDeploymentArtifactsDefined.xml";
    final public static String AWS_NOT_NODE_TYPE_IMPLEMENTATION_DEFINED = TOPOLOGY_FOLDER +"AWS-NotNodeTypeImplementationDefined.xml";
    final public static String AWS_SEVERAL_CORRECT_DEPLOYMENT_ARTIFACT_DEFINITION= TOPOLOGY_FOLDER +"AWS-SeveralCorrectDeploymentArtifactsDefinition.xml";
    final public static String ONLINE_RETAILING_DEFINITIONS_CLOUD_TOPOLOGY_V2= TOPOLOGY_FOLDER +"OnlineRetailingDefinitions-cloud-topology-v2.0.xml";
    final public static String APP_DB_TOMCAT_YAML= YAML_FOLDER +"testDbAppTomcat.yaml";
    final public static String APP_DB_JETTY_YAML= YAML_FOLDER +"testDbAppJetty.yaml";
    final public static String APP_DB_JBOSS_YAML= YAML_FOLDER +"testDbAppJBoss.yaml";
    final public static String SIMPLE_DB_APP_YAML= YAML_FOLDER +"testSimpleDbApp.yaml";

    public String getPathOfFile(String file){
        return getClass().getClassLoader().getResource(file).getFile();
    }
}
