package org.oiga.model.entities;

import org.springframework.data.neo4j.annotation.RelationshipEntity;

@RelationshipEntity(type="RATED")
public class RateInteraction extends Interaction{
	private double rate;

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
}
