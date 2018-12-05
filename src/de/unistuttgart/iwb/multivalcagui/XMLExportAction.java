/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
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

import de.unistuttgart.iwb.multivalca.*;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.558
 */

class XMLExportAction extends AbstractAction {
	
	public XMLExportAction(Language l) {
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		putValue(NAME, bundle.getString("mp61"));
		putValue(SHORT_DESCRIPTION, bundle.getString("mp61e"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
        
            Element root = document.createElement("XML");
            document.appendChild(root);
            
            writeFlows(document, root);
            writeProcessModules(document, root);
            writeProcessModuleGroups(document, root);
            writeProductSystems(document, root);
            writeImpactCategories(document, root);
            writeCFactors(document, root);
            writeLCIAMethods(document, root);
            writeProductDeclarations(document, root);
            writeComponents(document, root);
            
            JFileChooser chooser = new JFileChooser();
	        FileFilter filter = new FileNameExtensionFilter("XML-Dateien (*.xml)", "xml");
	        chooser.setFileFilter(filter);

	        // Dialog zum Speichern von Dateien anzeigen
	        int rueckgabeWert = chooser.showSaveDialog(null);
	        if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {	        	
	        	DOMSource domSource = new DOMSource(document);
	        	File fileOutput = chooser.getSelectedFile();
	        	if (!FilenameUtils.getExtension(fileOutput.getName()).equalsIgnoreCase("xml")) {
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
        } catch(Throwable e) {
            e.printStackTrace();
        } 		
	}

	public void writeFlows(Document document, Element root) {
        Element allefluesse = document.createElement("Flows");
        root.appendChild(allefluesse);
        
        for(String pfname : Flow.getAllInstances(Flow.class).keySet()) {
        	Flow pf = Flow.getInstance(pfname); 
        	Element fluss = document.createElement("Flow");
        	allefluesse.appendChild(fluss);
        	Element name = document.createElement("FlowName");
            fluss.appendChild(name);
            name.appendChild(document.createTextNode(pf.getName()));
            Element typ = document.createElement("FlowType");
            fluss.appendChild(typ);
            typ.appendChild(document.createTextNode(pf.getType().toString()));
            Element einheit = document.createElement("FlowUnit");
            fluss.appendChild(einheit);
            einheit.appendChild(document.createTextNode(pf.getUnit().toString()));
		}
	}
	
	private void writeProcessModules(Document document, Element root) {
        Element allemodule = document.createElement("ProcessModules");
        root.appendChild(allemodule);
        
		for(String mn : ProcessModule.getAllInstances().keySet()) {
			ProcessModule akModul = ProcessModule.getInstance(mn);
			Element prozessmodul = document.createElement("ProcessModule");
			allemodule.appendChild(prozessmodul);
			Element name = document.createElement("ModuleName");
			prozessmodul.appendChild(name);
			name.appendChild(document.createTextNode(akModul.getName()));
			Element efv = document.createElement("ElementaryFlowVector");
			prozessmodul.appendChild(efv);	            
            for(Flow pf : akModul.getElementarflussvektor().keySet()){
            	Element fluss = document.createElement("EFV-Entry");
				efv.appendChild(fluss);	
            	Element fname = document.createElement("EFV-Name");
            	fluss.appendChild(fname);
            	fname.appendChild(document.createTextNode(pf.getName()));
            	Element menge = document.createElement("EFV-MainValue");
            	fluss.appendChild(menge);
            	menge.appendChild(document.createTextNode(akModul.getElementarflussvektor().get(pf).get(ValueType.MeanValue).toString()));
            	Element menge2 = document.createElement("EFV-LowerBound");
            	fluss.appendChild(menge2);
            	menge2.appendChild(document.createTextNode(akModul.getElementarflussvektor().get(pf).get(ValueType.LowerBound).toString()));
            	Element menge3 = document.createElement("EFV-UpperBound");
            	fluss.appendChild(menge3);
            	menge3.appendChild(document.createTextNode(akModul.getElementarflussvektor().get(pf).get(ValueType.UpperBound).toString()));
            }
            Element pfv = document.createElement("ProductFlowVector");
			prozessmodul.appendChild(pfv);	  
            for(Flow pf : akModul.getProduktflussvektor().keySet()){
            	Element fluss = document.createElement("PFV-Entry");
				pfv.appendChild(fluss);
            	Element fname = document.createElement("PFV-Name");
            	fluss.appendChild(fname);
            	fname.appendChild(document.createTextNode(pf.getName()));
            	Element menge = document.createElement("PFV-MainValue");
            	fluss.appendChild(menge);
            	menge.appendChild(document.createTextNode(akModul.getProduktflussvektor().get(pf).get(ValueType.MeanValue).toString()));
            	Element menge2 = document.createElement("PFV-LowerBound");
            	fluss.appendChild(menge2);
            	menge2.appendChild(document.createTextNode(akModul.getProduktflussvektor().get(pf).get(ValueType.LowerBound).toString()));
            	Element menge3 = document.createElement("PFV-UpperBound");
            	fluss.appendChild(menge3);
            	menge3.appendChild(document.createTextNode(akModul.getProduktflussvektor().get(pf).get(ValueType.UpperBound).toString()));
            }
		}	
	}
	
	private void writeProcessModuleGroups(Document document, Element root) {
		Element allemodgroups = document.createElement("ProcessModuleGroups");
        root.appendChild(allemodgroups);
        
        for (String pmg : ProcessModuleGroup.getAllInstances().keySet()) {
        	ProcessModuleGroup akModul = ProcessModuleGroup.getInstance(pmg);
			Element prozessmodulgruppe = document.createElement("ProcessModuleGroup");
			allemodgroups.appendChild(prozessmodulgruppe);
			Element name = document.createElement("PMGroupName");
			prozessmodulgruppe.appendChild(name);
			name.appendChild(document.createTextNode(akModul.getName()));
			Element rfn = document.createElement("RelevantFlowName");
			prozessmodulgruppe.appendChild(rfn);
			rfn.appendChild(document.createTextNode(akModul.getRefFlow().getName()));
			Element rfv = document.createElement("RelevantFlowValue");
			prozessmodulgruppe.appendChild(rfv);
			rfv.appendChild(document.createTextNode(akModul.getRefValue().toString()));
			Element modullist = document.createElement("PMGroup-Modules");
			prozessmodulgruppe.appendChild(modullist);
			for (FlowValueMaps mod  : ProcessModuleGroup.getInstance(pmg).getModList()) {
				String modname = mod.getName();
				Element modul = document.createElement("PMGroup-Module");
				modullist.appendChild(modul);
				Element mname = document.createElement("PMGM-Name");
				modul.appendChild(mname);
				mname.appendChild(document.createTextNode(modname));
			}
        }	
	}
	
	private void writeProductSystems(Document document, Element root) {
		Element alleprosys = document.createElement("ProductSystems");
        root.appendChild(alleprosys);
        
        for(String psm : ProductSystem.getAllInstances().keySet()) {
        	ProductSystem akProSys = ProductSystem.getAllInstances().get(psm);
			Element produktsystem = document.createElement("ProductSystem");
			alleprosys.appendChild(produktsystem);
			Element name = document.createElement("PS-Name");
			produktsystem.appendChild(name);
			name.appendChild(document.createTextNode(akProSys.getName()));
			Element modullist = document.createElement("PS-Modules");
			produktsystem.appendChild(modullist);
			for (FlowValueMaps mod  : ProductSystem.getInstance(psm).getModulliste()) {
				String modname = mod.getName();
				Element modul = document.createElement("PS-Module");
				modullist.appendChild(modul);
				Element mname = document.createElement("PSM-Name");
				modul.appendChild(mname);
				mname.appendChild(document.createTextNode(modname));
			}					
			Element bv = document.createElement("DemandVector");
			produktsystem.appendChild(bv);		            	
        	for (Flow bvf : ProductSystem.getInstance(psm).getBedarfsvektor().keySet()) {
        		Element bedarf = document.createElement("DV-Entry");
        		bv.appendChild(bedarf);
        		Element fluss =  document.createElement("DV-Name");
        		bedarf.appendChild(fluss);
        		fluss.appendChild(document.createTextNode(bvf.getName()));
        		Element menge = document.createElement("DV-Value");
        		bedarf.appendChild(menge);
        		menge.appendChild(document.createTextNode(ProductSystem.getInstance(psm).
        				getBedarfsvektor().get(bvf).toString()));
        	}
        	Element vuk = document.createElement("PreAndCoProducts");
			produktsystem.appendChild(vuk);
        	for (Flow vkf : ProductSystem.getInstance(psm).getVorUndKoppelprodukte()){
        		Element produkt = document.createElement("PaCP-Entry");
        		vuk.appendChild(produkt);
        		Element prodname =  document.createElement("PaCP-Name");
        		produkt.appendChild(prodname);
        		prodname.appendChild(document.createTextNode(vkf.getName()));
        	}	            	
        }		
	}
	
	private void writeImpactCategories(Document document, Element root) {
        Element allecats = document.createElement("ImpactCategories");
        root.appendChild(allecats);
        
        for(String ick : ImpactCategory.getAllInstances().keySet()) {
        	ImpactCategory ic = ImpactCategory.getAllInstances().get(ick);
			Element cat = document.createElement("ImpactCategory");
			allecats.appendChild(cat);
			Element catname = document.createElement("Category");
			cat.appendChild(catname);	
			catname.appendChild(document.createTextNode(ic.getName()));
			Element catind = document.createElement("Indicator");
			cat.appendChild(catind);
			catind.appendChild(document.createTextNode(ic.getEinheit().getName()));        	
        }	
	}
	
	private void writeCFactors(Document document, Element root) {
        Element allecfs = document.createElement("CFactors");
        root.appendChild(allecfs);
        
        for(String cfName : CharacFactor.getAllInstances().keySet()) {
        	CharacFactor cf = CharacFactor.getAllInstances().get(cfName);
        	Element cfElement = document.createElement("CFactor");
        	allecfs.appendChild(cfElement);
        	Element cfname = document.createElement("CFName");
        	cfElement.appendChild(cfname);	
        	cfname.appendChild(document.createTextNode(cf.getName()));
        	Element cfflow = document.createElement("CFFlow");
        	cfElement.appendChild(cfflow);	
        	cfflow.appendChild(document.createTextNode(cf.getFlow().getName()));
        	Element cfcat = document.createElement("CFCategory");
        	cfElement.appendChild(cfcat);	
        	cfcat.appendChild(document.createTextNode(cf.getWirkung().getName()));
        	Element cfv1 = document.createElement("CFMainValue");
        	cfElement.appendChild(cfv1);	
        	cfv1.appendChild(document.createTextNode(cf.getValue(ValueType.MeanValue).toString()));
        	Element cfv2 = document.createElement("CFLowerBound");
        	cfElement.appendChild(cfv2);	
        	cfv2.appendChild(document.createTextNode(cf.getValue(ValueType.LowerBound).toString()));
        	Element cfv3 = document.createElement("CFUpperBound");
        	cfElement.appendChild(cfv3);	
        	cfv3.appendChild(document.createTextNode(cf.getValue(ValueType.UpperBound).toString()));      	
        }	
	}
	
	private void writeLCIAMethods(Document document, Element root) {
        Element alleMethods = document.createElement("LCIAMethods");
        root.appendChild(alleMethods);
        
        for(String lciaName : LCIAMethod.getAllInstances().keySet()) {
        	LCIAMethod thisLCIA = LCIAMethod.getAllInstances().get(lciaName);
        	Element lcia = document.createElement("LCIAMethod");
        	alleMethods.appendChild(lcia);
        	Element name = document.createElement("LCIA-Name");
        	lcia.appendChild(name);
        	name.appendChild(document.createTextNode(lciaName));
        	Element categoryList = document.createElement("LCIA-Categories");
        	lcia.appendChild(categoryList);
        	for (String imcat : thisLCIA.categoryList().keySet()) {
        		Element thiscat = document.createElement("LCIA-Category");
        		categoryList.appendChild(thiscat);
        		thiscat.appendChild(document.createTextNode(imcat));
        	}
        	Element factorList = document.createElement("LCIA-Factors");
        	lcia.appendChild(factorList);
        	for (String imfac : thisLCIA.getFactorSet().keySet()) {
        		Element thisfac = document.createElement("LCIA-Factor");
        		factorList.appendChild(thisfac);
        		thisfac.appendChild(document.createTextNode(imfac));
        	}
        }	
	}
	
	private void writeProductDeclarations(Document document, Element root) {
        Element allePDs = document.createElement("ProductDeclarations");
        root.appendChild(allePDs);
        
        for (String pdName : ProductDeclaration.getAllInstances().keySet()) {
        	ProductDeclaration thisPD = ProductDeclaration.getAllInstances().get(pdName);
        	Element pd = document.createElement("ProductDeclaration");
        	allePDs.appendChild(pd);
        	Element name = document.createElement("PD-Name");
        	pd.appendChild(name);
        	name.appendChild(document.createTextNode(pdName));
        	Element einheit = document.createElement("PD-Unit");
            pd.appendChild(einheit);
            einheit.appendChild(document.createTextNode(thisPD.getEinheit().toString()));
        	Element method = document.createElement("PD-Method");
            pd.appendChild(method);
            method.appendChild(document.createTextNode(thisPD.getBM().getName()));
			Element iv = document.createElement("ImpactValuesVector");
			pd.appendChild(iv);	            
            for(ImpactCategory ic : thisPD.getImpactValueMap(thisPD.getBM()).keySet()){
            	Element cat = document.createElement("IVV-Entry");
				iv.appendChild(cat);	
            	Element fname = document.createElement("ImpactCategorie-Name");
            	cat.appendChild(fname);
            	fname.appendChild(document.createTextNode(ic.getName()));
            	Element menge = document.createElement("ICV-MainValue");
            	cat.appendChild(menge);
            	menge.appendChild(document.createTextNode(thisPD.getImpactValueMap(thisPD.getBM()).get(ic).get(ValueType.MeanValue).toString()));
            	Element menge2 = document.createElement("ICV-LowerBound");
            	cat.appendChild(menge2);
            	menge2.appendChild(document.createTextNode(thisPD.getImpactValueMap(thisPD.getBM()).get(ic).get(ValueType.LowerBound).toString()));
            	Element menge3 = document.createElement("ICV-UpperBound");
            	cat.appendChild(menge3);
            	menge3.appendChild(document.createTextNode(thisPD.getImpactValueMap(thisPD.getBM()).get(ic).get(ValueType.UpperBound).toString()));
            }
        }		
	}
	
	private void writeComponents(Document document, Element root) {
        Element alleComponents = document.createElement("Components");
        root.appendChild(alleComponents);
        
        for (String comName : Component.getAllInstances().keySet()) {
        	Component thisCom = Component.getAllInstances().get(comName);
        	Element com1 = document.createElement("Component");
        	alleComponents.appendChild(com1);
        	Element name = document.createElement("Component-Name");
        	com1.appendChild(name);
        	name.appendChild(document.createTextNode(comName));
        	Element referenz = document.createElement("Component-Reference");
        	com1.appendChild(referenz);
        	referenz.appendChild(document.createTextNode(thisCom.getKomponente().getName().toString()));
        	Element mv = document.createElement("ComRef-MainValue");
        	com1.appendChild(mv);
        	mv.appendChild(document.createTextNode(thisCom.getValues().get(ValueType.MeanValue).toString()));
        	Element lv = document.createElement("ComRef-LowerBound");
        	com1.appendChild(lv);
        	lv.appendChild(document.createTextNode(thisCom.getValues().get(ValueType.LowerBound).toString()));
        	Element uv = document.createElement("ComRef-UpperBound");
        	com1.appendChild(uv);
        	uv.appendChild(document.createTextNode(thisCom.getValues().get(ValueType.UpperBound).toString()));
        }
		
	}
	
}
