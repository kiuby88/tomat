//
// TOSCA version: TOSCA-v1.0-cs02.xsd
//

package org.opentosca.model.tosca;

public interface IToscaModelFactory {
	
	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TDefinitions }
	 *
	 */
	public TDefinitions createTDefinitions();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipTemplate }
	 *
	 */
	public TRelationshipTemplate createTRelationshipTemplate();

	/**
	 * Create an instance of
	 * {@link org.opentosca.model.tosca.TRelationshipTemplate.RelationshipConstraints }
	 *
	 */
	public TRelationshipTemplate.RelationshipConstraints createTRelationshipTemplateRelationshipConstraints();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRequirementDefinition }
	 *
	 */
	public TRequirementDefinition createTRequirementDefinition();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeTemplate }
	 *
	 */
	public TNodeTemplate createTNodeTemplate();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TTopologyElementInstanceStates }
	 *
	 */
	public TTopologyElementInstanceStates createTTopologyElementInstanceStates();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TAppliesTo }
	 *
	 */
	public TAppliesTo createTAppliesTo();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TImplementationArtifacts }
	 *
	 */
	public TImplementationArtifacts createTImplementationArtifacts();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TArtifactReference }
	 *
	 */
	public TArtifactReference createTArtifactReference();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TBoundaryDefinitions }
	 *
	 */
	public TBoundaryDefinitions createTBoundaryDefinitions();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TBoundaryDefinitions.Properties }
	 *
	 */
	public TBoundaryDefinitions.Properties createTBoundaryDefinitionsProperties();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TExportedOperation }
	 *
	 */
	public TExportedOperation createTExportedOperation();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPlan }
	 *
	 */
	public TPlan createTPlan();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TCapabilityDefinition }
	 *
	 */
	public TCapabilityDefinition createTCapabilityDefinition();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TOperation }
	 *
	 */
	public TOperation createTOperation();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TArtifactTemplate }
	 *
	 */
	public TArtifactTemplate createTArtifactTemplate();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipTypeImplementation }
	 *
	 */
	public TRelationshipTypeImplementation createTRelationshipTypeImplementation();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipType }
	 *
	 */
	public TRelationshipType createTRelationshipType();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeTypeImplementation }
	 *
	 */
	public TNodeTypeImplementation createTNodeTypeImplementation();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeType }
	 *
	 */
	public TNodeType createTNodeType();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TDocumentation }
	 *
	 */
	public TDocumentation createTDocumentation();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.Definitions }
	 *
	 */
	public Definitions createDefinitions();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TExtensibleElements }
	 *
	 */
	public TExtensibleElements createTExtensibleElements();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TDefinitions.Extensions }
	 *
	 */
	public TDefinitions.Extensions createTDefinitionsExtensions();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TImport }
	 *
	 */
	public TImport createTImport();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TDefinitions.Types }
	 *
	 */
	public TDefinitions.Types createTDefinitionsTypes();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TServiceTemplate }
	 *
	 */
	public TServiceTemplate createTServiceTemplate();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRequirementType }
	 *
	 */
	public TRequirementType createTRequirementType();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TCapabilityType }
	 *
	 */
	public TCapabilityType createTCapabilityType();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TArtifactType }
	 *
	 */
	public TArtifactType createTArtifactType();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPolicyType }
	 *
	 */
	public TPolicyType createTPolicyType();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPolicyTemplate }
	 *
	 */
	public TPolicyTemplate createTPolicyTemplate();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRequirementRef }
	 *
	 */
	public TRequirementRef createTRequirementRef();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TImplementationArtifact }
	 *
	 */
	public TImplementationArtifact createTImplementationArtifact();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TParameter }
	 *
	 */
	public TParameter createTParameter();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TExtension }
	 *
	 */
	public TExtension createTExtension();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TDeploymentArtifact }
	 *
	 */
	public TDeploymentArtifact createTDeploymentArtifact();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRequiredContainerFeatures }
	 *
	 */
	public TRequiredContainerFeatures createTRequiredContainerFeatures();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TExportedInterface }
	 *
	 */
	public TExportedInterface createTExportedInterface();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TConstraint }
	 *
	 */
	public TConstraint createTConstraint();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPlans }
	 *
	 */
	public TPlans createTPlans();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPropertyMapping }
	 *
	 */
	public TPropertyMapping createTPropertyMapping();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TCondition }
	 *
	 */
	public TCondition createTCondition();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TExtensions }
	 *
	 */
	public TExtensions createTExtensions();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TTopologyTemplate }
	 *
	 */
	public TTopologyTemplate createTTopologyTemplate();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TTags }
	 *
	 */
	public TTags createTTags();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPolicy }
	 *
	 */
	public TPolicy createTPolicy();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRequiredContainerFeature }
	 *
	 */
	public TRequiredContainerFeature createTRequiredContainerFeature();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TDeploymentArtifacts }
	 *
	 */
	public TDeploymentArtifacts createTDeploymentArtifacts();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TCapability }
	 *
	 */
	public TCapability createTCapability();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPropertyConstraint }
	 *
	 */
	public TPropertyConstraint createTPropertyConstraint();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TTag }
	 *
	 */
	public TTag createTTag();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TInterface }
	 *
	 */
	public TInterface createTInterface();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRequirement }
	 *
	 */
	public TRequirement createTRequirement();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TCapabilityRef }
	 *
	 */
	public TCapabilityRef createTCapabilityRef();

	/**
	 * Create an instance of {@link model.TEntityTemplate.Properties }
	 *
	 */
	public TEntityTemplate.Properties createTEntityTemplateProperties();

	/**
	 * Create an instance of {@link model.TEntityTemplate.PropertyConstraints }
	 *
	 */
	public TEntityTemplate.PropertyConstraints createTEntityTemplatePropertyConstraints();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipTemplate.SourceElement }
	 *
	 */
	public TRelationshipTemplate.SourceElement createTRelationshipTemplateSourceElement();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipTemplate.TargetElement }
	 *
	 */
	public TRelationshipTemplate.TargetElement createTRelationshipTemplateTargetElement();

	/**
	 * Create an instance of
	 * {@link org.opentosca.model.tosca.TRelationshipTemplate.RelationshipConstraints.RelationshipConstraint }
	 *
	 */
	public TRelationshipTemplate.RelationshipConstraints.RelationshipConstraint createTRelationshipTemplateRelationshipConstraintsRelationshipConstraint();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRequirementDefinition.Constraints }
	 *
	 */
	public TRequirementDefinition.Constraints createTRequirementDefinitionConstraints();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeTemplate.Requirements }
	 *
	 */
	public TNodeTemplate.Requirements createTNodeTemplateRequirements();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeTemplate.Capabilities }
	 *
	 */
	public TNodeTemplate.Capabilities createTNodeTemplateCapabilities();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeTemplate.Policies }
	 *
	 */
	public TNodeTemplate.Policies createTNodeTemplatePolicies();

	/**
	 * Create an instance of
	 * {@link org.opentosca.model.tosca.TTopologyElementInstanceStates.InstanceState }
	 *
	 */
	public TTopologyElementInstanceStates.InstanceState createTTopologyElementInstanceStatesInstanceState();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TAppliesTo.NodeTypeReference }
	 *
	 */
	public TAppliesTo.NodeTypeReference createTAppliesToNodeTypeReference();

	/**
	 * Create an instance of
	 * {@link org.opentosca.model.tosca.TImplementationArtifacts.ImplementationArtifact }
	 *
	 */
	public TImplementationArtifacts.ImplementationArtifact createTImplementationArtifactsImplementationArtifact();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TArtifactReference.Include }
	 *
	 */
	public TArtifactReference.Include createTArtifactReferenceInclude();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TArtifactReference.Exclude }
	 *
	 */
	public TArtifactReference.Exclude createTArtifactReferenceExclude();

	/**
	 * Create an instance of {@link model.TEntityType.DerivedFrom }
	 *
	 */
	public TEntityType.DerivedFrom createTEntityTypeDerivedFrom();

	/**
	 * Create an instance of {@link model.TEntityType.PropertiesDefinition }
	 *
	 */
	public TEntityType.PropertiesDefinition createTEntityTypePropertiesDefinition();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TBoundaryDefinitions.PropertyConstraints }
	 *
	 */
	public TBoundaryDefinitions.PropertyConstraints createTBoundaryDefinitionsPropertyConstraints();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TBoundaryDefinitions.Requirements }
	 *
	 */
	public TBoundaryDefinitions.Requirements createTBoundaryDefinitionsRequirements();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TBoundaryDefinitions.Capabilities }
	 *
	 */
	public TBoundaryDefinitions.Capabilities createTBoundaryDefinitionsCapabilities();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TBoundaryDefinitions.Policies }
	 *
	 */
	public TBoundaryDefinitions.Policies createTBoundaryDefinitionsPolicies();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TBoundaryDefinitions.Interfaces }
	 *
	 */
	public TBoundaryDefinitions.Interfaces createTBoundaryDefinitionsInterfaces();

	/**
	 * Create an instance of
	 * {@link org.opentosca.model.tosca.TBoundaryDefinitions.Properties.PropertyMappings }
	 *
	 */
	public TBoundaryDefinitions.Properties.PropertyMappings createTBoundaryDefinitionsPropertiesPropertyMappings();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TExportedOperation.NodeOperation }
	 *
	 */
	public TExportedOperation.NodeOperation createTExportedOperationNodeOperation();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TExportedOperation.RelationshipOperation }
	 *
	 */
	public TExportedOperation.RelationshipOperation createTExportedOperationRelationshipOperation();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TExportedOperation.Plan }
	 *
	 */
	public TExportedOperation.Plan createTExportedOperationPlan();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPlan.InputParameters }
	 *
	 */
	public TPlan.InputParameters createTPlanInputParameters();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPlan.OutputParameters }
	 *
	 */
	public TPlan.OutputParameters createTPlanOutputParameters();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPlan.PlanModel }
	 *
	 */
	public TPlan.PlanModel createTPlanPlanModel();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TPlan.PlanModelReference }
	 *
	 */
	public TPlan.PlanModelReference createTPlanPlanModelReference();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TCapabilityDefinition.Constraints }
	 *
	 */
	public TCapabilityDefinition.Constraints createTCapabilityDefinitionConstraints();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TOperation.InputParameters }
	 *
	 */
	public TOperation.InputParameters createTOperationInputParameters();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TOperation.OutputParameters }
	 *
	 */
	public TOperation.OutputParameters createTOperationOutputParameters();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TArtifactTemplate.ArtifactReferences }
	 *
	 */
	public TArtifactTemplate.ArtifactReferences createTArtifactTemplateArtifactReferences();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipTypeImplementation.DerivedFrom }
	 *
	 */
	public TRelationshipTypeImplementation.DerivedFrom createTRelationshipTypeImplementationDerivedFrom();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipType.SourceInterfaces }
	 *
	 */
	public TRelationshipType.SourceInterfaces createTRelationshipTypeSourceInterfaces();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipType.TargetInterfaces }
	 *
	 */
	public TRelationshipType.TargetInterfaces createTRelationshipTypeTargetInterfaces();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipType.ValidSource }
	 *
	 */
	public TRelationshipType.ValidSource createTRelationshipTypeValidSource();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TRelationshipType.ValidTarget }
	 *
	 */
	public TRelationshipType.ValidTarget createTRelationshipTypeValidTarget();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeTypeImplementation.DerivedFrom }
	 *
	 */
	public TNodeTypeImplementation.DerivedFrom createTNodeTypeImplementationDerivedFrom();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeType.RequirementDefinitions }
	 *
	 */
	public TNodeType.RequirementDefinitions createTNodeTypeRequirementDefinitions();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeType.CapabilityDefinitions }
	 *
	 */
	public TNodeType.CapabilityDefinitions createTNodeTypeCapabilityDefinitions();

	/**
	 * Create an instance of {@link org.opentosca.model.tosca.TNodeType.Interfaces }
	 * 
	 */
	public TNodeType.Interfaces createTNodeTypeInterfaces();
	
}