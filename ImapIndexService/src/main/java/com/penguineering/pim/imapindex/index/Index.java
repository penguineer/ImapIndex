package com.penguineering.pim.imapindex.index;

import java.net.URI;
import java.util.Collection;

public interface Index {
	/**
	 * Stores the provided mapping into the index. Conflicting mappings will be
	 * deleted!
	 * 
	 * @param mapping
	 *            The mapping to be stored.
	 * @throws IndexAccessException
	 *             If access to the index fails.
	 * @throws NullPointerException
	 *             (optional) if the mapping or one of its fields are
	 *             <code>null</code>.
	 */
	public void storeMapping(LocationMapping mapping)
			throws IndexAccessException;

	public void removeLocation(URI context, String location)
			throws IndexAccessException;

	public void removeId(URI context, URI id) throws IndexAccessException;

	public URI getId(URI context, String location) throws IndexAccessException;
	
	public String getLocation(URI context, URI id) throws IndexAccessException;

	public Collection<LocationMapping> getLocations(URI id)
			throws IndexAccessException;

	public Collection<URI> getContexts() throws IndexAccessException;
}
