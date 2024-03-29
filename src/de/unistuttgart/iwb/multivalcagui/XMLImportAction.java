/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
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

import de.unistuttgart.iwb.multivalca.CategoryIndicator;
import de.unistuttgart.iwb.multivalca.CharacFactor;
import de.unistuttgart.iwb.multivalca.Component;
import de.unistuttgart.iwb.multivalca.Composition;
import de.unistuttgart.iwb.multivalca.FVMGroupType;
import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowType;
import de.unistuttgart.iwb.multivalca.FlowUnit;
import de.unistuttgart.iwb.multivalca.FlowValueMapGroup;
import de.unistuttgart.iwb.multivalca.IVMGroupType;
import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.ImpactValueMapGroup;
import de.unistuttgart.iwb.multivalca.ImpactValueMaps;
import de.unistuttgart.iwb.multivalca.LCIAMethod;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import de.unistuttgart.iwb.multivalca.ValueType;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.823
 */

class XMLImportAction extends AbstractAction {	

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
        
    	final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    	final int width = (int) screen.getWidth();
    	final int height = (int) screen.getHeight();
        final Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);
        
        chooser.setFileFilter(filter);
        chooserSetFont(chooser, generalfont);
        chooser.setPreferredSize(new Dimension(width*40/100, height*50/100));
        
        // Dialog zum Laden von Dateien anzeigen
        int rueckgabeWert = chooser.showDialog(null, "Datei ausw�hlen");
        
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {
        	File fileInput = chooser.getSelectedFile();
        	MultiVaLCA.setTitle(MultiVaLCA.getTitle()+"   -   "+fileInput.getName());
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				try {
					Document dom = db.parse(fileInput);
					Element docEle = dom.getDocumentElement();	

					MCAObject.clear();

					createFlows(docEle);
					createProcessModules(docEle);
					createFlowValueMapGroups(docEle);
					createProductSystems(docEle);
					createImpactCategories(docEle);
					createCFactors(docEle);
					createLCIAMethods(docEle);
					createProductDeclarations(docEle);
					createImpactValueMapGroups(docEle);
					createComponents(docEle);
					createCompositions(docEle);
					fillProcessModules(docEle);
					fillFlowValueMapGroups(docEle);
					fillProductSystems(docEle);
					fillProductDeclarations(docEle);
					fillImpactValueMapGroups(docEle);
//					fillComponents(docEle);
					fillCompositions(docEle);

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

	private void createFlows(Element docEle) {
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
	}
	
	private void createProcessModules(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ProcessModule");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String pmname = "";							
			for (int j = 0; j < nlc.getLength(); j++) {							
				if (nlc.item(j).getNodeName().equals("ModuleName")) {									
					pmname = nlc.item(j).getTextContent();
				}
			}
			ProcessModule.instance(pmname);
		}
	}	

	private void fillProcessModules(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ProcessModule");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String pmname = "";	
			LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> pmfv = new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>();	
			LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> dmfv = new LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>>();
			for (int j = 0; j < nlc.getLength(); j++) {							
				if (nlc.item(j).getNodeName().equals("ModuleName")) {									
					pmname = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("ElementaryFlowVector")) {									
					NodeList nlc2 = nlc.item(j).getChildNodes();
					fillPMwithEFV(nlc2, pmfv);					
				}
				if (nlc.item(j).getNodeName().equals("ProductFlowVector")) {	
					NodeList nlc2 = nlc.item(j).getChildNodes();
					fillPMwithPFV(nlc2, pmfv);				
				}
				if (nlc.item(j).getNodeName().equals("DeclaredFlowVector")) {	
					NodeList nlc2 = nlc.item(j).getChildNodes();
					fillPMwithDFV(nlc2, dmfv);				
				}
			}
			for (Flow akFluss : pmfv.keySet()) {
				ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.MeanValue, pmfv.get(akFluss).get(ValueType.MeanValue));
				ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.LowerBound, pmfv.get(akFluss).get(ValueType.LowerBound));
				ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.UpperBound, pmfv.get(akFluss).get(ValueType.UpperBound));
			}
