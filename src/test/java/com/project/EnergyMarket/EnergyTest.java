package com.project.EnergyMarket;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

@SpringBootTest
public class EnergyTest {

    @Autowired
    private Web3j web3j;

    @Autowired
    private EnergyMarketService energyMarketService;

    @Autowired
    private EnergyTokenService energyTokenService;

    private Credentials credentials = Credentials
            .create("0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80");

    private String producerAddress = "0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266";
    private String consumerAddress = "0x70997970C51812dc3A010C7d01b50e0d17dc79C8";

    @BeforeEach
    public void setup() throws Exception {

        // Esegui il deploy del contratto EnergyToken
        EnergyToken energyToken = EnergyToken.deploy(web3j, credentials, new DefaultGasProvider()).send();
        energyTokenService.setContractAddress(energyToken.getContractAddress());

        EnergyMarket energyMarket = EnergyMarket
                .deploy(web3j, credentials, new DefaultGasProvider(), energyTokenService.getContractAddress()).send();
        energyMarketService.setContractAddress(energyMarket.getContractAddress());

        // registrazione degli indirizzi per la produzione
        energyMarketService.registerUser(producerAddress,BigInteger.valueOf(1));
        energyMarketService.registerUser(consumerAddress,BigInteger.valueOf(1));
    }

    // Testa la produzione di energia
    @Test
    public void testProduceEnergy() throws Exception {
        TransactionReceipt receipt = energyMarketService.produceEnergy(producerAddress, new BigInteger("100"));
        assertNotNull(receipt, "Il TransactionReceipt non deve essere null");
    }

    // Testa che dopo aver prodotto energia, il balance del produttore venga
    // aggiornato correttamente
    @Test
    public void testProducerBalanceUpdated() throws Exception {
        // recupero balance iniziale
        BigInteger initialBalance = energyTokenService.balanceOf(producerAddress);
        System.out.println("Balance iniziale = " + initialBalance);
        assertNotNull(initialBalance, "Il balance iniziale non deve essere null");

        // produzione di energia
        BigInteger producedAmount = new BigInteger("200");
        energyMarketService.produceEnergy(producerAddress, producedAmount);

        // recupero nuovo balance
        BigInteger newBalance = energyTokenService.balanceOf(producerAddress);
        assertNotNull(newBalance, "Il nuovo balance non deve essere nullo");

        // verifica
        assertEquals(initialBalance.add(producedAmount), newBalance,
                "Il balance del produttore non è stato aggiornato correttamente");
    }

    // Test sulla coerenza dei balance di produttore e consumatore dopo la vendita
    // di energia
    @Test
    public void testSellEnergyBalancesUpdated() throws Exception {

        // produzione
        BigInteger producedAmount = new BigInteger("200");
        energyMarketService.produceEnergy(producerAddress, producedAmount);

        // acquisto dell'energia prodotta
        BigInteger saleAmount = new BigInteger("50");
        energyMarketService.buyEnergy(consumerAddress, producerAddress, saleAmount);

        // valori attesi
        BigInteger expectedProducerBalance = producedAmount.subtract(saleAmount);
        BigInteger expectedConsumerBalance = saleAmount;

        // valori reali
        BigInteger actualProducerBalance = energyTokenService.balanceOf(producerAddress);
        BigInteger actualConsumerBalance = energyTokenService.balanceOf(consumerAddress);

        assertEquals(expectedProducerBalance, actualProducerBalance,
                "Il balance del produttore non è aggiornato correttamente dopo la vendita");
        assertEquals(expectedConsumerBalance, actualConsumerBalance,
                "Il balance del consumatore non è aggiornato correttamente dopo la vendita");
    }

    // Testa la coerenza del balance dopo una sequenza di produzione,acquisto e consumo
    @Test
public void testSequentialOperations() throws Exception {

    BigInteger producedAmount = new BigInteger("200");
    BigInteger saleAmount = new BigInteger("80");
    BigInteger burnAmount = new BigInteger("50");

    //  produzione di energia
    energyMarketService.produceEnergy(producerAddress, producedAmount);
    
    // acquisto di energia
    energyMarketService.buyEnergy(consumerAddress,producerAddress, saleAmount);
    
    // Il consumatore consuma parte dell'energia acquistata
    energyMarketService.consumeEnergy(consumerAddress, burnAmount);

    // verifica del balance finale del consumatore
    BigInteger expectedConsumerBalance = saleAmount.subtract(burnAmount);
    BigInteger actualConsumerBalance = energyTokenService.balanceOf(consumerAddress);
    assertEquals(expectedConsumerBalance, actualConsumerBalance,
        "Il balance del consumatore non è corretto dopo operazioni sequenziali");
}
    /*
     * Testa che venga lanciata un'eccezione se un consumatore prova a comprare più
     * energia di quella posseduta dal produttore.
     */
    @Test
    public void testBuyMoreThanProducerHasThrowsException() throws Exception {
        // produzione
        BigInteger producedAmount = new BigInteger("200");
        energyMarketService.produceEnergy(producerAddress, producedAmount);

        // acquisto e verifica
        BigInteger excessiveAmount = new BigInteger("250");
        assertThrows(Exception.class, () -> {
            energyMarketService.buyEnergy(consumerAddress,producerAddress, excessiveAmount);
        });
    }

    /**
     * Testa che venga lanciata un'eccezione se un utente prova a consumare più
     * energia di quella posseduta.
     */
    @Test
    public void testBurnMoreThanOwnedThrowsException() throws Exception {
        // impostazione di una certa quantità di energia iniziale
        BigInteger initialProduced = new BigInteger("30");
        energyTokenService.mint(consumerAddress, initialProduced);

        // consumo e verifica
        BigInteger burnAmount = new BigInteger("50");
        assertThrows(Exception.class, () -> {
            energyMarketService.consumeEnergy(consumerAddress, burnAmount);
        });
    }

    @Test
    public void testProduceWithoutRegistrationThrowsException() {
        // Indirizzo non registrato
        String unregisteredProducer = "0x14dC79964da2C08b23698B3D3cc7Ca32193d9955 ";
        BigInteger energyAmount = BigInteger.valueOf(100);

        //verifica
        assertThrows(Exception.class, () -> {
            energyMarketService.produceEnergy(unregisteredProducer, energyAmount);
        });
    }
}
