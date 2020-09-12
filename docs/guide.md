docsify: 轻量, 方便，不占太多资源

## 1. 安装docsify

npm i docsify-cli -g

## 2. 初始化

新建一个目录，在根目录下

docsify init .

## 3. 运行

docsify serve . 

http://localhost:3000 到浏览器中看是否运行成功。

## 4. 开始编写第一个文档

根目录下新建一个*_coverpage.md*的markdown文件。

并在该文件中填入以下内容。

```
# 我的文档库

- docsify使用教程
- 很轻量化

[gitme](README)
```

接着需要在index.html中进行开启操作。

在该文件中修改以下内容。

<script>
    window.$docsify = {
      name: '',
      repo: '',
      coverpage: true,
      loadSidebar: true,
    }
  </script>



保存之后，网页自动刷新，但是发现没有sidebar。

在根目录下新建*_sidebar.md*文件，并填入以下内容。

```
# docsify安装使用

- [第一课](page)
- [第二课](page)
```

至此，sidebar也完成，至于链接的page可以写个page.md即可。





