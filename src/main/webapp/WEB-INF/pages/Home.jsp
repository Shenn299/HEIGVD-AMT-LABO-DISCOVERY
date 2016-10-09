
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <link href="assets/css/home.css" rel="stylesheet">
      <title>Home</title>
   </head>
   <body>
      <h1>Welcome home ${sessionScope.identity} !</h1>

      <form action="Logout" class="login-form" method="POST">
         <input type="submit" class="button" name="logout" value="Log Out"/>
      </form>
      
      <form action="userManager.html" class="login-form">
         <input type="submit" class="button" value="User manager"/>
      </form>      
      
   </body>
</html>
