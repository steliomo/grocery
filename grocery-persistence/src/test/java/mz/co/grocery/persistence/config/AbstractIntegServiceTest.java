/**
 *
 */
package mz.co.grocery.persistence.config;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import mz.co.grocery.core.GroceryCoreApplication;
import mz.co.grocery.persistence.GroceryPersistenceApplication;

/**
 * @author Stélio Moiane
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GroceryCoreApplication.class,
		GroceryPersistenceApplication.class })
@ActiveProfiles("test")
public abstract class AbstractIntegServiceTest extends AbstractServiceTest {

}
