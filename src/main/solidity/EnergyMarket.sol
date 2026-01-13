// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;
import "./IEnergyToken.sol";

contract EnergyMarket {
    IEnergyToken token;

    struct User {
        uint pricePerToken; // Prezzo in wei per token (KWh)
        bool exists;
    }
    
    mapping(address => User) users;
    address[] usersList;
    

    event UserRegistered(address user, uint pricePerToken);
    event EnergyProduced(address producer, uint amount);
    event EnergyPurchased(
        address consumer,
        address producer,
        uint amount,
        uint totalPrice
    );
    event EnergyConsumed(address consumer, uint amount);

    constructor(address tokenAddress) {
        token = IEnergyToken(tokenAddress);
    }

    // registrazione di un utente con prezzo di vendita iniziale
    function registerUser(uint pricePerToken) external  {
        if (users[msg.sender].exists) {
            return;
        }
        users[msg.sender] = User(pricePerToken, true);
        usersList.push(msg.sender);
        emit UserRegistered(msg.sender, pricePerToken);
    } 
    
    // registrazione di un utente con prezzo di vendita di default
    function registerUser() external  {
        if (users[msg.sender].exists) {
            return;
        }
        users[msg.sender] = User(1, true);
        usersList.push(msg.sender);
        emit UserRegistered(msg.sender, 1);
    }

    // restituisce il prezzo di un determinato produttore
    function getUserPrice(address producer) external view returns (uint) {
        return users[producer].pricePerToken;
    }

    // l'utente imposta un proprio prezzo di vendita
    function setUserPrice(uint newPricePerToken) external {
        require(users[msg.sender].exists,"L'utente specificato non e registrato");
        users[msg.sender].pricePerToken = newPricePerToken;
    }

    // Il produttore genera nuovi token di energia
    function produceEnergy(uint amount) external {
        require(users[msg.sender].exists, "L'utente non e registrato");
        bool success = token.mint(msg.sender, amount);
        require(success, "Produzione di energia fallita");

        emit EnergyProduced(msg.sender, amount);
    }

    function buyEnergy(address producer, uint amount) external payable {
        require(users[msg.sender].exists, "L'utente non e registrato");
        uint totalPrice = users[producer].pricePerToken * amount;
        // uint totalPrice = amount;
        require(msg.value >= totalPrice, "Fondi ETH insufficienti");
        require(
            token.balanceOf(producer) >= amount,
            "Token insufficienti dal produttore"
        );

        // Trasferisce i token dal produttore al consumatore
        bool success = token.transferFrom(producer, msg.sender, amount);
        require(success, "Trasferimento token fallito");

        // Inoltra il pagamento ETH al produttore
        payable(producer).transfer(totalPrice);

        if (msg.value > totalPrice) {
            payable(msg.sender).transfer(msg.value - totalPrice);
        }

        emit EnergyPurchased(msg.sender, producer, amount, totalPrice);
    }

    function consumeEnergy(uint amount) external {
        require(users[msg.sender].exists, "L'utente non e registrato");
        require(
            token.balanceOf(msg.sender) >= amount,
            "Energia insufficiente per il consumo"
        );
        bool success = token.burn(msg.sender, amount);
        require(success, "Consumo di energia fallito");
        emit EnergyConsumed(msg.sender, amount);
    }
}
