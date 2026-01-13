// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./";

contract EnergyToken{
    
    string public name = "EnergyToken";
    string public symbol = "KWh";
    uint256 public totalSupply;
    
    mapping(address => uint256) public balanceOf;
    event Transfer(address indexed from, address indexed to, uint256 value);

    function transferFrom(address from, address to, uint256 value) public returns (bool) {
         require(to != address(0), "Trasferimento a indirizzo zero non consentito");
         require(balanceOf[from] >= value, "Saldo insufficiente");
         
         balanceOf[from] -= value;
         balanceOf[to] += value;
         
         emit Transfer(from, to, value);
         return true;
    }
    
    function mint(address to, uint256 value) public returns (bool) {
         totalSupply += value;
         balanceOf[to] += value;
         emit Transfer(address(0), to, value);
         return true;
    }

    function burn(address from, uint256 value) public returns (bool) {
         require(balanceOf[from] >= value, "Saldo insufficiente per il burn");
         balanceOf[from] -= value;
         totalSupply -= value;
         emit Transfer(msg.sender, address(0), value);
         return true;
    }
}
