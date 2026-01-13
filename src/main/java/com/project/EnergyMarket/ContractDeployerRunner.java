package com.project.EnergyMarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

@Component
@Order(1)
public class ContractDeployerRunner implements CommandLineRunner {

  @Autowired
  private Web3j web3j;

  @Autowired
  private EnergyMarketService energyMarketService;

  @Autowired
  private EnergyTokenService energyTokenService;

  private final String deployerAddress = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80"; 


  @Override
  public void run(String... args) throws Exception {

    System.out.println("Connesso a Ethereum client: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
    Credentials credentials = Credentials.create(deployerAddress);

    String energyTokenAddress = getDeployedContractAddress("./src/main/resources/contracts/energyToken_address.txt");
    String energyMarketAddress = getDeployedContractAddress("./src/main/resources/contracts/energyMarket_address.txt");

    if (!energyTokenAddress.isEmpty()) { // carica il contratto
      System.out.println("Contratto già deployato a: " + energyTokenAddress);
      // EnergyToken energyToken = EnergyToken.load(energyTokenAddress, web3j, credentials, new DefaultGasProvider());
      energyTokenService.setContractAddress(energyTokenAddress);
    } 
    else { // Deploy del contratto
      EnergyToken energyToken = EnergyToken.deploy(web3j, credentials, new DefaultGasProvider()).send();
      energyTokenService.setContractAddress(energyToken.getContractAddress());
      System.out.println("EnergyToken deployato in: " + energyToken.getContractAddress());
      saveContractAddress("./src/main/resources/contracts/energyToken_address.txt", energyToken.getContractAddress());
    }

    if (!energyMarketAddress.isEmpty()) {
      System.out.println("Contratto già deployato a: " + energyMarketAddress);
      //EnergyMarket energyMarket = EnergyMarket.load(energyMarketAddress, web3j, credentials, new DefaultGasProvider());
      energyMarketService.setContractAddress(energyMarketAddress);
    } else {
      EnergyMarket energyMarket = EnergyMarket.deploy(web3j, credentials, new DefaultGasProvider(), energyTokenService.getContractAddress()).send();
      energyMarketService.setContractAddress(energyMarket.getContractAddress());
      System.out.println("EnergyMarket deployato in: " + energyMarket.getContractAddress());
      saveContractAddress("./src/main/resources/contracts/energyMarket_address.txt", energyMarket.getContractAddress());
    }

  }

  private String getDeployedContractAddress(String path) {
    try {
        Path filePath = Paths.get(path);
        File file = filePath.toFile();

        // Se il file non esiste, lo creo e ritorno stringa vuota
        if (!file.exists()) {
            Files.createFile(filePath);
            return "";
        }

        // Se il file esiste ma è vuoto, ritorno stringa vuota
        if (file.length() == 0) {
            return "";
        }

        // Leggo solo la prima riga
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String firstLine = reader.readLine();
            return firstLine != null ? firstLine.trim() : "";
        }

    } catch (IOException e) {
        System.out.println("Errore: " + e);
        return "";
    }
}

  private void saveContractAddress(String path, String address) {
    try {
      Files.write(Paths.get(path), address.getBytes());
    } catch (IOException e) {
      System.out.println("Errore: " + e.toString());
    }
  }
}
