/**
 *
 */
package mz.co.grocery.integ.resources.util;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Service(SmsResource.NAME)
public class SmsResource {

	public static final String NAME = "mz.co.grocery.integ.resources.util.SmsResource";

	public static final String ACCOUNT_SID = "ACec411d4d0268efc159bb9ba6f200ed8e";

	public static final String AUTH_TOKEN = "4b14baa28cafe6e0cd508b721c5fff80";

	public static final String TO_NAME = "MERCEARIAS";

	public static final String AREA_CODE = "+258";

	public String send(final String to, final String text) throws BusinessException {
		Twilio.init(SmsResource.ACCOUNT_SID, SmsResource.AUTH_TOKEN);

		final Message message = Message
		        .creator(new PhoneNumber(AREA_CODE + to), new PhoneNumber(SmsResource.TO_NAME), text).create();

		return message.getSid();
	}
}
