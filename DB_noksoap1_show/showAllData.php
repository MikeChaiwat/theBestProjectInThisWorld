<?
	$objConnect = mysql_connect("localhost:3306","root","123456");  
          ???????????  = ????????? mysql ?????????? localhost ????? 3306
	$objDB = mysql_select_db("mydatabase");
          ???????????  =  ????????????????????  mydatabase
		$_POST["txtKeyword"] = ""; // for Sample
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
		mysql_close($objConnect);
		echo json_encode($resultArray);
 //   echo $intNumField;
     echo $arrCol[0];
//echo $objQuery[1];
?>
