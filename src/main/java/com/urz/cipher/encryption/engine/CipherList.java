package com.urz.cipher.encryption.engine;

import com.urz.cipher.encryption.Caesar;
import com.urz.cipher.encryption.Fence;

import java.util.Arrays;
import java.util.List;

public class CipherList {
    public static List<Cipher> getCipherList() {
        return Arrays.asList(new Caesar(), new Fence());
    }
}
