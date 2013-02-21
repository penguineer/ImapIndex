package com.penguineering.pim.imapindex.store;

import java.net.URI;

public interface ImapClient {

	public void connect(URI storeLocation) throws StoreException;
	
	public boolean isConnected();

	public URI getStoreLocation();
}
