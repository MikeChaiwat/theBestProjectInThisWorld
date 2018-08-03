<?php
	$objConnect = mysql_connect("localhost","root","123456");
	$objDB = mysql_select_db("mydatabase");
	
	// $_POST["sMemberID"] = "1"; // for Sample

	$strMemberID = $_POST["sMemberID"];
	$strSQL = "DELETE FROM member WHERE 1 AND MemberID = '".$strMemberID."'   ";

	$objQuery = mysql_query($strSQL);
	if(!$objQuery)
	{
		$arr["StatusID"] = "0";
		$arr["Error"] = "Cannot delete data!";
	}
	else
	{
		$arr["StatusID"] = "1";
		$arr["Error"] = "";
	}
	
	/**
		$arr['StatusID'] // (0=Failed , 1=Complete)
		$arr['Error' // Error Message
	*/

	mysql_close($objConnect);
	
	echo json_encode($arr);
?>
