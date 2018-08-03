<?php
 header('Content-type:application/json;charset=utf-8');
	$objConnect = mysql_connect("localhost","root","123456");
	$objDB = mysql_select_db("mydatabase");
	mysql_query("SET NAMES UTF8");

	$strUsername = $_POST["sUsername"];
	$strPassword = $_POST["sPassword"];
	$strName = $_POST["sName"];
	$strEmail = $_POST["sEmail"];
	$strTel = $_POST["sTel"];

	/*** Check Username Exists ***/
	$strSQL = "SELECT * FROM member WHERE Username = '".$strUsername."' ";
	$objQuery = mysql_query($strSQL);
	$objResult = mysql_fetch_array($objQuery);
	if($objResult)
	{
		$arr['StatusID'] = "0"; 
		$arr['Error'] = "Username Exists!";	
		echo json_encode($arr);
		exit();
	}

	/*** Check Email Exists ***/
	$strSQL = "SELECT * FROM member WHERE Email = '".$strEmail."' ";
	$objQuery = mysql_query($strSQL);
	$objResult = mysql_fetch_array($objQuery);
	if($objResult)
	{
		$arr['StatusID'] = "0"; 
		$arr['Error'] = "Email Exists!";	
		echo json_encode($arr);
		exit();
	}
	
	/*** Insert ***/
	$strSQL = "INSERT INTO member (Username,Password,Name,Email,Tel) 
		VALUES (
			'".$strUsername."',
			'".$strPassword."',
			'".$strName."',
			'".$strEmail."',
			'".$strTel."'
			)
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


	
	mysql_close($objConnect);
	
	echo json_encode($arr);
?>