//			for (ProductDeclaration akFluss : dmfv.keySet()) {
			for (ImpactValueMaps akFluss : dmfv.keySet()) {
				ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.MeanValue, dmfv.get(akFluss).get(ValueType.MeanValue));
				ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.LowerBound, dmfv.get(akFluss).get(ValueType.LowerBound));
				ProcessModule.getInstance(pmname).addFluss(akFluss, ValueType.UpperBound, dmfv.get(akFluss).get(ValueType.UpperBound));
			}
		}
	}

	private void fillPMwithEFV(NodeList nlc2, LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> pmfv) {
		String fvename = "";	
		String fvemenge = "";
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

	private void fillPMwithPFV(NodeList nlc2, LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> pmfv) {
		String fvename = "";	
		String fvemenge = "";
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
	
	private void fillPMwithDFV(NodeList nlc2, LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> dmfv) {
		String fvename = "";	
		String fvemenge = "";
		for (int k = 0; k < nlc2.getLength(); k++) {
			if (nlc2.item(k).getNodeName().equals("DFV-Entry")) {											
				NodeList nlc3 = nlc2.item(k).getChildNodes();
				for (int l = 0; l < nlc3.getLength(); l++) {
					if (nlc3.item(l).getNodeName().equals("DFV-Name")) {
						fvename = nlc3.item(l).getTextContent();
					}
					if (ProductDeclaration.containsName(fvename)) {
						if (nlc3.item(l).getNodeName().equals("DFV-MainValue")) {
							fvemenge = nlc3.item(l).getTextContent();
							ProductDeclaration akFluss = ProductDeclaration.getInstance(fvename);
							LinkedHashMap<ValueType, Double> vt = new LinkedHashMap<ValueType, Double>();
							vt.put(ValueType.MeanValue, Double.parseDouble(fvemenge));
							dmfv.put(akFluss, vt);
						}
						if (nlc3.item(l).getNodeName().equals("DFV-LowerBound")) {
							fvemenge = nlc3.item(l).getTextContent();
							ProductDeclaration akFluss = ProductDeclaration.getInstance(fvename);
							LinkedHashMap<ValueType, Double> vt = dmfv.get(akFluss);
							vt.put(ValueType.LowerBound, Double.parseDouble(fvemenge));
							dmfv.put(akFluss, vt);
						}
						if (nlc3.item(l).getNodeName().equals("DFV-UpperBound")) {
							fvemenge = nlc3.item(l).getTextContent();
							ProductDeclaration akFluss = ProductDeclaration.getInstance(fvename);
							LinkedHashMap<ValueType, Double> vt = dmfv.get(akFluss);
							vt.put(ValueType.UpperBound, Double.parseDouble(fvemenge));
							dmfv.put(akFluss, vt);
						}
					} else {
						if (nlc3.item(l).getNodeName().equals("DFV-MainValue")) {
							fvemenge = nlc3.item(l).getTextContent();
							ImpactValueMapGroup akFluss = ImpactValueMapGroup.getInstance(fvename);
							LinkedHashMap<ValueType, Double> vt = new LinkedHashMap<ValueType, Double>();
							vt.put(ValueType.MeanValue, Double.parseDouble(fvemenge));
							dmfv.put(akFluss, vt);
						}
						if (nlc3.item(l).getNodeName().equals("DFV-LowerBound")) {
							fvemenge = nlc3.item(l).getTextContent();
							ImpactValueMapGroup akFluss = ImpactValueMapGroup.getInstance(fvename);
							LinkedHashMap<ValueType, Double> vt = dmfv.get(akFluss);
							vt.put(ValueType.LowerBound, Double.parseDouble(fvemenge));
							dmfv.put(akFluss, vt);
						}
						if (nlc3.item(l).getNodeName().equals("DFV-UpperBound")) {
							fvemenge = nlc3.item(l).getTextContent();
							ImpactValueMapGroup akFluss = ImpactValueMapGroup.getInstance(fvename);
							LinkedHashMap<ValueType, Double> vt = dmfv.get(akFluss);
							vt.put(ValueType.UpperBound, Double.parseDouble(fvemenge));
							dmfv.put(akFluss, vt);
						}
					}
				}											
			}
		}
	}

	private void createFlowValueMapGroups(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ProcessModuleGroup");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String pmgname = "";	
			String rfname = "";
			String rfvalue = "";
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

			}
			FlowValueMapGroup.createInstance(pmgname, FVMGroupType.ProcessModule, Flow.getInstance(rfname), Double.parseDouble(rfvalue));						
		}

		NodeList nl1 = docEle.getElementsByTagName("FlowValueMapGroup");
		for (int i = 0; i < nl1.getLength(); i++) {
			NodeList nlc = nl1.item(i).getChildNodes();
			String fvmgname = "";	
			String fvmgtype = "";
			String rfname = "";
			String rfvalue = "";
			for (int j = 0; j < nlc.getLength(); j++) {
				if (nlc.item(j).getNodeName().equals("FVMGroupName")) {
					fvmgname = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("FVM-Type")) {
					fvmgtype = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("RelevantFlowName")) {
					rfname = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("RelevantFlowValue")) {
					rfvalue = nlc.item(j).getTextContent();
				}

			}
			FlowValueMapGroup.createInstance(fvmgname, FVMGroupType.valueOf(fvmgtype), Flow.getInstance(rfname), Double.parseDouble(rfvalue));						
		}
	}

	private void fillFlowValueMapGroups(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ProcessModuleGroup");
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
			for (String mi : mnl) {
				FlowValueMapGroup.getAllInstances().get(pmgname).addFlowValueMap(ProcessModule.getAllInstances().get(mi));
			}					
		}	

		NodeList nl1 = docEle.getElementsByTagName("FlowValueMapGroup");
		for (int i = 0; i < nl1.getLength(); i++) {
			NodeList nlc = nl1.item(i).getChildNodes();
			String ssgname = "";	
			LinkedList<String> mnl = new LinkedList<String>();
			for (int j = 0; j < nlc.getLength(); j++) {
				if (nlc.item(j).getNodeName().equals("FVMGroupName")) {
					ssgname = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("FVMGroup-Elements")) {	
					NodeList nlc2 = nlc.item(j).getChildNodes();
					for (int k = 0; k < nlc2.getLength(); k++) {
						if (nlc2.item(k).getNodeName().equals("FVMGroup-Element")) {
							NodeList nlc3 = nlc2.item(k).getChildNodes();
							for (int l = 0; l < nlc3.getLength(); l++) {
								if (nlc3.item(l).getNodeName().equals("FVMGE-Name")) {
									String modname = nlc3.item(l).getTextContent();	
									mnl.add(modname);
								}											
							}										
						}
					}

				}
			}
			for (String mi : mnl) {
				if (ProcessModule.containsName(mi)) {
					FlowValueMapGroup.getInstance(ssgname).addFlowValueMap(ProcessModule.getInstance(mi));
				} else {
					if (FlowValueMapGroup.containsName(mi)) {
						FlowValueMapGroup.getInstance(ssgname).addFlowValueMap(FlowValueMapGroup.getInstance(mi));					
					} else {
						if (ProductSystem.containsName(mi)) {
							FlowValueMapGroup.getInstance(ssgname).addFlowValueMap(ProductSystem.getInstance(mi));									
						}
					}
				}
			}					
		}	
	}

	private void createProductSystems(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ProductSystem");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String psname = "";	
			LinkedHashMap<Flow, Double> bv = new LinkedHashMap<Flow, Double>();
			LinkedList<Flow> vuk = new LinkedList<Flow>();
			for (int j = 0; j < nlc.getLength(); j++) {	
				if (nlc.item(j).getNodeName().equals("PS-Name")) {
					psname = nlc.item(j).getTextContent();
				}
			}
			ProductSystem.instance(psname, bv, vuk);																	
		}
	}

	private void fillProductSystems(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ProductSystem");
		LinkedHashMap<String, LinkedList<String>> mnls = new LinkedHashMap<String, LinkedList<String>>();
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String psname = "";	
			LinkedList<String> mnl = new LinkedList<String>();
			LinkedHashMap<Flow, Double> bv = new LinkedHashMap<Flow, Double>();
			LinkedList<Flow> vuk = new LinkedList<Flow>();
			for (int j = 0; j < nlc.getLength(); j++) {	
				if (nlc.item(j).getNodeName().equals("PS-Name")) {
					psname = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("PS-Modules")) {
					NodeList nlc2 = nlc.item(j).getChildNodes();
					fillPSwithModules(nlc2, mnl);					
				}
				if (nlc.item(j).getNodeName().equals("DemandVector")) {
					NodeList nlc2 = nlc.item(j).getChildNodes();
					fillPSwithDemandVector(nlc2, bv);					
				}
				if (nlc.item(j).getNodeName().equals("PreAndCoProducts")) {
					NodeList nlc2 = nlc.item(j).getChildNodes();
					fillPSwithPreAndCoProducts(nlc2, vuk);														
				}							
			}
			ProductSystem.getInstance(psname).setBedarfsvektor(bv);
			ProductSystem.getInstance(psname).setVorUndKoppelProdukte(vuk);
			mnls.put(psname, mnl);																			
		}
		for (String psname : mnls.keySet()) {
			LinkedList<String> mnl = mnls.get(psname);
			for (String m : mnl) {
				if (ProcessModule.containsName(m)) {
					ProductSystem.getInstance(psname).addProzessmodul(ProcessModule.getInstance(m));
				} else {
					if (ProductSystem.containsName(m)) {
						ProductSystem.getInstance(psname).addProzessmodul(ProductSystem.getInstance(m));
					} else {
						if (FlowValueMapGroup.containsName(m)) {
							ProductSystem.getInstance(psname).addProzessmodul(FlowValueMapGroup.getInstance(m));									
						}
					}
				}
			}
		}	
	}

	private void fillPSwithModules(NodeList nlc2, LinkedList<String> mnl) {
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

	private void fillPSwithDemandVector(NodeList nlc2, LinkedHashMap<Flow, Double> bv) {
		for (int k = 0; k < nlc2.getLength(); k++) {
			if (nlc2.item(k).getNodeName().equals("DV-Entry")) {
				NodeList nlc3 = nlc2.item(k).getChildNodes();
				String bvename = "";
				for (int l = 0; l < nlc3.getLength(); l++) {					
					if (nlc3.item(l).getNodeName().equals("DV-Name")) {
						bvename = nlc3.item(l).getTextContent();
					}
					if (nlc3.item(l).getNodeName().equals("DV-Value")) {
						String bvemenge = nlc3.item(l).getTextContent();
						Flow akFluss = Flow.getInstance(bvename);
						bv.put(akFluss, Double.parseDouble(bvemenge));
					}
				}											
			}
		}
	}

	private void fillPSwithPreAndCoProducts(NodeList nlc2, LinkedList<Flow> vuk) {
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
	
	private void createImpactCategories(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ImpactCategory");
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
	}

	private void createCFactors(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("CFactor");
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
	}

	private void createLCIAMethods(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("LCIAMethod");
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
	}

	private void createProductDeclarations(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ProductDeclaration");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String nameProd = "";
			String einheit = "";
			//			String nameLCIA = "";
			for (int j = 0; j < nlc.getLength(); j++) {
				if (nlc.item(j).getNodeName().equals("PD-Name")) {
					nameProd = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("PD-Unit")) {
					einheit = nlc.item(j).getTextContent();
				}
				//				if (nlc.item(j).getNodeName().equals("PD-Method")) {
				//					nameLCIA = nlc.item(j).getTextContent();
				//				}						
			}
			FlowUnit einheit2 = FlowUnit.valueOf(einheit);
			//			LCIAMethod bm = LCIAMethod.instance(nameLCIA);
			//			ProductDeclaration.instance(nameProd, einheit2).setBM(bm);		
			ProductDeclaration.instance(nameProd, einheit2);
		}
	}

	private void fillProductDeclarations(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ProductDeclaration");
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
	}

	private void createImpactValueMapGroups(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ImpactValueMapGroup");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String ivmgname = "";
			String ivmgtype = "";
			for (int j = 0; j < nlc.getLength(); j++) {
				if (nlc.item(j).getNodeName().equals("IVMGroupName")) {
					ivmgname = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("IVM-Type")) {
					ivmgtype = nlc.item(j).getTextContent();
				}

			}
			ImpactValueMapGroup.instance(ivmgname, IVMGroupType.valueOf(ivmgtype));						
		}
	}

	private void fillImpactValueMapGroups(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("ImpactValueMapGroup");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String pdgname = "";	

			LinkedList<String> dnl = new LinkedList<String>();
			for (int j = 0; j < nlc.getLength(); j++) {
				if (nlc.item(j).getNodeName().equals("IVMGroupName")) {
					pdgname = nlc.item(j).getTextContent();
				}			
				if (nlc.item(j).getNodeName().equals("IVMGroup-Elements")) {	
					NodeList nlc2 = nlc.item(j).getChildNodes();
					for (int k = 0; k < nlc2.getLength(); k++) {
						if (nlc2.item(k).getNodeName().equals("IVMGroup-Element")) {
							NodeList nlc3 = nlc2.item(k).getChildNodes();
							for (int l = 0; l < nlc3.getLength(); l++) {
								if (nlc3.item(l).getNodeName().equals("IVMGE-Name")) {
									String decname = nlc3.item(l).getTextContent();	
									dnl.add(decname);
								}											
							}										
						}
					}

				}
			}

			for (String di : dnl) {
				if (ProductDeclaration.containsName(di)) {
					ImpactValueMapGroup.getInstance(pdgname).addImpactValueMap(ProductDeclaration.getInstance(di));
				} else {
					if (Component.containsName(di)) {
						ImpactValueMapGroup.getInstance(pdgname).addImpactValueMap(Component.getInstance(di));
					} else {
						if (Composition.containsName(di)) {
							ImpactValueMapGroup.getInstance(pdgname).addImpactValueMap(Composition.getInstance(di));									
						}
					}
				}
			}					
		}	
	}

	private void createComponents(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("Component");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String coname = "";
			String coref = "";
			String mMenge = "";
			String lMenge = "";
			String uMenge = "";
			String unit ="";
			for (int j = 0; j < nlc.getLength(); j++) {
				if (nlc.item(j).getNodeName().equals("Component-Name")) {
					coname = nlc.item(j).getTextContent();
				}	
				if (nlc.item(j).getNodeName().equals("Component-Unit")) {
					unit = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("Component-Reference")) {
					coref = nlc.item(j).getTextContent();
				}	
				if (nlc.item(j).getNodeName().equals("ComRef-MainValue")) {
					mMenge = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("ComRef-LowerBound")) {
					lMenge = nlc.item(j).getTextContent();
				}
				if (nlc.item(j).getNodeName().equals("ComRef-UpperBound")) {
					uMenge = nlc.item(j).getTextContent();
				}
			}
			Component.newInstance(coname,coref);
			Component.getInstance(coname).setEinheit(FlowUnit.valueOf(unit));
			LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
			values.put(ValueType.MeanValue, Double.parseDouble(mMenge));
			values.put(ValueType.LowerBound, Double.parseDouble(lMenge));
			values.put(ValueType.UpperBound, Double.parseDouble(uMenge));									
			Component.getInstance(coname).setValues(values);
		}
	}

	private void createCompositions(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("Composition");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String coname = "";
			for (int j = 0; j < nlc.getLength(); j++) {
				if (nlc.item(j).getNodeName().equals("Composition-Name")) {
					coname = nlc.item(j).getTextContent();
				}					
			}
			Composition.instance(coname);						
		}
	}

	private void fillCompositions(Element docEle) {
		NodeList nl = docEle.getElementsByTagName("Composition");
		for (int i = 0; i < nl.getLength(); i++) {
			NodeList nlc = nl.item(i).getChildNodes();
			String coname = "";				
			LinkedHashMap<String, Integer> elements = new LinkedHashMap<String, Integer>();
			for (int j = 0; j < nlc.getLength(); j++) {
				if (nlc.item(j).getNodeName().equals("Composition-Name")) {
					coname = nlc.item(j).getTextContent();
				}			
				if (nlc.item(j).getNodeName().equals("Composition-Elements")) {	
					NodeList nlc2 = nlc.item(j).getChildNodes();
					for (int k = 0; k < nlc2.getLength(); k++) {
						if (nlc2.item(k).getNodeName().equals("Composition-Element")) {
							NodeList nlc3 = nlc2.item(k).getChildNodes();
							String elename = "";
							Integer elenumber = 0;
							for (int l = 0; l < nlc3.getLength(); l++) {
								if (nlc3.item(l).getNodeName().equals("Element-Name")) {
									elename = nlc3.item(l).getTextContent();
								}
								if (nlc3.item(l).getNodeName().equals("Element-Number")) {
									try {
										elenumber = Integer.parseInt(nlc3.item(l).getTextContent().toString());
									} catch (NumberFormatException e){
										elenumber = 0;
									}
								}									
								
							}	elements.put(elename, elenumber);									
						}
					}

				}
			}

			for (String el : elements.keySet()) {
				if (Component.containsName(el)) {
					Composition.getInstance(coname).addKomponente((ImpactValueMaps)Component.getInstance(el), elements.get(el));
				} else {
					if (Composition.containsName(el)) {
						Composition.getInstance(coname).addKomponente((ImpactValueMaps)Composition.getInstance(el), elements.get(el));
					} else {
						if (ImpactValueMapGroup.containsName(el)) {
							Composition.getInstance(coname).addKomponente((ImpactValueMaps)ImpactValueMapGroup.getInstance(el), elements.get(el));									
						} else {
							if (ProductSystem.containsName(el)) {
								Composition.getInstance(coname).addKomponente((ImpactValueMaps)ProductSystem.getInstance(el), elements.get(el));									
							}
						}
					}
				}					
			}	
		}
	}
}

