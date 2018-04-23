package com.urz.cipher.encryption.engine;

import com.urz.cipher.encryption.Caesar;
import com.urz.cipher.encryption.Fence;
import com.urz.cipher.encryption.Hill;
import com.urz.cipher.encryption.Playfair;

import java.util.Arrays;
import java.util.List;

public class CipherList {
    public static List<Cipher> getCipherList() {
        return Arrays.asList(new Caesar(), new Fence(), new Hill(), new Playfair());
    }
}
