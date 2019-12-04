/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

/**
 * Zusammenstellung derjenigen Objekttypen, für die eine
 * Wirkungsabschätzung (LCIA) durchgeführt werden kann.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.548
 */

public enum ObjectType {
	ProcessModule,
	ProcessModuleGroup,
	SubSystemGroup,
	ProductSystem, 
	ProductDeclaration,
	ProductDeclarationGroup
}
