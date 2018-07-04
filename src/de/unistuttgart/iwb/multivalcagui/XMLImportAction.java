/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

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
 * @version 0.531
 */

class XMLImportAction extends AbstractAction {

	private static final long serialVersionUID = -8752103520306217094L;
	public XMLImportAction(Language l) {
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		putValue(NAME, bundle.getString("mp62"));
		putValue(SHORT_DESCRIPTION, bundle.getString("mp62e"));
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
					
					// Erster Durchgang:
					// Erzeugen der Objekte
					
					NodeList nl = docEle.getElementsByTagName("Flow");
					if (nl.getLength() != 0) {
						MCAObject.clear(Flow.class);
					}
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
									
					nl = docEle.getElementsByTagName("ProcessModule");
					if (nl.getLength() != 0) {
						MCAObject.clear(ProcessModule.class);
					}
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String pmname = "";	
						String fvename = "";	
						String fvemenge = "";
						LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> pmfv = new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>();						
						for (int j = 0; j < nlc.getLength(); j++) {							
							if (nlc.item(j).getNodeName().equals("ModuleName")) {									
								pmname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("ElementaryFlowVector")) {									
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("EFV-Entry")) {											
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("EFV-Name")) {
												fvename = nlc3.item(l).getTextContent();
											}
											if (nlc3.item(l).getNodeName().equals("EFV-MainValue")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = new LinkedHashMap<ValueType, Double>();
												vt.put(ValueType.MeanValue, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
											if (nlc3.item(l).getNodeName().equals("EFV-LowerBound")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = pmfv.get(akFluss);
												vt.put(ValueType.LowerBound, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
											if (nlc3.item(l).getNodeName().equals("EFV-UpperBound")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = pmfv.get(akFluss);
												vt.put(ValueType.UpperBound, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
										}											
									}
								}
							}
							if (nlc.item(j).getNodeName().equals("ProductFlowVector")) {									
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("PFV-Entry")) {											
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("PFV-Name")) {
												fvename = nlc3.item(l).getTextContent();
											}
											if (nlc3.item(l).getNodeName().equals("PFV-MainValue")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = new LinkedHashMap<ValueType, Double>();
												vt.put(ValueType.MeanValue, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
											if (nlc3.item(l).getNodeName().equals("PFV-LowerBound")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = pmfv.get(akFluss);
												vt.put(ValueType.LowerBound, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
											if (nlc3.item(l).getNodeName().equals("PFV-UpperBound")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = pmfv.get(akFluss);
												vt.put(ValueType.UpperBound, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
										}											
									}
								}
							}
						}
						ProcessModule.instance(pmname);
//						for (Flow akFluss : pmfv.keySet()) {
//							ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.MeanValue, pmfv.get(akFluss).get(ValueType.MeanValue));
//							ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.LowerBound, pmfv.get(akFluss).get(ValueType.LowerBound));
//							ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.UpperBound, pmfv.get(akFluss).get(ValueType.UpperBound));
//						}
					}
					
					nl = docEle.getElementsByTagName("ProcessModuleGroup");
					if (nl.getLength() != 0) {
						MCAObject.clear(ProcessModuleGroup.class);
					}
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String pmgname = "";	
						String rfname = "";
						String rfvalue = "";
						LinkedList<String> mnl = new LinkedList<String>();
						for (int j = 0; j < nlc.getLength(); j++) {
							if (nlc.item(j).getNodeName().equals("PMGroupName")) {
								pmgname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("RelevantFlowName")) {
								rfname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("RelevantFlowValue")) {
								rfvalue = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("PMGroup-Modules")) {	
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("PMGroup-Module")) {
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("PMGM-Name")) {
												String modname = nlc3.item(l).getTextContent();	
												mnl.add(modname);
											}											
										}										
									}
								}
								
							}
						}
						ProcessModuleGroup.createInstance(pmgname, Flow.getInstance(rfname), Double.parseDouble(rfvalue));
//						for (String mi : mnl) {
//							ProcessModuleGroup.getAllInstances().get(pmgname).addModule(ProcessModule.getAllInstances().get(mi));
//						}
						
					}
									
					nl = docEle.getElementsByTagName("ProductSystem");
					if (nl.getLength() != 0) {
						MCAObject.clear(ProductSystem.class);
					}
					LinkedHashMap<String, LinkedList<String>> mnls = new LinkedHashMap<String, LinkedList<String>>();
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String psname = "";	
						String bvename = "";
						String bvemenge = "";	
						LinkedList<String> mnl = new LinkedList<String>();
						LinkedHashMap<Flow, Double> bv = new LinkedHashMap<Flow, Double>();
						LinkedList<Flow> vuk = new LinkedList<Flow>();
						for (int j = 0; j < nlc.getLength(); j++) {	
							if (nlc.item(j).getNodeName().equals("PS-Name")) {
								psname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("PS-Modules")) {
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("PS-Module")) {
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("PSM-Name")) {
												String modname = nlc3.item(l).getTextContent();	
												mnl.add(modname);
											}											
										}										
									}
								}
							}
							if (nlc.item(j).getNodeName().equals("DemandVector")) {
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("DV-Entry")) {
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("DV-Name")) {
												bvename = nlc3.item(l).getTextContent();
											}
											if (nlc3.item(l).getNodeName().equals("DV-Value")) {
												bvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(bvename);
												bv.put(akFluss, Double.parseDouble(bvemenge));
											}
										}											
									}
								}
							}
							if (nlc.item(j).getNodeName().equals("PreAndCoProducts")) {
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("PaCP-Entry")) {
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("PaCP-Name")) {
												String flname = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(flname);
												vuk.add(akFluss);
											}											
										}										
									}
								}									
							}							
						}
						ProductSystem.instance(psname, bv, vuk);
						mnls.put(psname, mnl);																			
					}
					
					nl = docEle.getElementsByTagName("ImpactCategory");
					if (nl.getLength() != 0) {
						MCAObject.clear(ImpactCategory.class);
					}
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String catname = "";
						String indiname = "";
						for (int j = 0; j < nlc.getLength(); j++) {
							if (nlc.item(j).getNodeName().equals("Category")) {
								catname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("Indicator")) {
								indiname = nlc.item(j).getTextContent();
							}
						}
						CategoryIndicator indi = CategoryIndicator.instance(indiname);
						ImpactCategory.instance(catname, indi);
					}
					
					nl = docEle.getElementsByTagName("CFactor");
					if (nl.getLength() != 0) {
						MCAObject.clear(CharacFactor.class);
					}
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String cfname = "";
						String cfflow = "";
						String cfcat = "";
						String cfmv = "";
						String cflv = "";
						String cfuv = "";
						Double facVal = 0.0;					
						for (int j = 0; j < nlc.getLength(); j++) {	
							if (nlc.item(j).getNodeName().equals("CFName")) {
								cfname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("CFFlow")) {
								cfflow = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("CFCategory")) {
								cfcat = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("CFMainValue")) {
								cfmv = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("CFLowerBound")) {
								cflv = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("CFUpperBound")) {
								cfuv = nlc.item(j).getTextContent();
							}
						}
						facVal = Double.parseDouble(cfmv);
						CharacFactor.instance(cfname, Flow.getInstance(cfflow), ImpactCategory.getInstance(cfcat), facVal);
						Double lbv;
						Double obv;
						lbv = Double.parseDouble(cflv);
						obv = Double.parseDouble(cfuv);
						CharacFactor.setLowerBound(cfname, lbv);
						CharacFactor.setUpperBound(cfname, obv);
					}
					
					nl = docEle.getElementsByTagName("LCIAMethod");
					if (nl.getLength() != 0) {
						MCAObject.clear(LCIAMethod.class);
					}
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String lciaName = "";
						for (int j = 0; j < nlc.getLength(); j++) {
							if (nlc.item(j).getNodeName().equals("LCIA-Name")) {							
								lciaName = nlc.item(j).getTextContent();
								LCIAMethod.instance(lciaName);
							}							
							if (nlc.item(j).getNodeName().equals("LCIA-Categories")) {
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("LCIA-Category")) {
										String as = nlc2.item(k).getTextContent();
										LCIAMethod.instance(lciaName).addCategory(ImpactCategory.getInstance(as));
									}
								}
							}
							if (nlc.item(j).getNodeName().equals("LCIA-Factors")) {
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("LCIA-Factor")) {
										String as = nlc2.item(k).getTextContent();
										LCIAMethod.instance(lciaName).addFactor(CharacFactor.getInstance(as));
									}
								}							
							}							
						}					
					}
					
					nl = docEle.getElementsByTagName("ProductDeclaration");
					if (nl.getLength() != 0) {
						MCAObject.clear(ProductDeclaration.class);
					}
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String nameProd = "";
						String einheit = "";
						String nameLCIA = "";
						for (int j = 0; j < nlc.getLength(); j++) {
							if (nlc.item(j).getNodeName().equals("PD-Name")) {
								nameProd = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("PD-Unit")) {
								einheit = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("PD-Method")) {
								nameLCIA = nlc.item(j).getTextContent();
							}						
						}
						FlowUnit einheit2 = FlowUnit.valueOf(einheit);
						LCIAMethod bm = LCIAMethod.instance(nameLCIA);
						ProductDeclaration.instance(nameProd, einheit2).setBM(bm);					
					}
					
					// Zweiter Durchgang:
					// Füllen der Objekte mit Inhalten
									
					nl = docEle.getElementsByTagName("ProcessModule");
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String pmname = "";	
						String fvename = "";	
						String fvemenge = "";
						LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> pmfv = new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>();						
						for (int j = 0; j < nlc.getLength(); j++) {							
							if (nlc.item(j).getNodeName().equals("ModuleName")) {									
								pmname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("ElementaryFlowVector")) {									
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("EFV-Entry")) {											
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("EFV-Name")) {
												fvename = nlc3.item(l).getTextContent();
											}
											if (nlc3.item(l).getNodeName().equals("EFV-MainValue")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = new LinkedHashMap<ValueType, Double>();
												vt.put(ValueType.MeanValue, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
											if (nlc3.item(l).getNodeName().equals("EFV-LowerBound")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = pmfv.get(akFluss);
												vt.put(ValueType.LowerBound, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
											if (nlc3.item(l).getNodeName().equals("EFV-UpperBound")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = pmfv.get(akFluss);
												vt.put(ValueType.UpperBound, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
										}											
									}
								}
							}
							if (nlc.item(j).getNodeName().equals("ProductFlowVector")) {									
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("PFV-Entry")) {											
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("PFV-Name")) {
												fvename = nlc3.item(l).getTextContent();
											}
											if (nlc3.item(l).getNodeName().equals("PFV-MainValue")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = new LinkedHashMap<ValueType, Double>();
												vt.put(ValueType.MeanValue, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
											if (nlc3.item(l).getNodeName().equals("PFV-LowerBound")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = pmfv.get(akFluss);
												vt.put(ValueType.LowerBound, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
											if (nlc3.item(l).getNodeName().equals("PFV-UpperBound")) {
												fvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(fvename);
												LinkedHashMap<ValueType, Double> vt = pmfv.get(akFluss);
												vt.put(ValueType.UpperBound, Double.parseDouble(fvemenge));
												pmfv.put(akFluss, vt);
											}
										}											
									}
								}
							}
						}
//						ProcessModule.instance(pmname);
						for (Flow akFluss : pmfv.keySet()) {
							ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.MeanValue, pmfv.get(akFluss).get(ValueType.MeanValue));
							ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.LowerBound, pmfv.get(akFluss).get(ValueType.LowerBound));
							ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.UpperBound, pmfv.get(akFluss).get(ValueType.UpperBound));
						}
					}
					
					nl = docEle.getElementsByTagName("ProcessModuleGroup");
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String pmgname = "";	
						LinkedList<String> mnl = new LinkedList<String>();
						for (int j = 0; j < nlc.getLength(); j++) {
							if (nlc.item(j).getNodeName().equals("PMGroupName")) {
								pmgname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("PMGroup-Modules")) {	
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("PMGroup-Module")) {
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("PMGM-Name")) {
												String modname = nlc3.item(l).getTextContent();	
												mnl.add(modname);
											}											
										}										
									}
								}
								
							}
						}
