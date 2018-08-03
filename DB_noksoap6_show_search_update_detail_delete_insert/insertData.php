<?
	$objConnect = mysql_connect("localhost","root","123456");
	$objDB = mysql_select_db("mydatabase");
	
	/*** for Sample 
		$_POST["sUsername"] = "a";
		$_POST["sPassword"] = "b";
		$_POST["sName"] = "c";
		$_POST["sEmail"] = "d";
		$_POST["sTel"] = "e";
	*/

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

	/**
		$arr['StatusID'] // (0=Failed , 1=Complete)
		$arr['Error'] // Error Message
	*/
	
	mysql_close($objConnect);
	
	echo json_encode($arr);
?>
