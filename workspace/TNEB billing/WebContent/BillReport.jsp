<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>TNEB</title>
      <style>
	     #container{			
			border: 1px solid black; 
			border-image: none; 
			width: 55%; 
			margin-top: 4%;
			 margin-left: 16%;
		 }
         #header {
         background-color:cadetBlue;
         color:white;
         text-align:center;
         padding:5px;
         }
         #nav {
         line-height:30px;
         background-color:silver;
         height:300px;
         width:100px;
         float:left;
         padding:5px;	      
         }
         #section {
         width:350px;
         float:left;
         padding:10px;	 	
		  margin-top: 9%;
		  margin-left: 20%;	
         }
         #footer {
         background-color:grey;
         color:white;
         clear:both;
         text-align:center;
         padding:5px;	 	 
         }
         #info{
         width: 87%; 
         margin-top: 8%;
         float: left;
         }
      </style>
   </head>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
   <script>
   function myfunction(){	   
	    var divHtml = '<div>Customer Name : <input type="text" placeholder="Full Name" name="custName"></div>';
	    	divHtml = divHtml+''
	        divHtml = divHtml+ '<div>Customer Id : <input type="text" placeholder="customerId" name="newCustId" style="margin-left: 7%;"></div>';
			divHtml = divHtml+ '<div>Address : <input type="text" placeholder="Full Address" name="address" style="margin-left: 14.75%;">'; 
			divHtml = divHtml+ '<input type="submit" name="insertCust" value="Insert"></div>'; 
	     $('#section').html(divHtml);
	   }
   function myReportfun(){   
	   var divhtml = 'Search Customer : <input type="text" value=""  name="report" />';
			   divhtml = divhtml+'<input type="submit" value="Submit" />';
				divhtml = divhtml+'<input type="submit" value="Generate All Reports" name="report" style="margin-top: 11%; margin-left: 30%;" />';
		$('#section').html(divhtml);
   }
   function hadoopSearch(){
	   var divhtml='Search Customer : <input type="text" value=""  name="hadoopId" />';
	   divhtml = divhtml+'<input type="submit" value="Submit" name="hadoopSearch" />';
	   divhtml = divhtml+' <input type="submit" value="Generate Hadoop Reports" name="hadoop" style="margin-top: 11%; margin-left: 30%;" />';
	   $('#section').html('');
	   $('#section').html(divhtml);
   }
   </script>
   <body>
      <form action="serv/bill" method="post">
	  <div id="container">
         <div id="header">
			<img src="C:\Users\Administrator\Desktop\tneblogo.jpg" style="width: 114px; height: 87px; margin-top: -4px; margin-left: -5px; float: left;"/>
            <h1>TNEB</h1>
         </div>
         <div id="nav">
            <input type="button" value="Add Customer" onclick="myfunction();" style="width:100%;margin-top: 130%;">
            <input type="button" value="Report" onclick="myReportfun();" style="width:100%; margin-top: 10%;">
            <input type="button" value="Hadoop" onclick="hadoopSearch();" style="width:100%; margin-top: 10%;">
         </div>
         <div id="section">
             Search Customer : <input type="text" value=""  name="custId" />
 	         <input type="submit" value="Submit" />
  	         <input type="submit" value="Generate All Reports" name="report" style="margin-top: 11%; margin-left: 30%;" />
         </div>
         <div id="info"><marquee direction="right" style="color:red;">*Quotes of the day :"If you don't wanna pay it save it..."</marquee></div>
         <div id="footer">
           Copyright � 2013.  TamilNadu Generation and Distribution Corporation Limited 
         </div>
		</div> 
      </form>
   </body>
</html>