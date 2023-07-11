/**
 *
 */
package mz.co.grocery.integ.resources.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Stélio Moiane
 *
 */
@Component
public class Mpesa {

	@Value("${mpesa.payment.url}")
	private String url;

	@Value("${mpesa.payment.api.key}")
	private String apiKey;

	@Value("${mpesa.payment.public.key}")
	private String publicKey;

	@Value("${mpesa.payment.thirt.party.reference}")
	private String thirtPartyReference;

	@Value("${mpesa.payment.provider.code}")
	private String providerCode;

	public Mpesa() {
	}

	public Mpesa(final String url, final String apiKey, final String publicKey, final String thirtPartyReference, final String providerCode) {
		this.url = url;
		this.apiKey = apiKey;
		this.publicKey = publicKey;
		this.thirtPartyReference = thirtPartyReference;
		this.providerCode = providerCode;
	}

	public String getUrl() {
		return this.url;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public String getPublicKey() {
		return this.publicKey;
	}

	public String getThirtPartyReference() {
		return this.thirtPartyReference;
	}

	public String getProviderCode() {
		return this.providerCode;
	}
}
