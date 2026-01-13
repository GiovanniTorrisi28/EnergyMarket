// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;


interface IEnergyToken {
    function transferFrom(address from, address to, uint256 value) external returns (bool);
    function balanceOf(address account) external view returns (uint256);
    function mint(address to, uint256 value) external returns (bool);
    function burn(address from, uint256 value) external returns (bool);
    event Transfer(address indexed from, address indexed to, uint256 value);

}