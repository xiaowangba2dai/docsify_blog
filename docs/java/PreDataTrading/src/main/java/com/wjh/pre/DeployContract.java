package com.wjh.pre;

import com.wjh.contract.PreDex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.Properties;

public class DeployContract {

    private static final Logger log = LoggerFactory.getLogger(DeployContract.class);

    public static void main(String[] args) throws Exception {
        // load the properties
        FileInputStream propertiesFIS = new FileInputStream("./properties");
        Properties properties = new Properties();
        properties.load(propertiesFIS);
        propertiesFIS.close();

        // We start by creating a new web3j instance to connect to remote nodes on the network.
        Web3j web3j = Web3j.build(new HttpService(properties.getProperty("httpService")));
        log.info("Connected to Etherum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

        // We then need to load our Ethereum wallet file
        Credentials dataOwnerCredentials = WalletUtils.loadCredentials(properties.getProperty("dataOwnerWalletPassword"), "./wallet/"+properties.getProperty("dataOwnerAddress")+".json");
//        Credentials dataBuyerCredentials = WalletUtils.loadCredentials(properties.getProperty("dataBuyerWalletPassword"), "./wallet/"+properties.getProperty("dataBuyerAddress")+".json");
        log.info("Credentials loaded");

        ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(10000000L)); // 20 Gwei, GasLimit 1000000

        // deploy small contract
        PreDex preDex = PreDex.deploy(web3j, dataOwnerCredentials, provider).send();
        String contractAddress = preDex.getContractAddress();
        log.info("Smart contract: " + contractAddress);
    }
}
