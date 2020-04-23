package user.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PwdHandler {
	
	private static final SecureRandom RAND = new SecureRandom();
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1"; //le label de l'algo utilisé, SHA1
	private static final int ITERATIONS = 10000; //nb de fois que l'algo va s'executer sur le mdp, genre hashN = h(hashN-1), hashN-1 = h(hashN-2) 
												//etc... avec N le nombre d'itérations et h la fonction de hachage

	private static final int KEY_LENGTH = 128; //borné par l'algo utilisé, ici 160 bits, pas besoin de + je pense

	// Le salt c'est la donnée aleatoire en + qu'on utilise pour génerer des hash
	// Comme ça, meme si deux personnes ont comme mdp "abcd", ils n'auront pas le meme hash car ils auront deux salt différent
	// Plus sécurisé et pas beaucoup + difficile, ça protège des attaques par dictionnaire.
	public String generateSalt () {
		byte[] salt = new byte[128];
		RAND.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public Optional<String> hashPwd(String pwd, String salt) {
		char[] chars = pwd.toCharArray();
		byte[] bytes = salt.getBytes();

		PBEKeySpec spec = new PBEKeySpec(chars,bytes,ITERATIONS,KEY_LENGTH); // on créer les specifications du hash qu'on veut générer

		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
			byte[] hashedPwd = factory.generateSecret(spec).getEncoded(); // et pour génerer le hash on se sert bien de ses specs
			return Optional.of(Base64.getEncoder().encodeToString(hashedPwd));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			// TODO: handle exception
			System.err.println("Exception encountered in hashPwd");
			return Optional.empty();
		}
		finally {
			spec.clearPassword();
		}

	}
	// Le Optional<T> est isomorphe au type option en ocaml, ou Maybe en haskell
	public boolean verifyPwd (String pwd, String key, String salt) {
		Optional<String> hashedEntry = hashPwd(pwd,salt);
		if(!hashedEntry.isPresent())
			return false;
		return hashedEntry.get().equals(key);
	}
}
