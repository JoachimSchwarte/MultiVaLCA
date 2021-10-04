package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.LCIAMethod;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import de.unistuttgart.iwb.multivalca.ValueType;

public class ErgebnisseExport extends AbstractAction {
	public ErgebnisseExport(Language l) {
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		putValue(NAME, bundle.getString("mp61"));
		putValue(SHORT_DESCRIPTION, bundle.getString("mp61e"));
}
	public void actionPerformed(ActionEvent arg0) {
		
        Vector<String> methVektor = new Vector<String>();
		for (String methName : LCIAMethod.getAllInstances().keySet()) {
			methVektor.addElement(methName);
		}
        String [] options = methVektor.toArray(new String[methVektor.size()]);
        String info = (String)JOptionPane.showInputDialog(null,"Bitte wählen Sie die Methode：","提示",JOptionPane.QUESTION_MESSAGE,null,options,options[0]);      
        LCIAMethod akMeth = LCIAMethod.instance(info);
		
		Vector<String> ValueVektor = new Vector<String>();
		ValueVektor.addElement("Hauptwert");
		ValueVektor.addElement("Obergrenze");
		ValueVektor.addElement("Untergrenze");
		String [] options1 = ValueVektor.toArray(new String[ValueVektor.size()]);
		String info1 = (String)JOptionPane.showInputDialog(null,"Bitte wählen Sie Werttyp：","提示",JOptionPane.QUESTION_MESSAGE,null,options1,options1[0]);
		ValueType vt = ValueType.MeanValue;
		if(info1 == "Hauptwert") {
			vt = ValueType.MeanValue;
		}
		if(info1 == "Untergrenze") {
			vt = ValueType.LowerBound;
		}
		if(info1 == "Obergrenze") {
			vt = ValueType.UpperBound;
		}
		
		try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();       
            Element root = document.createElement("ProcessDataSet");
            document.appendChild(root);
            writeInfomartion(document, root);
            writeSachbilanz(document, root,vt);
			writeWirkungsabsätzung(document,root, akMeth,vt);

            
        	final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        	final int width = (int) screen.getWidth();
        	final int height = (int) screen.getHeight();
            final Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);
            
            JFileChooser chooser = new JFileChooser();
	        FileFilter filter = new FileNameExtensionFilter("XML-Dateien (*.xml)", "xml");
	        chooser.setFileFilter(filter);
	        chooserSetFont(chooser, generalfont);
	        chooser.setPreferredSize(new Dimension(width*40/100, height*50/100));

