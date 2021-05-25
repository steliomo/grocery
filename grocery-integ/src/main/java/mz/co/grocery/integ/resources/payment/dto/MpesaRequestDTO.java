/**
 *
 */
package mz.co.grocery.integ.resources.payment.dto;

/**
 * @author Stélio Moiane
 *
 */
public class MpesaRequestDTO {

	private static final String AREA_CODE = "258";

	private String input_TransactionReference;

	private String input_CustomerMSISDN;

	private String input_Amount;

	private String input_ThirdPartyReference;

	private String input_ServiceProviderCode;

	public MpesaRequestDTO() {
	}

	public MpesaRequestDTO(final String input_TransactionReference, final String input_CustomerMSISDN, final String input_Amount,
			final String input_ThirdPartyReference,
			final String input_ServiceProviderCode) {
		this.input_TransactionReference = input_TransactionReference;
		this.input_CustomerMSISDN = MpesaRequestDTO.AREA_CODE + input_CustomerMSISDN;
		this.input_Amount = input_Amount;
		this.input_ThirdPartyReference = input_ThirdPartyReference;
		this.input_ServiceProviderCode = input_ServiceProviderCode;
	}

	public String getInput_TransactionReference() {
		return this.input_TransactionReference;
	}

	public String getInput_CustomerMSISDN() {
		return this.input_CustomerMSISDN;
	}

	public String getInput_Amount() {
		return this.input_Amount;
	}

	public String getInput_ThirdPartyReference() {
		return this.input_ThirdPartyReference;
	}

	public String getInput_ServiceProviderCode() {
		return this.input_ServiceProviderCode;
	}

}
