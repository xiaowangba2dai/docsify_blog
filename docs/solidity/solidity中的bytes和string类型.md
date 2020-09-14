

## string --> 变长bytes

直接bytes(string)。

```js
contract Array {

   string a = "helloworld";
   bytes public b;
   
   function stringToBytes() public {
       b = bytes(a);
   }
}
```

## 变长bytes --> string

直接string(bytes)。

```js
contract Array {

   string public a = "helloworld";
   bytes public b;
   string public c;
   
   function stringToBytes() public {
       b = bytes(a);
   }
   
   function bytesToString() public {
       c = string(b);
   }
}
```
## 定长bytes --> string
不能直接转换，需要先将定长bytes转为变长bytes，然后再转为string.
```js
contract Array {

   bytes10 public a = 0x68656c6c6f776f726c64; // "helloworld"
   
   string public b;
   
   
   function bytesToString() public {
       // b = string(a); // ERROR
       
       // 1. 定长bytes --> 变长bytes
       bytes memory tmp = new bytes(a.length);
       
       for (uint i=0; i<a.length; i++){
           tmp[i] = a[i];
       }
       
       // 2. 变长bytes --> string 
       b = string(tmp);
   }
}
```
## 改变不定长数组 string的length和value

string类型没有length属性，所以要获取string的长度，需要将string转换为bytes再获取length。

同理，修改字符串也是这样，可以看看一个简单的例子。

```js
contract fixedArray {

    string public _name = "lily";
    
    function nameBytes() view public returns (bytes) {
        return bytes(_name);	// 将string转成bytes
    }
    
    function nameLength() view public returns (uint) {
        return bytes(_name).length; // 然后获取长度
        // return _name.length; // 这样不行，会出错
    }
    
    function changeName() public {
        bytes(_name)[0] = "L";	// 修改字符串
        // _name[0] = "L"; // 这样也不行
    }
    
}
```

## 定长字节数组 --> 变长字节数组

先声明一个bytes数组，然后进行拷贝。

```js
contract fixedArray {

    bytes10 b10 = 0x68656c6c6f776f726c64;   //helloworld  定长数组
    
    // 定长字节数组 --> 变长字节数组 （拷贝）
    // bytes public bs = b10; // ERROR
    bytes public bs = new bytes(b10.length);
    
    function fixedBytesToBytes() public {
        // copy
        for (uint i=0; i<b10.length; i++){
            bs[i] = b10[i];
        }
    }
}
```

