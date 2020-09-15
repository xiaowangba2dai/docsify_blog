通常写好智能合约后我们需要用另一种工具是部署或者调用它的方法，可以是js、python或者是java。由于自己的实验需求，需要使用java调用智能合约，所以我在这里记录下整个流程，以便之后查看。

## 安装web3j CLI

web3j提供CLI程式库，我们可以在CLI中将智能合约编译出来的abi和bin文件转成Java Class，之后我们就可以使用这个Java Class对合约进行操作。

Windows下管理员打开PowerShell，并输入以下命令：

`Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://raw.githubusercontent.com/web3j/web3j-installer/master/installer.ps1'))`

这个命令会自动帮你配置好环境变量，下载完成之后在cmd下输入以下命令检查是否安装成功：

`web3j version`

## Maven依赖

打开ide，我这里是idea，新建一个maven项目，在pom.xml文件中输入以下依赖。

```xml
<dependency>
   <groupId>org.web3j</groupId>
   <artifactId>core</artifactId>
   <version>4.6.3</version>
</dependency>
```

## 编辑智能合约

这里先提供一个简单的合约，便于测试，建议直接在remix中编辑比较方便。

```js
pragma solidity >=0.4.22 <0.7.0;

contract CloudStorageIntegrityChecking {
    
    /* Challenge Data, include: indcies、coefficients */
    uint[] public _indices;
    uint[] public _coefficients;
    
    string public _value;
    
    function setValue(string memory value) public {
        _value = value;
    }
    
    function getValue() public view returns (string memory) {
        return _value;
    }
    
    function setChallengeData(uint[] memory indcies, uint[] memory coefficients) public {
        _indices = indcies;
        _coefficients = coefficients;
    }
    
    function getChallengeData(uint i) public view returns (uint, uint) {
        return (_indices[i], _coefficients[i]);
    }
    
}
```

这个合约叫做cloudStorageIntegrityChecking.sol，编译之后可以找到ABI和Bytecode，分别复制之后建立两个文件：cloudStorageIntegrityChecking.abi和cloudStorageIntegrityChecking.bin。

## web3 CLI 轉換smart contract

接下来就是关键的一步了。

在之前的abi和bin文件目录下，执行以下命令：

`web3j solidity generate -b ./cloudStorageIntegrityChecking.bin -a ./cloudStorageIntegrityChecking.abi -o ./src/main/java -p org.web3j`

- -b: bin文件的路径
- -a: abi文件的路径
- -o: 产生出来的Java文件路径
- -p: 产生出来的Java文件的package name

## 部署合约

首先，要获取web3对象, 然后加载钱包，以及设计GasProvider对象。

```java
// 1. We start by creating a new web3j instance to connect to remote nodes on the network.
Web3j web3j = Web3j.build(new HttpService("https://kovan.infura.io/v3/...."));

// 2. We then need to load our Ethereum wallet file 
Credentials credentials = WalletUtils.loadCredentials("your password", "./wallet/UTC--......json");

// 3. Set GasProvider
ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(1000000L)); // 20 Gwei, GasLimit 1000000
```

接着，我们就可以部署合约了。
```java
CloudStorageIntegrityChecking cloudStorageIntegrityChecking = CloudStorageIntegrityChecking.deploy(web3j, credentials, provider).send();

System.out.println(cloudStorageIntegrityChecking.getContractAddress());
```

其中，创建一个钱包的方法：
```java
String fileName = WalletUtils.generateNewWalletFile(
                "your password",
                new File("./wallet"));
```
## 调用合约

调用合约需要前面部署完成的合约地址。
```java
CloudStorageIntegrityChecking contract = CloudStorageIntegrityChecking.load("0x your contract address", web3j, credentials, provider);
// 1. change the value
contract.setValue("aaa").send();
// 2. get the value
System.out.println("value:" + contract.getValue().send());

List<BigInteger> list1 = new ArrayList<BigInteger>();
list1.add(new BigInteger("12345"));
list1.add(new BigInteger("23456"));

List<BigInteger> list2 = new ArrayList<BigInteger>();
list2.add(new BigInteger("10338138324730068811245487336181438562"));
list2.add(new BigInteger("142321336715907430428130630560308685362"));

System.out.println(list1);
System.out.println(list2);

BigInteger index = new BigInteger("1");
// 3. change the challengeData
contract.setChallengeData(list1, list2).send();
// 4. get the challengeData
System.out.println(contract.getChallengeData(index).send());
```

## 查询账户ether余额

```java
EthGetBalance ethGetBalance = web3j.ethGetBalance("your wallet address", DefaultBlockParameterName.LATEST).sendAsync().get();
 
 BigInteger balance = ethGetBalance.getBalance();
 
System.out.println("balance: " + Convert.fromWei(balance.toString(), Convert.Unit.ETHER));
```

