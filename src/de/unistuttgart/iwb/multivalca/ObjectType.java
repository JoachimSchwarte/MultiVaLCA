/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

/**
 * Zusammenstellung derjenigen Objekttypen, für die eine
 * Wirkungsabschätzung (LCIA) durchgeführt werden kann.
 * 
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public enum ObjectType {
	ProcessModule,
	ProcessModuleGroup,	
	ProductSystem, 
	ProductSystemGroup,
	ProductDeclaration,
	ProductDeclarationGroup, 
	Component, 
	ComponentGroup,
	Composition,
	CompositionGroup
}
