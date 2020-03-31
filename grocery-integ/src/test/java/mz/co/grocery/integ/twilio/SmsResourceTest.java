/**
 *
 */
package mz.co.grocery.integ.twilio;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import mz.co.grocery.integ.resources.util.SmsResource;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class SmsResourceTest {

	private SmsResource smsResource;

	@Before
	public void setup() {
		this.smsResource = new SmsResource();
	}

	@Test
	@Ignore
	public void shouldSendSMS() throws BusinessException {
		final String send = this.smsResource.send("840546824", "Teste feito.....");
		assertNotNull(send);
	}
}
