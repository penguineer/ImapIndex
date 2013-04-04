package com.penguineering.pim.imapindex.index;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

public class SqlIndex implements Index {
	final DataSource ds;

	public SqlIndex(DataSource ds) {
		this.ds = ds;
	}

	private void _closeCon(Connection con) throws IndexAccessException {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				throw new IndexAccessException("Error closing SQL connection: "
						+ e.getMessage(), e);
			}
	}

	@Override
	public void storeMapping(LocationMapping mapping)
			throws IndexAccessException {

		Connection con = null;
		try {
			con = ds.getConnection();

			final PreparedStatement pst = con
					.prepareStatement("REPLACE INTO locationmappings "
							+ "(context, location, id) VALUES (?,?,?) ");
			pst.setString(1, mapping.getContext().toASCIIString());
			pst.setString(2, mapping.getLocation());
			pst.setString(3, mapping.getId().toASCIIString());

			pst.executeUpdate();

			pst.close();
		} catch (SQLException e) {
			throw new IndexAccessException("SQL access exception: "
					+ e.getMessage(), e);
		} finally {
			_closeCon(con);
			con = null;
		}
	}

	private void _remove(URI context, String field, String value)
			throws IndexAccessException {
		Connection con = null;
		try {
			con = ds.getConnection();

			final PreparedStatement pst = con
					.prepareStatement("DELETE FROM locationmappings "
							+ "WHERE " + field + "=? AND context=?");
			pst.setString(1, value);
			pst.setString(2, context.toASCIIString());

			pst.executeUpdate();

			pst.close();
		} catch (SQLException e) {
			throw new IndexAccessException("SQL access exception: "
					+ e.getMessage(), e);
		} finally {
			_closeCon(con);
			con = null;
		}

	}

	@Override
	public void removeLocation(URI context, String location)
			throws IndexAccessException {
		_remove(context, "location", location);
	}

	@Override
	public void removeId(URI context, URI id) throws IndexAccessException {
		_remove(context, "id", id.toASCIIString());
	}

	private String _find(URI context, String target, String field, String value)
			throws IndexAccessException {
		final String result;

		Connection con = null;
		try {
			con = ds.getConnection();

			final PreparedStatement pst = con.prepareStatement("SELECT "
					+ target + " FROM locationmappings "
					+ "WHERE context=? AND " + field + "=?");
			pst.setString(1, context.toASCIIString());
			pst.setString(2, value);

			final ResultSet rs = pst.executeQuery();
			result = rs.next() ? rs.getString(1) : null;

			rs.close();
			pst.close();
		} catch (SQLException e) {
			throw new IndexAccessException("SQL access exception: "
					+ e.getMessage(), e);
		} finally {
			_closeCon(con);
			con = null;
		}

		return result;

	}

	@Override
	public URI getId(URI context, String location) throws IndexAccessException {
		final String id = _find(context, "id", "location", location);
		return id == null ? null : _string2uri(id);
	}

	@Override
	public String getLocation(URI context, URI id) throws IndexAccessException {
		return _find(context, "location", "id", id.toASCIIString());
	}

	@Override
	public Collection<LocationMapping> getLocations(URI id)
			throws IndexAccessException {
		Collection<LocationMapping> mappings = new ArrayList<LocationMapping>();

		Connection con = null;
		try {
			con = ds.getConnection();

			final PreparedStatement pst = con
					.prepareStatement("SELECT context, location "
							+ "FROM locationmappings WHERE id=?");
			pst.setString(1, id.toASCIIString());

			final ResultSet rs = pst.executeQuery();

			while (rs.next())
				mappings.add(LocationMapping.newInstance(
						_string2uri(rs.getString(1)), rs.getString(2), id));

			rs.close();
			pst.close();
		} catch (SQLException e) {
			throw new IndexAccessException("SQL access exception: "
					+ e.getMessage(), e);
		} finally {
			_closeCon(con);
			con = null;
		}

		return mappings;
	}

	private URI _string2uri(String s) throws IndexAccessException {
		try {
			return new URI(s);
		} catch (URISyntaxException e) {
			throw new IndexAccessException("Invalid URI in database! At: "
					+ e.getMessage(), e);
		}
	}

	@Override
	public Collection<URI> getContexts() throws IndexAccessException {
		final Collection<URI> contexts = new ArrayList<URI>();

		Connection con = null;
		try {
			con = ds.getConnection();

			final PreparedStatement pst = con
					.prepareStatement("SELECT DISTINCT context FROM locationmappings");

			final ResultSet rs = pst.executeQuery();

			while (rs.next())
				contexts.add(_string2uri(rs.getString(1)));

			rs.close();
			pst.close();
		} catch (SQLException e) {
			throw new IndexAccessException("SQL access exception: "
					+ e.getMessage(), e);
		} finally {
			_closeCon(con);
			con = null;
		}

		return contexts;
	}
}
