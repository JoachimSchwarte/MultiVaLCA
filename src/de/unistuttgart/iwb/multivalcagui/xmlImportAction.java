/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcagui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.unistuttgart.iwb.multivalca.*;


/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.31
 */

class xmlImportAction extends AbstractAction {

	private static final long serialVersionUID = -8752103520306217094L;
	public xmlImportAction(Language l) {
		putValue(NAME, GuiStrings.getGS("mp62", l));
		putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp62e", l));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("XML-Dateien (*.xml)", "xml");
        chooser.setFileFilter(filter);
        
        // Dialog zum Laden von Dateien anzeigen
        int rueckgabeWert = chooser.showOpenDialog(null);
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {
        	File fileInput = chooser.getSelectedFile();
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				try {
					Document dom = db.parse(fileInput);
					Element docEle = dom.getDocumentElement();
					
					NameCheck.clear();
					
					Flow.clear();
					NodeList nl = docEle.getElementsByTagName("Flow");
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String flussname = "";
						String flusstyp = "";
						String flusseinheit = "";
						for (int j = 0; j < nlc.getLength(); j++) {
							if (nlc.item(j).getNodeName().equals("FlowName")) {
								flussname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("FlowType")) {
								flusstyp = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("FlowUnit")) {
								flusseinheit = nlc.item(j).getTextContent();
							}	
						}
						FlowType ft = FlowType.valueOf(flusstyp);
						FlowUnit fe = FlowUnit.valueOf(flusseinheit);
						Flow.instance(flussname, ft, fe);
					}				
					
				} catch (SAXException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			} catch (ParserConfigurationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        }
        
	}

}
