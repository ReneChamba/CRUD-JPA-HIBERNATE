package com.jpa.curso.crud_jpahibernate.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static final String UNITY_PERSISTENCE ="Persistencia";
	
	private static EntityManagerFactory factory;
	
	public static  EntityManagerFactory getEntityFactory() {
		
		if (factory ==null) {
			factory = Persistence.createEntityManagerFactory(UNITY_PERSISTENCE);
		}
		return factory;		
	}
	
	public static void closeEntityFactory(){
		
		if (factory !=null && factory.isOpen()) {
			factory.close();
		}		
	}

}
