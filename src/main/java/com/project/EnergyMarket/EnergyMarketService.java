package com.project.EnergyMarket;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
public class EnergyMarketService {

    @Autowired
    private Web3j web3j;

    AccountRegistry accountRegistry = AccountRegistry.getAccountRegistry();

    // Indirizzo del contratto EnergyMarket (impostato al deploy)
    private String contractAddress;

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractAddress() {
        return this.contractAddress;
    }

    public TransactionReceipt produceEnergy(String fromAddress, BigInteger energyAmount) throws Exception {
        if (contractAddress == null) {
            throw new Exception("Contratto EnergyMarket non deployato.");
        }
        Credentials credentials = Credentials.create(accountRegistry.getPrivateKey(fromAddress));
        EnergyMarket energyMarket = EnergyMarket.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return energyMarket.produceEnergy(energyAmount).send();
    }

    public TransactionReceipt buyEnergy(String fromAddress, String producer, BigInteger energyAmount) throws Exception {
        if (contractAddress == null) {
            throw new Exception("Contratto EnergyMarket non deployato.");
        }

        Credentials credentials = Credentials.create(accountRegistry.getPrivateKey(fromAddress));
        EnergyMarket energyMarket = EnergyMarket.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return energyMarket.buyEnergy(producer, energyAmount,
                energyAmount.multiply(energyMarket.getUserPrice(producer).send())).send();
    }

    public TransactionReceipt consumeEnergy(String fromAddress, BigInteger energyAmount) throws Exception {
        if (contractAddress == null) {
            throw new Exception("Contratto EnergyMarket non deployato.");
        }
        Credentials credentials = Credentials.create(accountRegistry.getPrivateKey(fromAddress));
        EnergyMarket energyMarket = EnergyMarket.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return energyMarket.consumeEnergy(energyAmount).send();
    }

    public TransactionReceipt registerUser(String fromAddress, BigInteger pricePerToken) throws Exception {
        if (contractAddress == null) {
            throw new Exception("Contratto EnergyMarket non deployato.");
        }
        Credentials credentials = Credentials.create(accountRegistry.getPrivateKey(fromAddress));
        EnergyMarket energyMarket = EnergyMarket.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return energyMarket.registerUser(pricePerToken).send();
    }

    public TransactionReceipt setUserPrice(String fromAddress, BigInteger price) throws Exception {
        if (contractAddress == null) {
            throw new Exception("Contratto EnergyMarket non deployato.");
        }
        Credentials credentials = Credentials.create(accountRegistry.getPrivateKey(fromAddress));
        EnergyMarket energyMarket = EnergyMarket.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return energyMarket.setUserPrice(price).send();
    }

    public BigInteger getUserPrice(String fromAddress, String producer) throws Exception {
        if (contractAddress == null) {
            throw new Exception("Contratto EnergyMarket non deployato.");
        }
        Credentials credentials = Credentials.create(accountRegistry.getPrivateKey(fromAddress));
        EnergyMarket energyMarket = EnergyMarket.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return energyMarket.getUserPrice(producer).send();
    }
}