//						ProcessModuleGroup.createInstance(pmgname, Flow.getInstance(rfname), Double.parseDouble(rfvalue));
						for (String mi : mnl) {
							ProcessModuleGroup.getAllInstances().get(pmgname).addModule(ProcessModule.getAllInstances().get(mi));
						}					
					}
									
					nl = docEle.getElementsByTagName("ProductSystem");
					mnls.clear();
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String psname = "";	
						String bvename = "";
						String bvemenge = "";	
						LinkedList<String> mnl = new LinkedList<String>();
						LinkedHashMap<Flow, Double> bv = new LinkedHashMap<Flow, Double>();
						LinkedList<Flow> vuk = new LinkedList<Flow>();
						for (int j = 0; j < nlc.getLength(); j++) {	
							if (nlc.item(j).getNodeName().equals("PS-Name")) {
								psname = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("PS-Modules")) {
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("PS-Module")) {
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("PSM-Name")) {
												String modname = nlc3.item(l).getTextContent();	
												mnl.add(modname);
											}											
										}										
									}
								}
							}
							if (nlc.item(j).getNodeName().equals("DemandVector")) {
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("DV-Entry")) {
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("DV-Name")) {
												bvename = nlc3.item(l).getTextContent();
											}
											if (nlc3.item(l).getNodeName().equals("DV-Value")) {
												bvemenge = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(bvename);
												bv.put(akFluss, Double.parseDouble(bvemenge));
											}
										}											
									}
								}
							}
							if (nlc.item(j).getNodeName().equals("PreAndCoProducts")) {
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("PaCP-Entry")) {
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("PaCP-Name")) {
												String flname = nlc3.item(l).getTextContent();
												Flow akFluss = Flow.getInstance(flname);
												vuk.add(akFluss);
											}											
										}										
									}
								}									
							}							
						}
