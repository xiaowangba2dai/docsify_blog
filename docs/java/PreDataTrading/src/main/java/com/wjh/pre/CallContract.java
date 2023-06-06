package com.wjh.pre;

import com.wjh.contract.PreDex;
import it.unisa.dia.gas.jpbc.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class CallContract {

    private static final Logger log = LoggerFactory.getLogger(CallContract.class);

    public static void main(String[] args) throws Exception {

        // load the properties
        FileInputStream propertiesFIS = new FileInputStream("./properties");
        Properties properties = new Properties();
        properties.load(propertiesFIS);
        propertiesFIS.close();

        // the count of aes key
        int keyCount = Integer.parseInt(properties.getProperty("keyCount"));
        String HASH_OF_ENCRYPTED_DATA = properties.getProperty("hashC");
        BigInteger PRICE_OF_DATA = new BigInteger(properties.getProperty("price"));
        BigInteger RANDOMINDEX = new BigInteger(properties.getProperty("random"));
        BigInteger PP = new BigInteger(properties.getProperty("q")); // from a.properties

        // We start by creating a new web3j instance to connect to remote nodes on the network.
        Web3j web3j = Web3j.build(new HttpService(properties.getProperty("httpService")));
        log.info("Connected to Etherum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

        // We then need to load our Ethereum wallet file
        Credentials dataOwnerCredentials = WalletUtils.loadCredentials(properties.getProperty("dataOwnerWalletPassword"), "./wallet/"+properties.getProperty("dataOwnerAddress")+".json");
        Credentials dataBuyerCredentials = WalletUtils.loadCredentials(properties.getProperty("dataBuyerWalletPassword"), "./wallet/"+properties.getProperty("dataBuyerAddress")+".json");
        log.info("Credentials loaded");

        ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(10000000L)); // 20 Gwei, GasLimit 1000000
        // deploy small contract
        PreDex preDex = PreDex.deploy(web3j, dataOwnerCredentials, provider).send();
        String contractAddress = preDex.getContractAddress();
        log.info("Smart contract: " + contractAddress);
//        String contractAddress = properties.getProperty("0xd04ac6b43225ea70c111e0f9873b9b19d734cdf5");

        PreDex dataOwner = PreDex.load(contractAddress,web3j,dataOwnerCredentials,provider);
        PreDex dataBuyer = PreDex.load(contractAddress,web3j,dataBuyerCredentials,provider);

        // ---------------------------------------------------------------------------------------------------------

        DataSeller seller = new DataSeller();
        DataBuyer buyer = new DataBuyer();

        // 1. Generate n sample aes key, and store to a file
        byte[][] K = seller.genAESKeys(keyCount);
        log.info("================== Data owner encrypted the data ================== ");
        // we ignore the encrypt data process just set hash of encrypte data and price of data on the blockchain
        BigInteger gas;
        gas = dataOwner.setHashAndPrice(HASH_OF_ENCRYPTED_DATA, PRICE_OF_DATA, PP).send().getGasUsed();
        log.info("(1) Data owner set the HashC、Price、PP, gas used: " + gas);

        // 2. Generate pk and sk
        log.info("Key of seller gens..");
        Map<String, Element> keyS = seller.keyGen();
        log.info("Key of buyer gens..");
        Map<String, Element> keyB = buyer.keyGen();

        // Data buyer request the data, publish his pk2
        String[] keyBuyerPk1 = keyB.get("pk1").toString().split(",");
        String[] keyBuyerPk2 = keyB.get("pk2").toString().split(",");
        gas = dataBuyer.request(new BigInteger(keyBuyerPk1[0]), new BigInteger(keyBuyerPk1[1]),new BigInteger(keyBuyerPk2[0]), new BigInteger(keyBuyerPk2[1])).send().getGasUsed();
        log.info("(2) Data buyer request the data and published the pk, gas used: " + gas);

        log.info("================== Data owner send the encrypted data to buyer ================== ");
        gas = dataBuyer.verifyHashC().send().getGasUsed();
        log.info("(3) Data buyer verify the hashC, gas used: " + gas);

        // 3. encrypt n sample aes key with seller public key
        log.info("Data owner encrypt k..");
        Map<String, Object>[] EK = new Map[keyCount];
        for (int i=0; i<keyCount; i++) {
            EK[i] = seller.encrypt(keyS.get("pk1"), keyS.get("pk2"), K[i]);
        }

        // 4. re-encrypt key gen
        log.info("Data owner generates rk..");
        Map<String, Object> rk = seller.reKeyGen(keyS.get("sk1"),keyS.get("sk2"),keyS.get("pk2"),keyB.get("pk2"));

        // 5. re-encrypt
        log.info("Data owner re-encrypt the ek..");
        Map<String, Object>[] REK = new Map[keyCount];
        for (int i=0; i<keyCount; i++) {
            REK[i] = seller.reEncrypt(rk, EK[i], keyS.get("pk1"), keyS.get("pk2"), keyB.get("pk1"), keyB.get("pk2"));
        }

        log.info("================== Data owner send the sample rek to data buyer ================== ");

        // 6. buyer get rek and decrypt with his own sk
        log.info(("buyer get REK and decrypt with his own sk"));
        for (int i=0; i<keyCount; i++) {
            byte[] decryptEk = buyer.reDecrypt(REK[i],keyB.get("sk1"),keyB.get("sk2"), keyB.get("pk1"), keyB.get("pk2"));
            if (!Arrays.equals(K[i],decryptEk)){
                System.out.println("error key");
            }
        }

        String[] ekRandom = EK[RANDOMINDEX.intValue()].get("E").toString().split(",");
        String[] rekRandom = REK[RANDOMINDEX.intValue()].get("E_1").toString().split(",");


        Function function = new Function("payAndSetHashEkRek",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(new BigInteger(ekRandom[0])),
                        new org.web3j.abi.datatypes.generated.Uint256(new BigInteger(ekRandom[1])),
                        new org.web3j.abi.datatypes.generated.Uint256(new BigInteger(rekRandom[0])),
                        new org.web3j.abi.datatypes.generated.Uint256(new BigInteger(rekRandom[1])),
                        new org.web3j.abi.datatypes.generated.Uint256(RANDOMINDEX)),
                Collections.<TypeReference<?>>emptyList()
        );


        String encodedConfirmFunction = FunctionEncoder.encode(function);

        EthGetTransactionCount ethGetConfirmTransactionCount = web3j.ethGetTransactionCount(
                dataBuyerCredentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger confirmNonce = ethGetConfirmTransactionCount.getTransactionCount();

        RawTransaction rawConfirmTransaction = RawTransaction.createTransaction(confirmNonce, BigInteger.valueOf(20000000000L), BigInteger.valueOf(10000000L), contractAddress, PRICE_OF_DATA, encodedConfirmFunction);
        byte[] signedConfirmMessage = TransactionEncoder.signMessage(rawConfirmTransaction, dataBuyerCredentials);
        String hexConfirmValue = Numeric.toHexString(signedConfirmMessage);
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexConfirmValue).sendAsync().get();
        String transactionHash = ethSendTransaction.getTransactionHash();
        EthGetTransactionReceipt transactionReceipt;

        while (true) {
            transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
            if (transactionReceipt.getResult() != null) {
                break;
            }
            Thread.sleep(15000);
        }
        log.info("(4) Data buyer paid and set ek and rek, gas used: " + transactionReceipt.getResult().getGasUsed());

        Element rk_i_j_1 = (Element) rk.get("rk_i_j_1");
        gas = dataOwner.publishRk(rk_i_j_1.toBigInteger()).send().getGasUsed();
        log.info("(5) Data seller publish rk, gas used: " + gas);

        gas = dataBuyer.arbitrate().send().getGasUsed();
        log.info("(6) Data buyer arbitrate, gas used: " + gas);
    }
}
