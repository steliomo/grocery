/**
 *
 */
package mz.co.grocery.integ.resources.util;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author Stélio Moiane
 *
 */
public class BearerTokenUtil {

	public static String getBearerToken(final String apiKey, final String publicKey) {
		try {
			final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			final Cipher cipher = Cipher.getInstance("RSA");
			final byte[] encodedPublicKey = Base64.decodeBase64(publicKey);
			final X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
			final PublicKey pk = keyFactory.generatePublic(publicKeySpec);
			cipher.init(1, pk);
			final byte[] encryptedApiKey = Base64.encodeBase64(cipher.doFinal(apiKey.getBytes("UTF-8")));
			return new String(encryptedApiKey, "UTF-8");
		} catch (final Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
