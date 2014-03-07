package com.penguineering.pim.imapindex.index;

import java.beans.PropertyVetoException;
import java.net.URI;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.penguineering.pim.imapindex.common.Identifiers;

public class SqlTest {

	public static DataSource initDS() throws PropertyVetoException {
		ComboPooledDataSource cpds = new ComboPooledDataSource();

		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl("jdbc:mysql://suedpol.homelinux.net/imapindex_test");
		cpds.setUser("imapindex");
		cpds.setPassword("ii$");

		return cpds;
	}

	/**
	 * @param args
	 * @throws PropertyVetoException
	 * @throws SQLException
	 * @throws IndexAccessException
	 */
	public static void main(String[] args) throws PropertyVetoException,
			SQLException, IndexAccessException {
		final DataSource ds = initDS();
		final URI context = URI.create(Identifiers.URI_PREFIX);

		final Index index = new SqlIndex(ds);

		index.storeMapping(LocationMapping.newInstance(context, "loc1",
				URI.create("message:1")));

		index.storeMapping(LocationMapping.newInstance(context, "loc2",
				URI.create("message:2")));

		index.storeMapping(LocationMapping.newInstance(context, "loc1",
				URI.create("message:3")));

		index.storeMapping(LocationMapping.newInstance(context, "loc1",
				URI.create("message:1")));

		System.out.println(index.getId(context, "loc1"));
		System.out.println(index.getLocation(context, URI.create("message:2")));

		System.out.println(index.getLocations(URI.create("message:2")));
		System.out.println(index.getContexts());

		index.removeLocation(context, "loc1");
		index.removeId(context, URI.create("message:2"));
	}
}
