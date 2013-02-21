package com.penguineering.pim.imapindex.store.impl;

import java.net.URI;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import com.penguineering.pim.imapindex.store.ImapClient;
import com.penguineering.pim.imapindex.store.StoreException;

public class JavaMailImapClient implements ImapClient {
	private Store store = null;

	public JavaMailImapClient() {
		super();
	}

	@Override
	public void connect(URI location) throws StoreException {
		if (location == null)
			throw new NullPointerException(
					"Location argument must not be null!");

		try {
			Session session = Session
					.getDefaultInstance(System.getProperties());

			this.store = session.getStore(new URLName(location.toString()));

			store.connect();
		} catch (NoSuchProviderException e) {
			throw new StoreException("Unknown store protocol: "
					+ e.getMessage(), e);
		} catch (MessagingException e) {
			throw new StoreException("Error connecting to store: "
					+ e.getMessage(), e);
		}
	}

	@Override
	public boolean isConnected() {
		return store != null && store.isConnected();
	}

	@Override
	public URI getStoreLocation() {
		return store == null ? null : URI.create(store.getURLName().toString());
	}
}
