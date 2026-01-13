package com.project.EnergyMarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import java.math.BigInteger;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    @Autowired
    private EnergyMarketService energyMarketService;

    @Autowired
    private EnergyTokenService energyTokenService;

    // Esempio: GET /energy/produce?fromAddress=0x123...&energyAmount=100
    @GetMapping("/produce")
    public String produceEnergy(@RequestParam String fromAddress, @RequestParam String energyAmount) {
        System.out.println("Indirizzo del contratto: " + energyMarketService.getContractAddress());
        try {
            TransactionReceipt receipt = energyMarketService.produceEnergy(fromAddress, new BigInteger(energyAmount));
            return "produceEnergy eseguito. Transaction hash: " + receipt.getTransactionHash();
        } catch (Exception e) {
            return "Errore in produceEnergy: " + e.getMessage();
        }
    }

    // Esempio: GET /energy/buy?fromAddress=0x123...&energyAmount=100
    @GetMapping("/buy")
    public String buyEnergy(@RequestParam String fromAddress, @RequestParam String producer, @RequestParam String energyAmount) {
        try {
            TransactionReceipt receipt = energyMarketService.buyEnergy(fromAddress,producer, new BigInteger(energyAmount));
            return "buyEnergy eseguito. Transaction hash: " + receipt.getTransactionHash();
        } catch (Exception e) {
            return "Errore in buyEnergy: " + e.getMessage();
        }
    }

    // Esempio: GET /energy/burn?fromAddress=0x123...&energyAmount=100
    @GetMapping("/consume")
    public String consumeEnergy(@RequestParam String fromAddress, @RequestParam String energyAmount) {
        try {
            TransactionReceipt receipt = energyMarketService.consumeEnergy(fromAddress, new BigInteger(energyAmount));
            return "consumeEnergy eseguito. Transaction hash: " + receipt.getTransactionHash();
        } catch (Exception e) {
            return "Errore in consumeEnergy: " + e.getMessage();
        }
    }

    @GetMapping("/balanceOf")
    public BigInteger balanceOf(@RequestParam String address){
        try {
            return energyTokenService.balanceOf(address);
        } catch (Exception e) {
            System.out.println("Errore in balanceOf " + e.getMessage());
            return BigInteger.valueOf(-1);
        }

        
    }
}
