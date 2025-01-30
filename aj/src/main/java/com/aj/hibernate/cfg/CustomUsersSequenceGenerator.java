package com.aj.hibernate.cfg;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import com.aj.model.Users;

public class CustomUsersSequenceGenerator implements IdentifierGenerator, Configurable {
	private String sequenceCallSyntax;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		final JdbcEnvironment jdbcEnvironment = serviceRegistry.getService(JdbcEnvironment.class);
		final Dialect dialect = jdbcEnvironment.getDialect();
		sequenceCallSyntax = dialect
				.getSequenceNextValString(ConfigurationHelper.getString(SequenceStyleGenerator.SEQUENCE_PARAM, params));
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) {
		Users myEntity = (Users) obj;
		if (myEntity.getId() > 0) {
			return myEntity.getId();
		} else {
			return ((Number) Session.class.cast(session).createNativeQuery(sequenceCallSyntax).uniqueResult())
					.longValue();
		}
	}
}
