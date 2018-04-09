package com.urz.cipher.encryption.engine;

import java.io.IOException;

public interface Cipher {
    public String encrypt(String key, String text);
    public String decrypt(String key, String text) throws IOException;
    public String prepareText(String text);
    public boolean isKeyValid(String key);
}
