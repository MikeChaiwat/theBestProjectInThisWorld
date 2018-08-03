<?php
header('Content-type:application/json;charset=utf-8'); 
	$objConnect = mysql_connect("localhost:3306","root","123456");
	$objDB = mysql_select_db("mydatabase");
	mysql_query("SET NAMES UTF8");
	// $_POST["sMemberID"] = "2"; // for Sample

	$strMemberID = $_POST["sMemberID"];
	$strSQL = "SELECT * FROM member WHERE 1 AND MemberID = '".$strMemberID."'  ";

	$objQuery = mysql_query($strSQL);
	$obResult = mysql_fetch_array($objQuery);
	if($obResult)
	{
		$arr["MemberID"] = $obResult["MemberID"];
		$arr["Username"] = $obResult["Username"];
		$arr["Password"] = $obResult["Password"];
		$arr["Name"] = $obResult["Name"];
		$arr["Email"] = $obResult["Email"];
		$arr["Tel"] = $obResult["Tel"];
	}

	
	mysql_close($objConnect);


	
	echo json_encode($arr);
?>
