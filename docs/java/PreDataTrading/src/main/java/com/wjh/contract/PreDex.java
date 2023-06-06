package com.wjh.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
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
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class PreDex extends Contract {
    public static final String BINARY = "6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600e60006101000a81548160ff0219169083600681111561006357fe5b0217905550611b70806100776000396000f3fe6080604052600436106101a05760003560e01c80637e293f2b116100ec578063c5ca71cb1161008a578063dc2337e211610064578063dc2337e2146106f6578063e53655f514610721578063eeeac01e1461074c578063f7f8556014610777576101a7565b8063c5ca71cb14610689578063d2010c08146106b4578063d48fa9d3146106cb576101a7565b80639a44abdb116100c65780639a44abdb14610491578063b52664221461056d578063b7c931e8146105fd578063c19d93fb14610653576101a7565b80637e293f2b146104105780637f0157451461043b578063997da8d414610466576101a7565b806340efdc5d116101595780634e1f1e03116101335780634e1f1e031461034e5780635727dc5c1461037957806370dea79a146103a45780637150d8ae146103cf576101a7565b806340efdc5d14610302578063472b370d1461032d5780634bd7d08114610344576101a7565b806308551a53146101ac5780630c55699c146101ed57806329fe9430146102185780632ab732ab146102535780632fb400ed1461027e5780633b9b573f146102d7576101a7565b366101a757005b600080fd5b3480156101b857600080fd5b506101c16107a2565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101f957600080fd5b506102026107c6565b6040518082815260200191505060405180910390f35b34801561022457600080fd5b506102516004803603602081101561023b57600080fd5b81019080803590602001909291905050506107cc565b005b34801561025f57600080fd5b506102686108d0565b6040518082815260200191505060405180910390f35b34801561028a57600080fd5b506102d5600480360360808110156102a157600080fd5b81019080803590602001909291908035906020019092919080359060200190929190803590602001909291905050506108d6565b005b3480156102e357600080fd5b506102ec610a1f565b6040518082815260200191505060405180910390f35b34801561030e57600080fd5b50610317610a25565b6040518082815260200191505060405180910390f35b34801561033957600080fd5b50610342610a2b565b005b61034c610c5e565b005b34801561035a57600080fd5b50610363610db9565b6040518082815260200191505060405180910390f35b34801561038557600080fd5b5061038e610dbf565b6040518082815260200191505060405180910390f35b3480156103b057600080fd5b506103b9610dc4565b6040518082815260200191505060405180910390f35b3480156103db57600080fd5b506103e4610dca565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561041c57600080fd5b50610425610df0565b6040518082815260200191505060405180910390f35b34801561044757600080fd5b50610450610df6565b6040518082815260200191505060405180910390f35b34801561047257600080fd5b5061047b610dfc565b6040518082815260200191505060405180910390f35b34801561049d57600080fd5b5061056b600480360360608110156104b457600080fd5b81019080803590602001906401000000008111156104d157600080fd5b8201836020820111156104e357600080fd5b8035906020019184600183028401116401000000008311171561050557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019092919080359060200190929190505050610e01565b005b34801561057957600080fd5b50610582610f16565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156105c25780820151818401526020810190506105a7565b50505050905090810190601f1680156105ef5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610651600480360360a081101561061357600080fd5b810190808035906020019092919080359060200190929190803590602001909291908035906020019092919080359060200190929190505050610fb4565b005b34801561065f57600080fd5b5061066861112f565b6040518082600681111561067857fe5b815260200191505060405180910390f35b34801561069557600080fd5b5061069e611142565b6040518082815260200191505060405180910390f35b3480156106c057600080fd5b506106c9611148565b005b3480156106d757600080fd5b506106e0611246565b6040518082815260200191505060405180910390f35b34801561070257600080fd5b5061070b61124c565b6040518082815260200191505060405180910390f35b34801561072d57600080fd5b50610736611252565b6040518082815260200191505060405180910390f35b34801561075857600080fd5b50610761611258565b6040518082815260200191505060405180910390f35b34801561078357600080fd5b5061078c61125e565b6040518082815260200191505060405180910390f35b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60035481565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff166004600f544211158273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461082d57600080fd5b81600681111561083957fe5b600e60009054906101000a900460ff16600681111561085457fe5b1461085e57600080fd5b8061086857600080fd5b6005600e60006101000a81548160ff0219169083600681111561088757fe5b02179055506102584201600f8190555083600d819055507f3017ee1af445a9b419593c65c01067182e22ae77c9887313aa806851f937821860405160405180910390a150505050565b600a5481565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141561092f57600080fd5b6001600681111561093c57fe5b600e60009054906101000a900460ff16600681111561095757fe5b1461096157600080fd5b6002600e60006101000a81548160ff0219169083600681111561098057fe5b021790555033600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550836004819055508260058190555081600681905550806007819055506102584201600f819055507f044748436b71cad59b7a70816324bfd61ff49bfbbc648e6d4ea9e57adda6c4f260405160405180910390a150505050565b60075481565b60105481565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166005600f544211158273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610a8e57600080fd5b816006811115610a9a57fe5b600e60009054906101000a900460ff166006811115610ab557fe5b14610abf57600080fd5b80610ac957600080fd5b600080610ae2600d546008546009546001601154611264565b80925081935050506006600e60006101000a81548160ff02191690836006811115610b0957fe5b0217905550600a5482148015610b205750600b5481145b15610bbf5760008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6003549081150290604051600060405180830381858888f19350505050158015610b8d573d6000803e3d6000fd5b507fc7f3adddf8a8e7baac92905bc7bc548aa9737df6705afa002bdba58a21532cda60405160405180910390a1610c57565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6003549081150290604051600060405180830381858888f19350505050158015610c29573d6000803e3d6000fd5b507fd4ef6cb012f0b9f008d79f7fc40c7bc1de0020918d6053efd5da214dcaf292df60405160405180910390a15b5050505050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166004600f5442118273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610cc057600080fd5b816006811115610ccc57fe5b600e60009054906101000a900460ff166006811115610ce757fe5b14610cf157600080fd5b80610cfb57600080fd5b7fd4ef6cb012f0b9f008d79f7fc40c7bc1de0020918d6053efd5da214dcaf292df60405160405180910390a16006600e60006101000a81548160ff02191690836006811115610d4657fe5b0217905550600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f19350505050158015610db3573d6000803e3d6000fd5b50505050565b60055481565b600081565b600f5481565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600b5481565b60065481565b600181565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600060018273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610e5e57600080fd5b816006811115610e6a57fe5b600e60009054906101000a900460ff166006811115610e8557fe5b14610e8f57600080fd5b80610e9957600080fd5b6001600e60006101000a81548160ff02191690836006811115610eb857fe5b02179055508560029080519060200190610ed3929190611a7b565b5084600381905550836011819055507f5648efa0b38a3bca83806f08aba235d2173d8f244fe1effcd39ed3b477cb59e660405160405180910390a1505050505050565b60028054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610fac5780601f10610f8157610100808354040283529160200191610fac565b820191906000526020600020905b815481529060010190602001808311610f8f57829003601f168201915b505050505081565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166003600f544211158273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461101757600080fd5b81600681111561102357fe5b600e60009054906101000a900460ff16600681111561103e57fe5b1461104857600080fd5b8061105257600080fd5b600354341461106057600080fd5b6004600e60006101000a81548160ff0219169083600681111561107f57fe5b02179055506102584201600f81905550876008819055508660098190555085600a8190555084600b8190555083600c819055503073ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f193505050501580156110f8573d6000803e3d6000fd5b507fed76f396dc6bc9025d743aaa4b36622dc8d4356e24e60f771116369acf14a77e60405160405180910390a15050505050505050565b600e60009054906101000a900460ff1681565b60095481565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166002600f544211158273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146111ab57600080fd5b8160068111156111b757fe5b600e60009054906101000a900460ff1660068111156111d257fe5b146111dc57600080fd5b806111e657600080fd5b6003600e60006101000a81548160ff0219169083600681111561120557fe5b02179055506102584201600f819055507f54f15a42217a7aac48a6bd7e3c72bd3326846f835c73fe6188fa8b67ebfc68df60405160405180910390a1505050565b60045481565b600c5481565b60085481565b60115481565b600d5481565b600080600080600061127b8a8a8a60018b8b61129e565b92509250925061128d8383838961133c565b945094505050509550959350505050565b6000806000808914156112b957878787925092509250611330565b60008990506000806000600190505b6000841461132257600060018516146112f7576112ea8383838f8f8f8e611397565b8093508194508295505050505b6002848161130157fe5b0493506113118c8c8c8c8c611868565b809c50819d50829e505050506112c8565b828282965096509650505050505b96509650969350505050565b600080600061134b8585611987565b90506000848061135757fe5b82830990506000858061136657fe5b828a0990506000868061137557fe5b878061137d57fe5b8486098a0990508181955095505050505094509492505050565b6000806000808a1480156113ab5750600089145b156113be5786868692509250925061185b565b6000871480156113ce5750600086145b156113e15789898992509250925061185b565b6113e9611afb565b84806113f157fe5b898a098160006004811061140157fe5b602002018181525050848061141257fe5b8160006004811061141f57fe5b60200201518a098160016004811061143357fe5b602002018181525050848061144457fe5b8687098160026004811061145457fe5b602002018181525050848061146557fe5b8160026004811061147257fe5b602002015187098160036004811061148657fe5b602002018181525050604051806080016040528086806114a257fe5b836002600481106114af57fe5b60200201518e09815260200186806114c357fe5b836003600481106114d057fe5b60200201518d09815260200186806114e457fe5b836000600481106114f157fe5b60200201518b098152602001868061150557fe5b8360016004811061151257fe5b60200201518a0981525090508060026004811061152b57fe5b60200201518160006004811061153d57fe5b602002015114158061157157508060036004811061155757fe5b60200201518160016004811061156957fe5b602002015114155b6115e3576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f557365206a6163446f75626c652066756e6374696f6e20696e7374656164000081525060200191505060405180910390fd5b6115eb611afb565b85806115f357fe5b8260006004811061160057fe5b602002015187038360026004811061161457fe5b6020020151088160006004811061162757fe5b602002018181525050858061163857fe5b8260016004811061164557fe5b602002015187038360036004811061165957fe5b6020020151088160016004811061166c57fe5b602002018181525050858061167d57fe5b8160006004811061168a57fe5b60200201518260006004811061169c57fe5b602002015109816002600481106116af57fe5b60200201818152505085806116c057fe5b816000600481106116cd57fe5b6020020151826002600481106116df57fe5b602002015109816003600481106116f257fe5b6020020181815250506000868061170557fe5b8260036004811061171257fe5b60200201518803888061172157fe5b8460016004811061172e57fe5b60200201518560016004811061174057fe5b602002015109089050868061175157fe5b878061175957fe5b888061176157fe5b8460026004811061176e57fe5b60200201518660006004811061178057fe5b6020020151096002098803820890506000878061179957fe5b88806117a157fe5b838a038a806117ac57fe5b866002600481106117b957fe5b6020020151886000600481106117cb57fe5b60200201510908846001600481106117df57fe5b602002015109905087806117ef57fe5b88806117f757fe5b8460036004811061180457fe5b60200201518660016004811061181657fe5b6020020151098903820890506000888061182c57fe5b898061183457fe5b8b8f098560006004811061184457fe5b602002015109905082828297509750975050505050505b9750975097945050505050565b6000806000808614156118835787878792509250925061197c565b6000848061188d57fe5b898a0990506000858061189c57fe5b898a099050600086806118ab57fe5b898a099050600087806118ba57fe5b88806118c257fe5b848e096004099050600088806118d457fe5b89806118dc57fe5b8a806118e457fe5b8586098c098a806118f157fe5b87600309089050888061190057fe5b898061190857fe5b8384088a038a8061191557fe5b838409089450888061192357fe5b898061192b57fe5b8a8061193357fe5b8687096008098a038a8061194357fe5b8b8061194b57fe5b888d0386088409089350888061195d57fe5b898061196557fe5b8c8e09600209925084848497509750975050505050505b955095509592505050565b60008083141580156119995750818314155b80156119a6575060008214155b611a18576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f496e76616c6964206e756d62657200000000000000000000000000000000000081525060200191505060405180910390fd5b60008060019050600084905060005b60008714611a6e57868281611a3857fe5b049050828680611a4457fe5b8780611a4c57fe5b8584098803860880945081955050508687820283038098508193505050611a27565b8394505050505092915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611abc57805160ff1916838001178555611aea565b82800160010185558215611aea579182015b82811115611ae9578251825591602001919060010190611ace565b5b509050611af79190611b1d565b5090565b6040518060800160405280600490602082028036833780820191505090505090565b5b80821115611b36576000816000905550600101611b1e565b509056fea2646970667358221220fcda46b2e9ca8632fff68c04bc112d017fb5b168c53794bd5401441d21d8cf6664736f6c634300060c0033";
;

    public static final String FUNC_AA = "AA";

    public static final String FUNC_BB = "BB";

    public static final String FUNC_PP = "PP";

    public static final String FUNC_ARBITRATE = "arbitrate";

    public static final String FUNC_BUYER = "buyer";

    public static final String FUNC_DEPTHOFMERKLETREE = "depthOfMerkleTree";

    public static final String FUNC_EKX = "ekX";

    public static final String FUNC_EKY = "ekY";

    public static final String FUNC_HASHC = "hashC";

    public static final String FUNC_PAYANDSETHASHEKREK = "payAndSetHashEkRek";

    public static final String FUNC_PK1BUYERX = "pk1BuyerX";

    public static final String FUNC_PK1BUYERY = "pk1BuyerY";

    public static final String FUNC_PK2BUYERX = "pk2BuyerX";

    public static final String FUNC_PK2BUYERY = "pk2BuyerY";

    public static final String FUNC_PUBLISHRK = "publishRk";

    public static final String FUNC_RANDOMINDEX = "randomIndex";

    public static final String FUNC_REFUNDTOBUYER = "refundToBuyer";

    public static final String FUNC_REKX = "rekX";

    public static final String FUNC_REKY = "rekY";

    public static final String FUNC_REQUEST = "request";

    public static final String FUNC_RK = "rk";

    public static final String FUNC_SELLER = "seller";

    public static final String FUNC_SETHASHANDPRICE = "setHashAndPrice";

    public static final String FUNC_STATE = "state";

    public static final String FUNC_TIMEOUT = "timeout";

    public static final String FUNC_VERIFYHASHC = "verifyHashC";

    public static final String FUNC_X = "x";

    public static final Event PAIDANDSETEKREK_EVENT = new Event("PaidAndSetEkRek", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event PUBLISHEDRK_EVENT = new Event("PublishedRk", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event REFUNDTOBUYER_EVENT = new Event("RefundToBuyer", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event REQUEST_EVENT = new Event("Request", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event SETHASHCANDPRICE_EVENT = new Event("SetHashCAndPrice", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event TRADINGCOMPLETED_EVENT = new Event("TradingCompleted", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event VERIFYHASHC_EVENT = new Event("VerifyHashC", 
            Arrays.<TypeReference<?>>asList());
    ;

    @Deprecated
    protected PreDex(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PreDex(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PreDex(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PreDex(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<PaidAndSetEkRekEventResponse> getPaidAndSetEkRekEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(PAIDANDSETEKREK_EVENT, transactionReceipt);
        ArrayList<PaidAndSetEkRekEventResponse> responses = new ArrayList<PaidAndSetEkRekEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            PaidAndSetEkRekEventResponse typedResponse = new PaidAndSetEkRekEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PaidAndSetEkRekEventResponse> paidAndSetEkRekEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PaidAndSetEkRekEventResponse>() {
            @Override
            public PaidAndSetEkRekEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(PAIDANDSETEKREK_EVENT, log);
                PaidAndSetEkRekEventResponse typedResponse = new PaidAndSetEkRekEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<PaidAndSetEkRekEventResponse> paidAndSetEkRekEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAIDANDSETEKREK_EVENT));
        return paidAndSetEkRekEventFlowable(filter);
    }

    public List<PublishedRkEventResponse> getPublishedRkEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(PUBLISHEDRK_EVENT, transactionReceipt);
        ArrayList<PublishedRkEventResponse> responses = new ArrayList<PublishedRkEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            PublishedRkEventResponse typedResponse = new PublishedRkEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PublishedRkEventResponse> publishedRkEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PublishedRkEventResponse>() {
            @Override
            public PublishedRkEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(PUBLISHEDRK_EVENT, log);
                PublishedRkEventResponse typedResponse = new PublishedRkEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<PublishedRkEventResponse> publishedRkEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUBLISHEDRK_EVENT));
        return publishedRkEventFlowable(filter);
    }

    public List<RefundToBuyerEventResponse> getRefundToBuyerEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REFUNDTOBUYER_EVENT, transactionReceipt);
        ArrayList<RefundToBuyerEventResponse> responses = new ArrayList<RefundToBuyerEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RefundToBuyerEventResponse typedResponse = new RefundToBuyerEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RefundToBuyerEventResponse> refundToBuyerEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RefundToBuyerEventResponse>() {
            @Override
            public RefundToBuyerEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REFUNDTOBUYER_EVENT, log);
                RefundToBuyerEventResponse typedResponse = new RefundToBuyerEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<RefundToBuyerEventResponse> refundToBuyerEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REFUNDTOBUYER_EVENT));
        return refundToBuyerEventFlowable(filter);
    }

    public List<RequestEventResponse> getRequestEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REQUEST_EVENT, transactionReceipt);
        ArrayList<RequestEventResponse> responses = new ArrayList<RequestEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RequestEventResponse typedResponse = new RequestEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RequestEventResponse> requestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RequestEventResponse>() {
            @Override
            public RequestEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REQUEST_EVENT, log);
                RequestEventResponse typedResponse = new RequestEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<RequestEventResponse> requestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REQUEST_EVENT));
        return requestEventFlowable(filter);
    }

    public List<SetHashCAndPriceEventResponse> getSetHashCAndPriceEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SETHASHCANDPRICE_EVENT, transactionReceipt);
        ArrayList<SetHashCAndPriceEventResponse> responses = new ArrayList<SetHashCAndPriceEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SetHashCAndPriceEventResponse typedResponse = new SetHashCAndPriceEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SetHashCAndPriceEventResponse> setHashCAndPriceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SetHashCAndPriceEventResponse>() {
            @Override
            public SetHashCAndPriceEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SETHASHCANDPRICE_EVENT, log);
                SetHashCAndPriceEventResponse typedResponse = new SetHashCAndPriceEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<SetHashCAndPriceEventResponse> setHashCAndPriceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SETHASHCANDPRICE_EVENT));
        return setHashCAndPriceEventFlowable(filter);
    }

    public List<TradingCompletedEventResponse> getTradingCompletedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRADINGCOMPLETED_EVENT, transactionReceipt);
        ArrayList<TradingCompletedEventResponse> responses = new ArrayList<TradingCompletedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TradingCompletedEventResponse typedResponse = new TradingCompletedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TradingCompletedEventResponse> tradingCompletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TradingCompletedEventResponse>() {
            @Override
            public TradingCompletedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRADINGCOMPLETED_EVENT, log);
                TradingCompletedEventResponse typedResponse = new TradingCompletedEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<TradingCompletedEventResponse> tradingCompletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRADINGCOMPLETED_EVENT));
        return tradingCompletedEventFlowable(filter);
    }

    public List<VerifyHashCEventResponse> getVerifyHashCEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(VERIFYHASHC_EVENT, transactionReceipt);
        ArrayList<VerifyHashCEventResponse> responses = new ArrayList<VerifyHashCEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            VerifyHashCEventResponse typedResponse = new VerifyHashCEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VerifyHashCEventResponse> verifyHashCEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, VerifyHashCEventResponse>() {
            @Override
            public VerifyHashCEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(VERIFYHASHC_EVENT, log);
                VerifyHashCEventResponse typedResponse = new VerifyHashCEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<VerifyHashCEventResponse> verifyHashCEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VERIFYHASHC_EVENT));
        return verifyHashCEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> AA() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_AA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> BB() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BB, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> PP() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> arbitrate() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ARBITRATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> buyer() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BUYER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> depthOfMerkleTree() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEPTHOFMERKLETREE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> ekX() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EKX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> ekY() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EKY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> hashC() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_HASHC, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> payAndSetHashEkRek(BigInteger _ekX, BigInteger _ekY, BigInteger _rekX, BigInteger _rekY, BigInteger _randomIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PAYANDSETHASHEKREK, 
                Arrays.<Type>asList(new Uint256(_ekX),
                new Uint256(_ekY),
                new Uint256(_rekX),
                new Uint256(_rekY),
                new Uint256(_randomIndex)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> pk1BuyerX() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PK1BUYERX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> pk1BuyerY() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PK1BUYERY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> pk2BuyerX() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PK2BUYERX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> pk2BuyerY() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PK2BUYERY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> publishRk(BigInteger _rk) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PUBLISHRK, 
                Arrays.<Type>asList(new Uint256(_rk)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> randomIndex() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RANDOMINDEX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> refundToBuyer() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REFUNDTOBUYER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> rekX() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REKX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> rekY() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REKY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> request(BigInteger pk1x, BigInteger pk1y, BigInteger pk2x, BigInteger pk2y) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUEST, 
                Arrays.<Type>asList(new Uint256(pk1x),
                new Uint256(pk1y),
                new Uint256(pk2x),
                new Uint256(pk2y)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> rk() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> seller() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SELLER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setHashAndPrice(String _hashC, BigInteger _x, BigInteger _PP) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETHASHANDPRICE, 
                Arrays.<Type>asList(new Utf8String(_hashC),
                new Uint256(_x),
                new Uint256(_PP)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> state() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> timeout() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TIMEOUT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyHashC() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFYHASHC, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> x() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_X, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static PreDex load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PreDex(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PreDex load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PreDex(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PreDex load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PreDex(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PreDex load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PreDex(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PreDex> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PreDex.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<PreDex> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PreDex.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PreDex> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PreDex.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PreDex> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PreDex.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class PaidAndSetEkRekEventResponse extends BaseEventResponse {
    }

    public static class PublishedRkEventResponse extends BaseEventResponse {
    }

    public static class RefundToBuyerEventResponse extends BaseEventResponse {
    }

    public static class RequestEventResponse extends BaseEventResponse {
    }

    public static class SetHashCAndPriceEventResponse extends BaseEventResponse {
    }

    public static class TradingCompletedEventResponse extends BaseEventResponse {
    }

    public static class VerifyHashCEventResponse extends BaseEventResponse {
    }
}
