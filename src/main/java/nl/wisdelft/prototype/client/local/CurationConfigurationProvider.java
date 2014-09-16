/**
 * 
 */
package nl.wisdelft.prototype.client.local;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;

import org.jboss.errai.ioc.client.api.IOCProvider;
import org.slf4j.Logger;

/**
 * @author oosterman
 */
@IOCProvider
@Singleton
public class CurationConfigurationProvider implements Provider<CurationConfiguration> {
	@Inject
	EntityManager em;

	@Inject
	Logger logger;

	@Override
	public CurationConfiguration get() {
		TypedQuery<CurationConfiguration> q = em.createNamedQuery("config", CurationConfiguration.class);
		boolean createNewConfig = false;
		CurationConfiguration conf = null;

		try {
			conf = q.getSingleResult();
			if (conf == null) {
				createNewConfig = true;
			}
		} catch (NoResultException ex) {
			createNewConfig = true;
		} catch (NonUniqueResultException ex) {
			// remove all current configs
			List<CurationConfiguration> configs = q.getResultList();
			for (CurationConfiguration c : configs) {
				em.remove(c);
			}
			createNewConfig = true;
		}

		// create new config
		if (createNewConfig) {
			conf = new CurationConfiguration();
		}

		logger.info("Configuration loaded");
		return conf;
	}

	public void setConfiguration(@Observes CurationConfiguration config) {
		if (em.contains(config)) {
			em.merge(config);
		} else {
			em.persist(config);
		}
	}

}
