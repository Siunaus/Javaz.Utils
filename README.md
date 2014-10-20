Javaz.Utils
===========

Java语言的简单工具集合

+ CodecUtil 编码/解码相关的工具类

    1、Md5编码

        String md5Code = CodeUtil.MD5("Zero");

        or

        String md5Code = CodeUtil.MD5("Zero","utf-8");

    2、Base64编码

        String encodeStr = CodeUtil.base64Encode("Zero");

    3、Base64解码

        String decodeStr = CodeUtil.base64Decode("");

    4、将一个Java对象序列化为字节数组对象

    5、将字节数组对象反序列化为一个Java对象

+ DateUtil 日期相关的工具类

    1、提供日期转字符串的接口

    2、提供字符串转日期的接口

    3、提供两个日期之间相隔的时，分，秒等

+ FileUtil 文件相关的工具类

+ HttpUtil 网络请求相关的工具类

+ MathUtil 数学函数相关的工具类
    
    1、提供随机指定区间内的随机数

    2、提供指定位数的随机整数

+ StringUtil 字符串相关的工具类

+ XlsUtil Excel表格相关的工具类

+ XmlUtil Xml相关的工具类

+ ArgsUtil Java命令行参数相关的工具类
    
    1、将命令行传进来的参数保存到一个Map之中

    2、对外提供通过key来获取参数具体值的接口

+ PathUtil 资源代码路径相关的工具类

+ CommUtil 通用工具类

