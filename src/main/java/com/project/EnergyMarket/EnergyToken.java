package com.project.EnergyMarket;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.2.
 */
@SuppressWarnings("rawtypes")
public class EnergyToken extends Contract {
    public static final String BINARY = "60806040526040518060400160405280600b81526020017f456e65726779546f6b656e0000000000000000000000000000000000000000008152505f908161004791906102d8565b506040518060400160405280600381526020017f4b576800000000000000000000000000000000000000000000000000000000008152506001908161008c91906102d8565b50348015610098575f80fd5b506103a7565b5f81519050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f600282049050600182168061011957607f821691505b60208210810361012c5761012b6100d5565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f6008830261018e7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610153565b6101988683610153565b95508019841693508086168417925050509392505050565b5f819050919050565b5f819050919050565b5f6101dc6101d76101d2846101b0565b6101b9565b6101b0565b9050919050565b5f819050919050565b6101f5836101c2565b610209610201826101e3565b84845461015f565b825550505050565b5f90565b61021d610211565b6102288184846101ec565b505050565b5b8181101561024b576102405f82610215565b60018101905061022e565b5050565b601f8211156102905761026181610132565b61026a84610144565b81016020851015610279578190505b61028d61028585610144565b83018261022d565b50505b505050565b5f82821c905092915050565b5f6102b05f1984600802610295565b1980831691505092915050565b5f6102c883836102a1565b9150826002028217905092915050565b6102e18261009e565b67ffffffffffffffff8111156102fa576102f96100a8565b5b6103048254610102565b61030f82828561024f565b5f60209050601f831160018114610340575f841561032e578287015190505b61033885826102bd565b86555061039f565b601f19841661034e86610132565b5f5b8281101561037557848901518255600182019150602085019450602081019050610350565b86831015610392578489015161038e601f8916826102a1565b8355505b6001600288020188555050505b505050505050565b610bbf806103b45f395ff3fe608060405234801561000f575f80fd5b506004361061007b575f3560e01c806340c10f191161005957806340c10f19146100eb57806370a082311461011b57806395d89b411461014b5780639dc29fac146101695761007b565b806306fdde031461007f57806318160ddd1461009d57806323b872dd146100bb575b5f80fd5b610087610199565b6040516100949190610776565b60405180910390f35b6100a5610224565b6040516100b291906107ae565b60405180910390f35b6100d560048036038101906100d0919061084f565b61022a565b6040516100e291906108b9565b60405180910390f35b610105600480360381019061010091906108d2565b61042f565b60405161011291906108b9565b60405180910390f35b61013560048036038101906101309190610910565b61050a565b60405161014291906107ae565b60405180910390f35b61015361051f565b6040516101609190610776565b60405180910390f35b610183600480360381019061017e91906108d2565b6105ab565b60405161019091906108b9565b60405180910390f35b5f80546101a590610968565b80601f01602080910402602001604051908101604052809291908181526020018280546101d190610968565b801561021c5780601f106101f35761010080835404028352916020019161021c565b820191905f5260205f20905b8154815290600101906020018083116101ff57829003601f168201915b505050505081565b60025481565b5f8073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1603610299576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161029090610a08565b60405180910390fd5b8160035f8673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f20541015610319576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161031090610a70565b60405180910390fd5b8160035f8673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546103659190610abb565b925050819055508160035f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546103b89190610aee565b925050819055508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef8460405161041c91906107ae565b60405180910390a3600190509392505050565b5f8160025f8282546104419190610aee565b925050819055508160035f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546104949190610aee565b925050819055508273ffffffffffffffffffffffffffffffffffffffff165f73ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040516104f891906107ae565b60405180910390a36001905092915050565b6003602052805f5260405f205f915090505481565b6001805461052c90610968565b80601f016020809104026020016040519081016040528092919081815260200182805461055890610968565b80156105a35780601f1061057a576101008083540402835291602001916105a3565b820191905f5260205f20905b81548152906001019060200180831161058657829003601f168201915b505050505081565b5f8160035f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2054101561062c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161062390610b6b565b60405180910390fd5b8160035f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546106789190610abb565b925050819055508160025f8282546106909190610abb565b925050819055505f73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040516106f491906107ae565b60405180910390a36001905092915050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f601f19601f8301169050919050565b5f61074882610706565b6107528185610710565b9350610762818560208601610720565b61076b8161072e565b840191505092915050565b5f6020820190508181035f83015261078e818461073e565b905092915050565b5f819050919050565b6107a881610796565b82525050565b5f6020820190506107c15f83018461079f565b92915050565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6107f4826107cb565b9050919050565b610804816107ea565b811461080e575f80fd5b50565b5f8135905061081f816107fb565b92915050565b61082e81610796565b8114610838575f80fd5b50565b5f8135905061084981610825565b92915050565b5f805f60608486031215610866576108656107c7565b5b5f61087386828701610811565b935050602061088486828701610811565b92505060406108958682870161083b565b9150509250925092565b5f8115159050919050565b6108b38161089f565b82525050565b5f6020820190506108cc5f8301846108aa565b92915050565b5f80604083850312156108e8576108e76107c7565b5b5f6108f585828601610811565b92505060206109068582860161083b565b9150509250929050565b5f60208284031215610925576109246107c7565b5b5f61093284828501610811565b91505092915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f600282049050600182168061097f57607f821691505b6020821081036109925761099161093b565b5b50919050565b7f54726173666572696d656e746f206120696e646972697a7a6f207a65726f206e5f8201527f6f6e20636f6e73656e7469746f00000000000000000000000000000000000000602082015250565b5f6109f2602d83610710565b91506109fd82610998565b604082019050919050565b5f6020820190508181035f830152610a1f816109e6565b9050919050565b7f53616c646f20696e73756666696369656e7465000000000000000000000000005f82015250565b5f610a5a601383610710565b9150610a6582610a26565b602082019050919050565b5f6020820190508181035f830152610a8781610a4e565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f610ac582610796565b9150610ad083610796565b9250828203905081811115610ae857610ae7610a8e565b5b92915050565b5f610af882610796565b9150610b0383610796565b9250828201905080821115610b1b57610b1a610a8e565b5b92915050565b7f53616c646f20696e73756666696369656e74652070657220696c206275726e005f82015250565b5f610b55601f83610710565b9150610b6082610b21565b602082019050919050565b5f6020820190508181035f830152610b8281610b49565b905091905056fea2646970667358221220a270bc20bdc221e9848b9ce94be185f49be195c42c2c05399a83203f3cae6c8564736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURN = "burn";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected EnergyToken(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EnergyToken(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EnergyToken(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EnergyToken(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<TransferEventResponse> getTransferEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String param0) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> burn(String from, BigInteger value) {
        final Function function = new Function(
                FUNC_BURN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String to, BigInteger value) {
        final Function function = new Function(
                FUNC_MINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to,
            BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EnergyToken load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new EnergyToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EnergyToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EnergyToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EnergyToken load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new EnergyToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EnergyToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EnergyToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EnergyToken> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EnergyToken.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<EnergyToken> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EnergyToken.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<EnergyToken> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EnergyToken.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<EnergyToken> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EnergyToken.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }
}
