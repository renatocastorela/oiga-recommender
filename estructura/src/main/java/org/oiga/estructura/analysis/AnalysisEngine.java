package org.oiga.estructura.analysis;

import java.util.List;
import java.util.Map;

public interface AnalysisEngine<T> {
	public List<T> analyse(List<Map<String,String>> data);
}
