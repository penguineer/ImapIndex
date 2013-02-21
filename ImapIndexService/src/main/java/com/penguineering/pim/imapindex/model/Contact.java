package com.penguineering.pim.imapindex.model;

import java.net.URI;

import net.jcip.annotations.Immutable;

@Immutable
public interface Contact {

	public URI getAddress();

	public String getLabel();

}
