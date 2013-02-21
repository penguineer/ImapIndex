package com.penguineering.pim.imapindex.model;

import java.net.URI;
import java.util.List;

import net.jcip.annotations.Immutable;

@Immutable
public interface EMail {

	public URI getMessageId();

	public Contact getFrom();

	public String getSubject();

	// TODO Date sent

	public List<Contact> getTo();

	public List<Contact> getCc();

	public List<Contact> getBcc();

	public URI getInReplyTo();

	public List<URI> getReferences();

}
