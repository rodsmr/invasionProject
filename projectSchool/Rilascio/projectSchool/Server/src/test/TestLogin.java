/**
 * 
 */
package test;

import junit.framework.*;
import login.OperazioniLogin;

/**
 * @author Antonio Iannacci - Marco Rodella
 *
 */
public class TestLogin extends TestCase {

	private login.OperazioniLogin operazioniLogin = null;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		operazioniLogin = new login.OperazioniLogin();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void esitoPositivo() {
		assertEquals("ok@", operazioniLogin.login("anto", "anto"));
	}
	
	public void esitoNegativo() {
		assertEquals("ko@invalidData", operazioniLogin.login("ant", "anto"));
	}
	
	public void esitoOldLog() {
		assertEquals("ko@prevLog", operazioniLogin.login("anto", "anto"));
	}
	
	public void esitoExc() {
		assertEquals("ko@sqlExcp", operazioniLogin.login(null, "anto"));
	}
	
}