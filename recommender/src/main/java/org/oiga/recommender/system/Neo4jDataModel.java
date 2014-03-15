package org.oiga.recommender.system;

import java.util.Collection;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

public class Neo4jDataModel implements DataModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4025171527638218569L;

	@Override
	public void refresh(Collection<Refreshable> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LongPrimitiveIterator getItemIDs() throws TasteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FastIDSet getItemIDsFromUser(long arg0) throws TasteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getMaxPreference() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMinPreference() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumItems() throws TasteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumUsers() throws TasteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumUsersWithPreferenceFor(long arg0) throws TasteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumUsersWithPreferenceFor(long arg0, long arg1)
			throws TasteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long getPreferenceTime(long arg0, long arg1) throws TasteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float getPreferenceValue(long arg0, long arg1) throws TasteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreferenceArray getPreferencesForItem(long arg0)
			throws TasteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreferenceArray getPreferencesFromUser(long arg0)
			throws TasteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LongPrimitiveIterator getUserIDs() throws TasteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPreferenceValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removePreference(long arg0, long arg1) throws TasteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPreference(long arg0, long arg1, float arg2)
			throws TasteException {
		// TODO Auto-generated method stub
		
	}
	
}
