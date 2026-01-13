package com.project.EnergyMarket;

import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import java.util.HashMap;
import java.util.Map;

public class AccountRegistry {
    private  Map<String, String> accountMap = new HashMap<>(); // <Indirizzo, Chiave Privata>
    private static AccountRegistry accountRegistry = new AccountRegistry("test test test test test test test test test test test junk",10);

    private AccountRegistry(String mnemonic, int numAccounts) {
        initializeFromMnemonic(mnemonic, numAccounts);
    }

    public static AccountRegistry getAccountRegistry(){
        return accountRegistry;
    }

    private void initializeFromMnemonic(String mnemonic, int numAccounts) {
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
        int[] derivationPathBase = {44 | 0x80000000, 60 | 0x80000000, 0 | 0x80000000, 0};

        for (int i = 0; i < numAccounts; i++) {
            int[] derivationPath = new int[derivationPathBase.length + 1];
            System.arraycopy(derivationPathBase, 0, derivationPath, 0, derivationPathBase.length);
            derivationPath[derivationPathBase.length] = i;

            Bip32ECKeyPair childKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath);
            Credentials credentials = Credentials.create(childKeyPair);

            String address = credentials.getAddress();
            String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
      
            accountMap.put(address, "0x" + privateKey);
        }
    }

    public String getPrivateKey(String address) {
        return accountMap.get(address.toLowerCase());
    }

    public void addAccount(String privateKey) {
        Credentials credentials = Credentials.create(privateKey);
        accountMap.put(credentials.getAddress(), privateKey);
    }

    public Map<String, String> getAllAccounts() {
        return (accountMap);
    }
}
