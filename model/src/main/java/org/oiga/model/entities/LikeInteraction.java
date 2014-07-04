package org.oiga.model.entities;

import org.springframework.data.neo4j.annotation.RelationshipEntity;

@RelationshipEntity(type="LIKED")
public class LikeInteraction extends Interaction{
	private boolean liked = true;

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}
}
