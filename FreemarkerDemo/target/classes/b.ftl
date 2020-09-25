<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>表格标签</title>
</head>
<body>
<table width="50%" align="center" border="1" cellpadding="1" Cellspacing="1" bgcolor="red" >
    <caption>学生成绩</caption>
    <tr>
        <th>编号</th>
        <th>姓名</th>
        <th>成绩</th>
    </tr>
    <tr bgcolor="#9932cc" align="center">
        <#list list as m>
            <td>${m_index +1}</td>
            <td>${m.name}</td>
            <td>${m.grades}</td>
        </#list>
    </tr>
</table>
</body>
</html>