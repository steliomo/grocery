/**
 *
 */
package mz.co.grocery.integ.resources.payment.dto;

/**
 * @author Stélio Moiane
 *
 */
public class MpesaResponseDTO {

	private String output_ConversationID;

	private String output_ResponseCode;

	private String output_ResponseDesc;

	private String output_ThirdPartyReference;

	private String output_TransactionID;

	public String getOutput_ConversationID() {
		return this.output_ConversationID;
	}

	public String getOutput_ResponseCode() {
		return this.output_ResponseCode;
	}

	public String getOutput_ResponseDesc() {
		return this.output_ResponseDesc;
	}

	public String getOutput_ThirdPartyReference() {
		return this.output_ThirdPartyReference;
	}

	public String getOutput_TransactionID() {
		return this.output_TransactionID;
	}

}
