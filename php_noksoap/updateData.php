<?php
 header('Content-type:application/json;charset=utf-8');
	$objConnect = mysql_connect("localhost","root","123456");
	$objDB = mysql_select_db("mydatabase");
	   mysql_query("SET NAMES UTF8");

	$strMemberID = $_POST["sMemberID"];
	$strPassword = $_POST["sPassword"];
	$strName = $_POST["sName"];
	$strEmail = $_POST["sEmail"];
	$strTel = $_POST["sTel"];
	
	/*** Update ***/
	$strSQL = " UPDATE member SET
		Password = '".$strPassword."'
		,Name = '".$strName."'
		,Email = '".$strEmail."'
		,Tel = '".$strTel."'
		WHERE MemberID = '".$strMemberID."'
	";

	$objQuery = mysql_query($strSQL);
	if(!$objQuery)
	{
		$arr['StatusID'] = "0"; 
		$arr['Error'] = "Cannot save data!";	
	}
	else
	{
		$arr['StatusID'] = "1"; 
		$arr['Error'] = "";	
	}

	/**
		$arr['StatusID'] // (0=Failed , 1=Complete)
		$arr['Error'] // Error Message
	*/
	
	mysql_close($objConnect);
	
	echo json_encode($arr);
?>
