package com.penguineering.pim.imapindex.service;

import java.util.Collection;

import de.ovgu.dke.mocca.api.MoccaException;
import de.ovgu.dke.mocca.api.MoccaRuntime;
import de.ovgu.dke.mocca.api.command.CommandHandler;
import de.ovgu.dke.mocca.daemon.MoccaAgent;
import de.ovgu.dke.mocca.daemon.MoccaShell;

public class ImapService implements MoccaAgent {

	@Override
	public void destroy() {

	}

	@Override
	public Collection<CommandHandler> getCommandHandlers() {
		return null;
	}

	@Override
	public String getName() {
		return "IMAP Index Service";
	}

	@Override
	public void init(MoccaRuntime runtime) throws MoccaException {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MoccaShell.main(new String[] { ImapService.class.getCanonicalName() });
	}
}
