/**
 *
 */
package mz.co.grocery.core.application.payment.in;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface SubscriptionRenewalNotificationUseCase {

	List<Unit> sendNotification(LocalDate notificationDate) throws BusinessException;

}
