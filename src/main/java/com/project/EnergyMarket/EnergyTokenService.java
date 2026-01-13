package com.project.EnergyMarket;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
public class EnergyTokenService {

    @Autowired
    private Web3j web3j;

    // (impostato al deploy)
    private String contractAddress;

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractAddress() {
        return this.contractAddress;
    }
    
    public BigInteger balanceOf(String fromAddress) throws Exception {
        if (contractAddress == null) {
            throw new Exception("Contratto EnergyToken non deployato.");
        }
        Credentials credentials = Credentials.create(fromAddress);
        EnergyToken energyToken = EnergyToken.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return energyToken.balanceOf(fromAddress).send();
    }

    public TransactionReceipt mint(String fromAddress, BigInteger amount) throws Exception {
        if (contractAddress == null) {
            throw new Exception("Contratto EnergyToken non deployato.");
        }
        Credentials credentials = Credentials.create(AccountRegistry.getAccountRegistry().getPrivateKey(fromAddress));
        EnergyToken energyToken = EnergyToken.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return energyToken.mint(fromAddress, amount).send();
    }


}
