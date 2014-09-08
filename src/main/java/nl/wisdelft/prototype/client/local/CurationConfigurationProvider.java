/**
 * 
 */
package nl.wisdelft.prototype.client.local;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import org.jboss.errai.ioc.client.api.IOCProvider;
import com.google.gwt.core.shared.GWT;

/**
 * @author oosterman
 */
@IOCProvider
@Singleton
public class CurationConfigurationProvider implements Provider<CurationConfiguration> {
	@Inject
	EntityManager em;

	@Override
	public CurationConfiguration get() {
		TypedQuery<CurationConfiguration> q = em.createNamedQuery("config", CurationConfiguration.class);
		boolean createNewConfig = false;
		CurationConfiguration conf = null;

		try {
			conf = q.getSingleResult();
			if (conf == null) createNewConfig = true;
		}
		catch (NoResultException ex) {
			createNewConfig = true;
		}
		catch (NonUniqueResultException ex) {
			createNewConfig = true;
		}

		// create new config
		if (createNewConfig) {
			conf = new CurationConfiguration();
			em.persist(conf);
			em.flush();
		}
		
		GWT.log("Produced CurationConfiguration with ID: "+conf.getId());
		
		return conf;

	}

}
