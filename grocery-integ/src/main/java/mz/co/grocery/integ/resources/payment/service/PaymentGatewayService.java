/**
 *
 */
package mz.co.grocery.integ.resources.payment.service;

/**
 * @author Stélio Moiane
 *
 */
public interface PaymentGatewayService<T, L> {

	L makePayment(T t);

}
