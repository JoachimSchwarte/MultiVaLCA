/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

/**
 * Zusammenstellung derjenigen Objekttypen, f�r die eine
 * Wirkungsabsch�tzung (LCIA) durchgef�hrt werden kann.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.548
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
