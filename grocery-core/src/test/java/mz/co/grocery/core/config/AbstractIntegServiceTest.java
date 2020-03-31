/**
 *
 */
package mz.co.grocery.core.config;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Stélio Moiane
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractIntegServiceTest extends AbstractServiceTest {

}
