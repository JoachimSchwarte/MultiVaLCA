/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.HashMap;
import java.util.LinkedHashMap;

import de.unistuttgart.iwb.ivari.IvariScalar;

/**
 * Vaterklasse für
 * Prozessmodule und
 * Produktsysteme
 * (generalisiertes Prozessmodul)
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.554
 */

public class GenPM extends MCAObject implements ImpactValueMaps {

	private HashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> efv;

	protected GenPM(String name) {
		super(name);
	}

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wv =
				new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();
		LinkedHashMap<ValueType, Double> values0 = new LinkedHashMap<ValueType, Double>();
		values0.put(ValueType.MeanValue, 0.);
		values0.put(ValueType.LowerBound, 0.);
		values0.put(ValueType.UpperBound, 0.);
		for (String wk : bm.categoryList().keySet()){
			wv.put(bm.categoryList().get(wk), values0);
		}
		for (String cfName : bm.getFactorSet().keySet()){
			CharacFactor cf = bm.getFactorSet().get(cfName);
			if (efv.containsKey(cf.getFlow())) {
				LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();				
				double mv0 = wv.get(cf.getWirkung()).get(ValueType.MeanValue);
				IvariScalar iv0 = new IvariScalar();
				iv0.setLowerBound(wv.get(cf.getWirkung()).get(ValueType.LowerBound));
				iv0.setUpperBound(wv.get(cf.getWirkung()).get(ValueType.UpperBound));
				double mvc = cf.getValue(ValueType.MeanValue);
				IvariScalar ivc = new IvariScalar();
				ivc.setLowerBound(cf.getValue(ValueType.LowerBound));
				ivc.setUpperBound(cf.getValue(ValueType.UpperBound));
				double mvf = efv.get(cf.getFlow()).get(ValueType.MeanValue);
				IvariScalar ivf = new IvariScalar();
				ivf.setLowerBound(efv.get(cf.getFlow()).get(ValueType.LowerBound));
				ivf.setUpperBound(efv.get(cf.getFlow()).get(ValueType.UpperBound));
				double mvr = mvc * mvf;
				IvariScalar ivr = ivc.mult(ivf);
				values.put(ValueType.MeanValue, mv0 + mvr);
				values.put(ValueType.LowerBound, iv0.getLowerBound() + ivr.getLowerBound());
				values.put(ValueType.UpperBound, iv0.getUpperBound() + ivr.getUpperBound());
				wv.put(cf.getWirkung(), values);
			}		
		}
		return wv;
	}
}
