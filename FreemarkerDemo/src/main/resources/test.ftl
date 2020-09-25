
<html>
<head>
    <meta charset="utf-8">
    <title>Freemarker入门</title>
</head>
<body>
    ${name},你好，${message} <br>

<#--在模板上定义一个变量-->
<#assign linkman="唐三藏">
    西天取经:${linkman}  <br>

<#--引用其他网页数据-->
<#include "a.ftl"> <br>

<#--if指令-->
<#if success == true>
    是帅哥
    <#else>
    是美女
</#if> <br>

<#--list指令-->
<#list list as m>
 <br>   编号：${m_index +1} 姓名：${m.name} 成绩：${m.grades}
</#list> <br>

<#--内建函数1-->
一共${list?size}条数据 <br>

<#--内建函数2-->
<#assign text="{'bank':'中国银行','account':'1231312'}">
<#assign data=text?eval>
开户行：${data.bank} <br>
账户: ${data.account} <br>

<#--内建函数3-->
当前日期：${today?date}<br>
当前时间：${today?time}<br>
当前日期+时间：${today?datetime}<br>
日期格式化：${today?string('yyyy-MM-dd')}<br>

<#--内建函数4-->
当前积分:${point?c} <br>

<#--空值处理运算符-->
<#if aaa??>
    aaa变量存在
    <#else>
    aaa变量不存在
</#if> <br>

<#--运算符-->
<#if point gt 100>
    是会员
</#if> <br>
<#if (point > 100)>
    是会员
</#if> <br>
</body>
</html>
