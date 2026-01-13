package com.project.EnergyMarket;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Component
@Order(2) 
public class ContractOperationsRunner implements CommandLineRunner {

    @Autowired
    private EnergyMarketService energyMarketService;
    
    @Autowired
    private EnergyTokenService energyTokenService;

    @Override
    public void run(String... args) throws Exception {
        
        try{
                
         energyMarketService.registerUser("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266", BigInteger.valueOf(1));
         energyMarketService.registerUser("0x70997970C51812dc3A010C7d01b50e0d17dc79C8", BigInteger.valueOf(1));
        
        // System.out.println("Prezzo di 0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266 = " + energyMarketService.getUserPrice("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266", "0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266"));
        // energyMarketService.setUserPrice("0x70997970C51812dc3A010C7d01b50e0d17dc79C8", BigInteger.valueOf(4));
        // System.out.println("Prezzo di 0x70997970C51812dc3A010C7d01b50e0d17dc79C8 = " + energyMarketService.getUserPrice("0x70997970C51812dc3A010C7d01b50e0d17dc79C8", "0x70997970C51812dc3A010C7d01b50e0d17dc79C8"));
          

         TransactionReceipt receipt1 = energyMarketService.produceEnergy("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266", BigInteger.valueOf(100));
         System.out.println("Prodotta energia con costo " + receipt1.getGasUsed());

         TransactionReceipt receipt2 = energyMarketService.buyEnergy("0x70997970C51812dc3A010C7d01b50e0d17dc79C8",
         "0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266", BigInteger.valueOf(20));
         System.out.println("Acquistata energia con costo " + receipt2.getGasUsed());

         TransactionReceipt receipt3 = energyMarketService.consumeEnergy("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266", BigInteger.valueOf(5));
         System.out.println("Consumata energia con costo " + receipt3.getGasUsed());

         System.out.println("Balance di 0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266 = " + energyTokenService.balanceOf("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266"));
        
        } catch (Exception e){
           System.out.println("errore4: " + e.getMessage());
        }
    }
}
