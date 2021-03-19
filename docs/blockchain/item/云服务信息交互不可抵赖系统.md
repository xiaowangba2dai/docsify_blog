# TrustBuilder: A Non-repudiation Scheme for Industrial IoT Cloud Applications

`Github link`: https://github.com/szu-security-group/trust-builder

**TrustBuilder** is a  non-repudiation scheme for industrial IoT cloud applications. The proposed scheme guarantees that neither the IoT client nor the cloud could repudiate a service enjoyment and provisioning. Specifically, the proposed scheme employs a blockchain to achieve non-repudiation. 

Formally, we abstract our non-repudiation scheme as follows.
1) *request_service*. The IoT client sends a service request to the service provider using the blockchain.
2) *promise_service*. The cloud sends a service promise to blockchain.
3) *send_masked_service*. The cloud sends the requested service program to the IoT client via an offchain channel.
4) *confirm_receipt*. After receiving the masked service via the off-chain channel, the IoT client sends an acknowledgement to the blockchain to indicate that it received the masked data.
5) *send_key*. The cloud sends the key that is able to unmask the service to the blockchain. The IoT client fetches this key to obtain the real service. 

## Evaluation Setup

We built a Ethereum test environment on Ubuntu 16.04. To install the test environment, one needs to install NodeJS and NPM on the system in advance.

### Geth

We built a blockchain network on Geth 1.9.23 with proof-of-authority (PoA) consensus mechanism. One can install Geth using terminal commands as follows.

```
sudo add-apt-repository -y ppa:ethereum/ethereum 
sudo apt-get update 
sudo apt-get install ethereum 
```

### Solc

To compile the smart contract ( written in solidity), one also needs to install the solc module. The command is as follows.

```
npm install --save solc@0.4.25
```

### Javascript module

In order to run the Javascript code, one also needs to install web3, async, js-sha256 and node-rsa module. The command is as follows.

```
npm init
npm install web3 --save
npm install async --save
npm install node-rsa --save
npm install js-sha256 --save
```

## Build A Private Ethernum Network

In our experimental evaluation, we created 50 IoT client nodes and 6 service provider notes. To demonstrate the process of establishing the Ethernum private chain concisely, in the followsing, we show the setup of two nodes as an example. They comprise a point-to-point network on our localhost.

### Create workspace

```
mkdir workspace
cd workspace
mkdir node1 node2
```

### Create account

An account (also known as a wallet) has the public-private key pairs. The key is needed to interact with any blockchain network. As an example, we create two accounts as follows. We treat node 1 as *client* and node 2 as *service provider*. For convenience, we set the password to the same value.

```
geth --datadir node1/ account new
geth --datadir node2/ account new
```

For each node, we recommend saving the password in a file. This will simplify some of the processes later (such as unlocking your account).
```
echo 'password' > password.txt
```

### Create genesis file

The 'genesis' file is the file that is used to initialize the blockchain. One can use puppeth to create a genesis file. Use the follow command and choose the PoA consensus mechanism. After that, one can get the genesis.json file, which will be used to initialize each node.

`puppeth`

### Initializing node

One can use genesis.json to initialize the creation block. It is a must to initialize each node with the same genesis file.

```
geth --datadir node1/ init genesis.json
geth --datadir node2/ init genesis.json
```

### Start node

Use the following command format to start node 1. One needs to change the account in the command. The networkid is the same as the one in the previous genesis.json file.

`nohup geth --datadir node1/ --syncmode 'full' --port 30311 --rpc --rpcaddr 'localhost' --rpcport 8501 --rpcapi 'personal,eth,net,web3,txpool,miner' --networkid 666 --allow-insecure-unlock --unlock '0x....' --password password.txt --mine`

After successfully starting the node, one can get the bootnodes of node 1, like this *enode://xxxx@127.0.0.1:30311*.

Now one can use the same way to start node 2. One has to change the account and the bootnodes value.
`nohup geth --datadir node2/ --syncmode 'full' --port 30312 --rpc --rpcaddr 'localhost' --rpcport 8502 --rpcapi 'personal,eth,net,web3,txpool,miner' --networkid 666 --allow-insecure-unlock --unlock '0x....' --password password.txt --mine --bootnodes 'enode://xxxx@127.0.0.1:30311'`

## Usage

### Deploy smart contract

Now, one can use the follow javascript code to deploy the smart contract *(contract/Service.sol)*. 

`node deploy.js`

Once the transaction is packaged into a block, one can get the contract address (e.g. 0x917.....da3) and the abi value. One has to modify the contract address value in *instance.js*. What's more, one also needs to cover the abi value in *instance.js* too.  

### Generate public/private key for client

For each client node, one can run the *generateKey.js* to generate a pair of public/private key. This public key, associated with the address of the client, will be published on the blockchain. For example, one could generate the key pairs for node 1 as follows. 
`node generateKey.js`

### Request service

In this phase, the client will write the service provider address and the service ID data in the smart contract. This ID is unique in this system. One can run the JavaScript code *requestService.js* to simulate the request process.

`node requestService.js`

### Promise service

The the promise_service phase writes a hash value in the smart contract. One can run the *promiseService.js* to simulate the promise process.

`node promiseService.js`

### Confirm service

For the confirm_service phase, the proposed scheme checks whether two hash values are consistent.  Once can run *confirmService.js* to simulate the confirm process.

`node confirmService.js`

### Send key

For the send_key phase, the proposed scheme needs to write a decryption key in the smart contract. However, its size is smaller. Once can run sendKey.js to simulate the send key process.

`node sendKey.js`

 