	        // Dialog zum Speichern von Dateien anzeigen
	        int rueckgabeWert = chooser.showDialog(null, "Datei speichern unter");
	        if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {	        	
	        	DOMSource domSource = new DOMSource(document);
	        	File fileOutput = chooser.getSelectedFile();
	        	if (!FilenameUtils.getExtension(fileOutput.getName()).equalsIgnoreCase("ProcessDataSet")) {
	        		fileOutput = new File(fileOutput.toString() + ".xml");  // append .xml if "foo.jpg.xml" is OK
	        	}
	        	
	        	StreamResult streamResult = new StreamResult(fileOutput);
	            TransformerFactory tf = TransformerFactory.newInstance();

	            Transformer serializer = tf.newTransformer();
	            serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
	            serializer.transform(domSource, streamResult);
	        		}                   
				} catch(ParserConfigurationException e) {
            e.printStackTrace();
         } 
				catch(Throwable e) {
            e.printStackTrace();
	} 		
}
	
	private void chooserSetFont(JComponent comp, Font f) {
		 synchronized(comp) {
	            comp.setFont(f);
	            for (int index = 0; index < comp.getComponentCount(); index++) {
	                java.awt.Component c = comp.getComponent(index);
	                if (c instanceof JComponent) {
	                    chooserSetFont((JComponent)c, f);
	                } //Ends if
	           } 
	      } 
	}
	
	public void writeInfomartion(Document document, Element root) {
		Element ProcessInformation = document.createElement("ProcessInformation");
        root.appendChild(ProcessInformation);
        Element dataSetInformation = document.createElement("dataSetInformation");
        ProcessInformation.appendChild(dataSetInformation);
        Element commonUUID = document.createElement("UUID");
        dataSetInformation.appendChild(commonUUID);
        UUID uuid = UUID.randomUUID(); 
        commonUUID.appendChild(document.createTextNode(uuid.toString()));
        Element name = document.createElement("name");
        dataSetInformation.appendChild(name);
        Element baseName = document.createElement("baseName");
        name.appendChild(baseName);
        baseName.setAttribute("xml:lang", "de");
        baseName.appendChild(document.createTextNode("String1")); 					
        Element treatmentStandardsRoutes =document.createElement("treatmentStandardsRoutes");   //    @Xu Sheng    To Do: alle String 1 - String 10 kann man später selbst ausfüllen
        name.appendChild(treatmentStandardsRoutes);
        treatmentStandardsRoutes.appendChild(document.createTextNode("String2"));
        Element mixAndLocationTypes =document.createElement("mixAndLocationTypes");
        name.appendChild(mixAndLocationTypes);
        mixAndLocationTypes.appendChild(document.createTextNode("String3"));
        Element functionalUnitFlowProperties =document.createElement("functionalUnitFlowProperties");
        name.appendChild(functionalUnitFlowProperties);
        functionalUnitFlowProperties.appendChild(document.createTextNode("String4")); 
        Element identifierOfSubDataSet = document.createElement("identifierOfSubDataSet");
        dataSetInformation.appendChild(identifierOfSubDataSet);        
        Element synonyms =document.createElement("synonyms");
        identifierOfSubDataSet.appendChild(synonyms);
    	synonyms.appendChild(document.createTextNode("String5"));       
    	Element complementingProcesses =document.createElement("complementingProcesses");
        identifierOfSubDataSet.appendChild(complementingProcesses);
        complementingProcesses.appendChild(document.createTextNode("String6"));                        
        Element referenceToComplementingProcess =document.createElement("referenceToComplementingProcess");
        complementingProcesses.appendChild(referenceToComplementingProcess);
        referenceToComplementingProcess.appendChild(document.createTextNode("String7"));        
        Element classificationInformation =document.createElement("classificationInformation");
        dataSetInformation.appendChild(classificationInformation);
        classificationInformation.appendChild(document.createTextNode("String8"));
        Element generalComment =document.createElement("generalComment");
        dataSetInformation.appendChild(generalComment);
        generalComment.appendChild(document.createTextNode("String9"));
        Element referenceToExternalDocumentation =document.createElement("referenceToExternalDocumentation");
        dataSetInformation.appendChild(referenceToExternalDocumentation);
        referenceToExternalDocumentation.appendChild(document.createTextNode("String10"));
        Element time =document.createElement("time");
        dataSetInformation.appendChild(time);
        Element referenceYear =document.createElement("referenceYear");
        time.appendChild(referenceYear);
        referenceYear.appendChild(document.createTextNode("2021"));
        Element dataSetValidUntil =document.createElement("dataSetValidUntil");
        time.appendChild(dataSetValidUntil);
        dataSetValidUntil.appendChild(document.createTextNode("2028"));        
        
        
        
        
	}
	
	
	public void writeSachbilanz(Document document, Element root,ValueType vt) {
		SameKeyLinkedHashMap<Flow,LinkedHashMap<String,Double>> map = new SameKeyLinkedHashMap<>();	
		if (ProductSystem.getAllInstances().size() > 0) {					
			for(String sysName : ProductSystem.getAllInstances().keySet()) {			
				ProductSystem sysAktuell = ProductSystem.getAllInstances().get(sysName);
				LinkedHashMap<Flow,LinkedHashMap<ValueType,Double>> efvAktuell = sysAktuell.getElementarflussvektor();
				LinkedHashMap<Flow,LinkedHashMap<ValueType,Double>> pfvAktuell = sysAktuell.getProduktflussvektor();
				try {
					if (efvAktuell.size() > 0) {
						for(Flow sysFluss : efvAktuell.keySet()){
						LinkedHashMap<String,Double> small = new LinkedHashMap<>();
						small.put(sysName, efvAktuell.get(sysFluss).get(vt));
						map.put(sysFluss, small);
						}
					}				
					if (pfvAktuell.size() > 0) {
						for(Flow sysFluss : pfvAktuell.keySet()){
								boolean ausgabe = false;
							if (sysAktuell.getVorUndKoppelprodukte().contains(sysFluss)) {
								ausgabe = true;
							}
							if (ausgabe) {
								LinkedHashMap<String,Double> small = new LinkedHashMap<>();
								small.put(sysName, efvAktuell.get(sysFluss).get(vt));
								map.put(sysFluss, small);
								}
							}
						} 
					} catch (ArithmeticException vz) {				
				}					 
			}
		}
		
		Element Exchanges = document.createElement("Exchanges");
        root.appendChild(Exchanges);
        if (map.size() > 0) {
			int i = 1;
			Element Exchange1 = document.createElement("Exchange");
			Exchanges.appendChild(Exchange1);
			Exchange1.setAttribute("dataSetInternalID", "0");
			Element referenceToFlowDataSET1= document.createElement("referenceToFlowDataSET");
			Exchange1.appendChild(referenceToFlowDataSET1);
			referenceToFlowDataSET1.setAttribute("uri", "null");
			referenceToFlowDataSET1.setAttribute("type", "flow data set");
			referenceToFlowDataSET1.setAttribute("refObjectid", "null");
			Element commonshortDescription1 = document.createElement("shortDescription");
			referenceToFlowDataSET1.appendChild(commonshortDescription1);
			for(String psn : ProductSystem.getAllInstances().keySet()) {
			for (Flow bvf : ProductSystem.getInstance(psn).getBedarfsvektor().keySet()) {
				commonshortDescription1.appendChild(document.createTextNode("" + bvf.getName() + " (" + 
					ProductSystem.getInstance(psn).getBedarfsvektor().get(bvf) + 
					" " + bvf.getEinheit()+")"));
				}
			}			
			commonshortDescription1.setAttribute("xml:lang", "de");
        	for(Flow Fluss :map.keySet() ){
				Element Exchange = document.createElement("Exchange");
				Exchanges.appendChild(Exchange);
				Exchange.setAttribute("dataSetInternalID", String.valueOf(i));
				i=i+1;
				Element referenceToFlowDataSET = document.createElement("referenceToFlowDataSET");
				Exchange.appendChild(referenceToFlowDataSET);				
				Element commonshortDescription = document.createElement("shortDescription");
				referenceToFlowDataSET.appendChild(commonshortDescription);
				commonshortDescription.appendChild(document.createTextNode(Fluss.getName()));
				commonshortDescription.setAttribute("xml:lang", "de");			
//				Element exchangeDirection = document.createElement("exchangeDirection");
//				Element referencesToDataSource = document.createElement("referencesToDataSource");
				Element commonother = document.createElement("other");
				Exchange.appendChild(commonother);
				Element epdreferenceToUnitGroupDataSet = document.createElement("referenceToUnitGroupDataSet");
				commonother.appendChild(epdreferenceToUnitGroupDataSet);
				epdreferenceToUnitGroupDataSet.setAttribute("uri", "null");
				epdreferenceToUnitGroupDataSet.setAttribute("type", "null");
				epdreferenceToUnitGroupDataSet.setAttribute("refObjectid", "null");
				Element commonshortDescription2 = document.createElement("shortDescription");
				epdreferenceToUnitGroupDataSet.appendChild(commonshortDescription2);
				commonshortDescription2.setAttribute("xml:lang", "de");
				commonshortDescription2.appendChild(document.createTextNode(Fluss.getEinheit().toString()));
				for(String sysName:map.get(Fluss).keySet() ) {					
					Element epdamount = document.createElement("amount");
					commonother.appendChild(epdamount);
					epdamount.setAttribute("module", sysName);
					epdamount.appendChild(document.createTextNode(map.get(Fluss).get(sysName).toString()));
				}
        	}	
        }	
	}
	
	
	
	
	
	public void writeWirkungsabsätzung(Document document, Element root,LCIAMethod akMeth,ValueType vt) {
		SameKeyLinkedHashMap<ImpactCategory,LinkedHashMap<String,Double>> map = new SameKeyLinkedHashMap<>();
		if (ProcessModule.getAllInstances().size() > 0) {					
			for(String sysName : ProcessModule.getAllInstances().keySet()) {	
				ProcessModule sysAktuell = ProcessModule.getAllInstances().get(sysName);
				for (ImpactCategory wName : sysAktuell.getImpactValueMap(akMeth).keySet()) {			
					LinkedHashMap<String,Double> small = new LinkedHashMap<>();
					small.put(sysName, sysAktuell.getImpactValueMap(akMeth).get(wName).get(vt));
					map.put(wName, small);		
					}
				}
			}		
			Element LCIAResults = document.createElement("LCIAResults");
			root.appendChild(LCIAResults);
			for(ImpactCategory ImpactCategory :map.keySet() ){
				Element LCIAResult = document.createElement("LCIAResult");
				LCIAResults.appendChild(LCIAResult);
				Element referenceToLCIAMethodDataSet = document.createElement("referenceToLCIAMethodDataSet");
				LCIAResult.appendChild(referenceToLCIAMethodDataSet);
				referenceToLCIAMethodDataSet.setAttribute("uri", "null");
				referenceToLCIAMethodDataSet.setAttribute("type", "null");
				referenceToLCIAMethodDataSet.setAttribute("refObjectid", "null");
				Element commonshortDescription = document.createElement("shortDescription");
				referenceToLCIAMethodDataSet.appendChild(commonshortDescription);
				commonshortDescription.setAttribute("xml:lang", "de");
				commonshortDescription.appendChild(document.createTextNode(ImpactCategory.getName()));
				Element commonother = document.createElement("other");
				LCIAResult.appendChild(commonother);
				Element epdreferenceToUnitGroupDataSet = document.createElement("referenceToUnitGroupDataSet");
				commonother.appendChild(epdreferenceToUnitGroupDataSet);
				epdreferenceToUnitGroupDataSet.setAttribute("uri", "null");
				epdreferenceToUnitGroupDataSet.setAttribute("type", "null");
				epdreferenceToUnitGroupDataSet.setAttribute("refObjectid", "null");
				Element commonshortDescription2 = document.createElement("shortDescription");
				epdreferenceToUnitGroupDataSet.appendChild(commonshortDescription2);
				commonshortDescription2.setAttribute("xml:lang", "de");
				commonshortDescription2.appendChild(document.createTextNode(ImpactCategory.getEinheit().getName()));
				for(String sysName:map.get(ImpactCategory).keySet() ) {
					Element epdamount = document.createElement("amount");
					commonother.appendChild(epdamount);
					epdamount.setAttribute("epdamount", sysName);
					epdamount.appendChild(document.createTextNode(map.get(ImpactCategory).get(sysName).toString()));
			}
		}
	}	
}
