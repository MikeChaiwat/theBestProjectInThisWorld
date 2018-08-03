<?php 
 header('Content-type:application/json;charset=utf-8');  //สำคัญ
	$objConnect = mysql_connect("localhost:3306","root","123456");
	$objDB = mysql_select_db("mydatabase");
        mysql_query("SET NAMES UTF8");
		$strKeyword = $_POST["txtKeyword"];
	$strSQL = "SELECT * FROM member WHERE 1 AND Name LIKE '%".$strKeyword."%'  ORDER BY MemberID ASC  ";
	$objQuery = mysql_query($strSQL);
	$intNumField = mysql_num_fields($objQuery);
	$resultArray = array();
	while($obResult = mysql_fetch_array($objQuery))
	{
		$arrCol = array();
		for($i=0;$i<$intNumField;$i++)
		{
			$arrCol[mysql_field_name($objQuery,$i)] = $obResult[$i];
		}
		array_push($resultArray,$arrCol);
	}
	 echo json_encode($resultArray); 
  // echo json_encode(array($resultArray), JSON_UNESCAPED_UNICODE);    // สำคัญ
		    
	mysql_close($objConnect);
?>