//						ProductSystem.instance(psname, bv, vuk);
						mnls.put(psname, mnl);																			
					}
					for (String psname : mnls.keySet()) {
						LinkedList<String> mnl = mnls.get(psname);
						for (String m : mnl) {
							if (ProcessModule.containsName(m)) {
								ProductSystem.getInstance(psname).addProzessmodul(ProcessModule.getInstance(m));
							} else {
								if (ProcessModuleGroup.containsName(m)) {
									ProductSystem.getInstance(psname).addProzessmodul(ProcessModuleGroup.getInstance(m));
								} else {
									if (ProductSystem.containsName(m)) {
										ProductSystem.getInstance(psname).addProzessmodul(ProductSystem.getInstance(m));
									}
								}
							}
						}
					}
					
					nl = docEle.getElementsByTagName("ProductDeclaration");
					for (int i = 0; i < nl.getLength(); i++) {
						NodeList nlc = nl.item(i).getChildNodes();
						String nameProd = "";
						for (int j = 0; j < nlc.getLength(); j++) {
							if (nlc.item(j).getNodeName().equals("PD-Name")) {
								nameProd = nlc.item(j).getTextContent();
							}
							if (nlc.item(j).getNodeName().equals("ImpactValuesVector")) {
								NodeList nlc2 = nlc.item(j).getChildNodes();
								for (int k = 0; k < nlc2.getLength(); k++) {
									if (nlc2.item(k).getNodeName().equals("IVV-Entry")) {											
										NodeList nlc3 = nlc2.item(k).getChildNodes();
										String icname = "";
										String mMenge = "";
										String lMenge = "";
										String uMenge = "";
										for (int l = 0; l < nlc3.getLength(); l++) {
											if (nlc3.item(l).getNodeName().equals("ImpactCategorie-Name")) {
												icname = nlc3.item(l).getTextContent();
											}
											if (nlc3.item(l).getNodeName().equals("ICV-MainValue")) {
												mMenge = nlc3.item(l).getTextContent();
											}
											if (nlc3.item(l).getNodeName().equals("ICV-LowerBound")) {
												lMenge = nlc3.item(l).getTextContent();
											}
											if (nlc3.item(l).getNodeName().equals("ICV-UpperBound")) {
												uMenge = nlc3.item(l).getTextContent();
											}										
										}
										ImpactCategory ic = ImpactCategory.getInstance(icname);
										LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
										values.put(ValueType.MeanValue, Double.parseDouble(mMenge));
										values.put(ValueType.LowerBound, Double.parseDouble(lMenge));
										values.put(ValueType.UpperBound, Double.parseDouble(uMenge));									
										ProductDeclaration.getInstance(nameProd).addWirkung(ic, values);
									}									
								}								
							}
						}					
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
