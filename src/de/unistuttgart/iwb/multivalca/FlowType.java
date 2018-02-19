/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

/**
 * Zusammenstellung derjenigen Werte, die der Parameter "typ"
 * eines Flussobjekts annehmen darf.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.2
 */

public enum FlowType {
	ElementaryInput,
	ElementaryOutput,
	ProductOutput,
	PreProductInput,
	CoProductOutput
}
