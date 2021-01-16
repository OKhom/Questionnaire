<%@ page import="com.javapro.Questionnaire.ResultServlet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Questionnaire</title>
</head>
<body>
<h1>Questionnaire</h1>
<% String login = (String)session.getAttribute("userLogin"); %>
<% if (login == null || "".equals(login)) { %>
<form action="/login" method="POST">
    Login: <input type="text" name="login"><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" />
</form>
<% } else { %>
<p>You are logged in as: <%= login %></p>
<form action="/result" method="POST">
    <p><h3><%=ResultServlet.getQuestion1()%></h3>
    <%for (int i = 0; i < ResultServlet.getAnswers1().length; i++) {%>
    <input type="radio" id="question1choice<%=i%>"
           name="question1" value="<%=i%>">
    <label for="question1choice<%=i%>"><%=ResultServlet.getAnswers1()[i]%></label>
    <%}%>

    <p><h3><%=ResultServlet.getQuestion2()%></h3>
    <%for (int i = 0; i < ResultServlet.getAnswers2().length; i++) {%>
    <input type="radio" id="question2choice<%=i%>"
           name="question2" value="<%=i%>">
    <label for="question2choice<%=i%>"><%=ResultServlet.getAnswers2()[i]%></label>
    <%}%>
    
    <p><button type="submit">Submit</button></p>
</form>

<br>Click this link to <a href="/login?a=exit">logout</a>
<% } %>
<br>
</body>
</html>