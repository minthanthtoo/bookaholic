<?php
	include("utils/Patterns.php");
	
	function is_db_request($code){// checking if the request is for file or db query
	return ($code<0)? false: true;
	}
	
	function db_request($requestCode,$requestData,$link//,
		//$host=$cfg['$_mylibapp_mysql_host'],
		//$user=$cfg['$_mylibapp_mysql_default_user'],
		//$pass=$cfg['$_mylibapp_mysql_default_pass'],
//				$host,
//				$user,
//				$pass,
//				$db,$port=3306
){
				global $_mylibapp;
				$DEBUG = $_mylibapp['debug']['request'];
				
				$requestData = dataQueryToArray($requestData);
				
		
		$q= "select * from `_requesthandler` where id=$requestCode";
//
		if ($stmt = mysqli_prepare($link, $q)) {

			/* execute statement */
			mysqli_stmt_execute($stmt);
			$query="";

			/* bind result variables */
			mysqli_stmt_bind_result($stmt , $id, $query ,$xmlPattern , $comment );
			mysqli_stmt_fetch($stmt);
			/* close statement */
			mysqli_stmt_close($stmt);
			if($query==""){
				//_load_error();
				echo "\n<br><b>Warning: </b>No query found for request code '$requestCode' on the server.<br>";
				if($DEBUG){
				print_r($stmt);
				print_r($query);
				print_r($link);echo $user.$pass.$db;
				}
				return;
			}

			$query=translate_direct_literals($query,$requestData);
			/* fetch values */
			$result=null;
			$xmlOutput="";
			$table = array();
			if (mysqli_multi_query($link,$query)) {
			do{
				
				if(($result=mysqli_store_result($link)) && mysqli_num_rows($result)){
				
				while($row=mysqli_fetch_row($result))
					$table[]=$row;
					mysqli_free_result($result);
				if($DEBUG){
				echo "\nresult table=";
				print_r($table);
				}
					$xmlPattern=translate_direct_literals($xmlPattern,$requestData);
				$xmlOutput.=translate_result_literals($xmlPattern,$table);
				// echo $xmlOutput;
				
				}
		if(!mysqli_more_results($link))break;
				} while(mysqli_next_result($link)); // end of do-while
			}
			if ( $result ===false && mysqli_errno($link))
				{
				  printf("ERROR in query: %s\nWhole query: = %s", mysqli_error($link),$query);
				  exit();
				}
				// print out the result
				ob_flush();
			echo '<?xml version="1.0" encoding="UTF-8" ?>'
			."\n<MyLibraryApp>\n"
			.$xmlOutput
			."\n</MyLibraryApp>";
			ob_flush();

		}else{
			echo "<br><b>Warning: </b>MySql statement error.<br>";
			echo "Query:\"$q\"";
		}
	}
	function multi_query($link,$query){
/* execute multi query */
if (mysqli_multi_query($link, $query)) {
do {
/* store first result set */
if ($result = mysqli_store_result($link)) {
while ($row = mysqli_fetch_row($result)) {
printf("%s\n", $row[0]);
}
mysqli_free_result($result);
}
/* print divider */
if (mysqli_more_results($link)) {
printf("-----------------\n");
}
} while (mysqli_next_result($link));
}
/* close connection */
mysqli_close($link);
}
?>