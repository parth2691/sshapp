<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page session="false" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
  <title>
    <tiles:insertAttribute name="title" />
  </title>
  <link rel="stylesheet" href="<c:url value="/css/blueprint/screen.css" />" type="text/css" media="screen, projection" />
  <link rel="stylesheet" href="<c:url value="/css/blueprint/print.css" />" type="text/css" media="print" />
  <!--[if lt IE 8]>
    <link rel="stylesheet" href="<c:url value="/css/blueprint/ie.css" />" type="text/css" media="screen, projection" />
  <![endif]-->
  <tiles:useAttribute id="styles" name="styles" classname="java.util.List" ignore="true" />
  <c:forEach var="style" items="${styles}">
    <link rel="stylesheet" href="<c:url value="/css/${style}" />" type="text/css" media="all" />
  </c:forEach>
  <script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js" />"></script>
  <tiles:useAttribute id="scripts" name="scripts" classname="java.util.List" ignore="true" />
  <c:forEach var="script" items="${scripts}">
    <script type="text/javascript" src="<c:url value="/js/${script}" />"></script>
  </c:forEach>
  <link rel="shortcut icon" href="<c:url value="/img/icon/extjs.ico" />" />

  <style>
  #logo h1 {
  margin-bottom: 0;
}

#navigation ul {
  margin-left: 0;
  list-style: none;
}

#navigation li {
  float: left;
}

#appointmentNavigation a {
  margin-right: 3px;
}

  </style>
</head>
<body>
  <div id="page" class="container">
    <div id="header" class="span-24 last">
      <p></p>
      <div id="logo" class="span-24 last">
        <div class="span-6">
          <h1>spring3</h1>
          <h2 class="alt">开发框架</h2>
        </div>
      </div>
      <hr/>
    </div>
    <div id="content" class="span-24">
      <tiles:insertAttribute name="content" />
    </div>
    <hr/>
    <div id="footer" class="span-24">
      Copyright (c) 2010 SpringSource
    </div>
  </div>
</body>
</html>