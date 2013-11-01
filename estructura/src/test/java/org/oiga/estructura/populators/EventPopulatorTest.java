package org.oiga.estructura.populators;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.support.node.Neo4jHelper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:db-context.xml","classpath:estructura-context.xml"})
@Transactional
public class EventPopulatorTest {
	
	@Autowired
	private EventPopulator eventPopulator;
	
	@Before
	public void setUp(){
		
	}
	
	
	@Test
	public void test() {
		eventPopulator.populate();
	}

}
