package com.penguineering.pim.imapindex.index;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import net.jcip.annotations.Immutable;

/**
 * Mapping from a location within a context to an id.
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
@Immutable
public class LocationMapping {
	public static LocationMapping newInstance(URI context, String location,
			URI id) {
		return new LocationMapping(context, location, id);
	}

	public static Collection<LocationMapping> filterByContext(URI context,
			Collection<LocationMapping> mappings) {
		final Collection<LocationMapping> result = new ArrayList<LocationMapping>(
				mappings.size());

		for (LocationMapping mapping : mappings)
			if (context.equals(mapping.getContext()))
				result.add(mapping);

		return result;
	}

	private final URI context;
	private final String location;
	private final URI id;

	private LocationMapping(URI context, String location, URI id) {
		super();
		this.context = context;
		this.location = location;
		this.id = id;
	}

	public URI getContext() {
		return context;
	}

	public String getLocation() {
		return location;
	}

	public URI getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(context.toASCIIString());
		sb.append(" - ");
		sb.append(location);
		sb.append(" - ");
		sb.append(id.toASCIIString());
		sb.append(")");

		return sb.toString();
	}
}
