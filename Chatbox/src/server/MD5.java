package server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5 {

	private static MessageDigest messageDigest;

	static {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String generateSalt() {

		Random r = new Random();

		String set = "abcdefghijklmnopqrstuvwxyz`~1234567890-=!@#$%^&*()_+";
		String salt = "";

		for (int i = 0; i < 5; i++) {
			salt += set.charAt(r.nextInt(set.length()));
		}

		return salt;
	}

	public static String hash(String text) {
		byte[] md5hash;
		try {
			synchronized (messageDigest) {
				messageDigest.reset();
				messageDigest.update(text.getBytes("iso-8859-1"), 0, text.length());
				md5hash = messageDigest.digest();
			}
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (byte hashByte : md5hash) {
			int halfbyte = (hashByte >>> 4) & 0x0F;
			for (int i = 0; i < 2; i++) {
				if (0 <= halfbyte && halfbyte <= 9) {
					buf.append((char) ('0' + halfbyte));
				} else {
					buf.append((char) ('a' + (halfbyte - 10)));
				}
				halfbyte = hashByte & 0x0F;
			}
		}
		return buf.toString();
	}

	public static String hashedCode(String salt, String password) {
		return MD5.hash(MD5.hash(salt) + MD5.hash(password));
	}

}
