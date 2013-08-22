<?php

/****** all are functions ******/
/****** START: db post ******/

include ("utils/Patterns.php");

function is_db_post($code) { // checking if the post is for file or db query
	return ($code < 0) ? false : true;
}

function db_post($postCode, $postData, $host, $user, $pass, $db, $port = 3306) {
	global $_mylibapp;
	$DEBUG = $_mylibapp ['debug'] ['post'];
	
	$postData = dataQueryToArray ( $postData );
	 			$link=$_mylibapp['mysql']['dblink'];
	
	$q = "select * from `_posthandler` where id=$postCode";
	//
	if ($stmt = mysqli_prepare ( $link, $q )) {
		
		/* execute statement */
		mysqli_stmt_execute ( $stmt );
		$query = "";
		
		/* bind result variables */
		mysqli_stmt_bind_result ( $stmt, $id, $fileStoreLocator, $query, $xmlPattern, $comment );
		mysqli_stmt_fetch ( $stmt );
		/* close statement */
		mysqli_stmt_close ( $stmt );
		if ($query == "") {
			//_load_error();
			echo "<br><b>Warning: </b>No query found for post code '$postCode' on the server.<br>";
			if ($DEBUG) {
				print_r ( $stmt );
				print_r ( $query );
				print_r ( $link );
				echo $user . ':' . $pass . ':' . $db;
			}
			return;
		}
		
		// manage file posts
		$fileStoreLocator = resolve_posted_file_location ( $postCode, $postData, $fileStoreLocator, $_mylibapp ['dirs'] ['data'] );
		if ($DEBUG)
			print_r ( $fileStoreLocator );
		
		move_uploaded_files ( $fileStoreLocator );
		
		// convert the acquired raw query to valid query by merging corresponding post data 
		$query = translate_direct_literals ( $query, $postData, $fileStoreLocator );
		/* fetch values */
		$result = null;
		$xmlOutput = "";
		if (mysqli_multi_query ( $link, $query )) {
			do {
				$table = array ();
				if (($result = mysqli_store_result ( $link )) && mysqli_num_rows ( $result )) {
					while ( $row = mysqli_fetch_row ( $result ) )
						$table [] = $row;
					mysqli_free_result ( $result );
					
					// $xmlPattern=translate_result_literals($xmlPattern , $table); // TODO: implement later
					if ($DEBUG)
						echo "\n\$xmlPattern=$xmlPattern\n";
					$xmlPattern = translate_direct_literals ( $xmlPattern, $postData, $fileStoreLocator );
					$xmlOutput .= translate_result_literals ( $xmlPattern, $table );
				
		// echo $xmlOutput;
				

				}
				if (! mysqli_more_results ( $link ))
					break;
			} while ( mysqli_next_result ( $link ) ); // close do-while
		}
		if ($result === false && mysqli_errno ( $link )) {
			printf ( "ERROR in query: %s\nWhole query: = %s", mysqli_error ( $link ), $query );
		
		//exit();
		}
		if ($DEBUG) {
			echo "\n\$xmlOutput=$xmlOutput";
			echo "\n\$xmlPattern=$xmlPattern\n";
		}
		// print out the result
		ob_flush();
		echo '<?xml version="1.0" encoding="UTF-8" ?>' . "\n<MyLibraryApp>\n" . $xmlOutput . "\n</MyLibraryApp>";
		ob_end_flush();
	
	} else {
		echo "<br><b>Warning: </b>MySql statement error.<br>";
		echo "Query:\"$q\"";
	}
}
function multi_query($link, $query) {
	/* execute multi query */
	if (mysqli_multi_query ( $link, $query )) {
		do {
			/* store first result set */
			if ($result = mysqli_store_result ( $link )) {
				while ( $row = mysqli_fetch_row ( $result ) ) {
					printf ( "%s\n", $row [0] );
				}
				mysqli_free_result ( $result );
			}
			/* print divider */
			if (mysqli_more_results ( $link )) {
				printf ( "-----------------\n" );
			}
		} while ( mysqli_next_result ( $link ) );
	}
	/* close connection */
	mysqli_close ( $link );
}
/***** end: db post *****/
?><?php

/***** START:file post *****/
//testFilePost();


function testFilePost() {
	
	$response = postFile ( 'userfile' );
	
	$response .= getFileContent ( 'uploadedFile' );
	if (isset ( $_REQUEST ['rdata'] )) {
		$response .= $_REQUEST ['rdata'];
	}
	//header("Content-Length: ".strlen($response));
	

	//header("Content-Length: "."10000");
	echo $response;
	echo print_r ( $_FILES );
}

function postFile($upload) {
	$response = '';
	$uploaddir = " /www/zxq.net/a/y/f/ayflumm/htdocs/MyLibraryApp/";
	$uploaddir = "/sdcard/htdocs/upload/";
	$uploadfile = $uploaddir . basename ( $_FILES [$upload] ['name'] );
	
	//echo '<pre>';
	if (move_uploaded_file ( $_FILES [$upload] ['tmp_name'], $uploadfile )) {
		$response .= "File is valid, and was successfully uploaded.\n";
	} else {
		$response .= "Possible file upload attack!\n";
	}
	
	$response .= 'Here is some more debugging info:' . ".........";
	//header("Content-Length: ".strlen($response));
	//echo $response;
	//print_r($_FILES);
	

	//print "</pre>";
	return $response;
}

function getFileContent($file) {
	$uploaddir = "/sdcard/htdocs/upload/";
	$file = file_get_contents ( $uploaddir . $file );
	return $file;
}

/*
* e.g.
$postData=
*  $fileStoreLocator => "0=path/to/file/%[d0]"
* $defaultFileStorePath => "/htdocs/MyLibraryApp/data/"
*/
function resolve_posted_file_location($postCode, $postData, $fileStoreLocator, $defaultFileStorePath) {
	global $_mylibapp;
	$DEBUG = $_mylibapp ['debug'] ['post'];
	
	$resolvedFilePath = array ();
	
	if (isset ( $_FILES )) {
		
		$fileStoreLocator = translate_direct_literals ( $fileStoreLocator, $postData );
		
		// split  file-storage locations
		preg_match_all ( '/[^=\n\r]*=.*[^\n\r]+/', $fileStoreLocator, $fileStoreLocator ); // split lines
		if ($DEBUG) {
			echo "\nfileStoreLocator=";
			print_r ( $fileStoreLocator );
		}
		$fileStoreLocator = $fileStoreLocator [0];
		
		foreach ( $fileStoreLocator as $i => $path ) {
			list ( $i, $path ) = explode ( '=', $path, 2 ); // split each line into key-value array
			$resolvedFilePath [$i] = $path;
		}
		
		$ini = parse_ini_file ( $defaultFileStorePath . 'file_allocate' );
		if ($DEBUG){
		echo "\nmain ini file:";
			print_r ( $ini );
			}
		$allowedCodes = $ini ['allowed-post-codes'];
		
		// choose whether default folder or other available remote sites for storage of uploaded files
		$desDirs = (in_array ( $postCode, $allowedCodes )) ? array (0 => $defaultFileStorePath ) : $ini ['file-store-sites'];
		
		foreach ( $_FILES as $index => $src ) {
			if ($src ['error'] > 0) {
				//_load_error();
			} else {
				
				foreach ( $desDirs as $des ) {
					$iniValues = parse_ini_file ( $des . 'file_allocate' );
					if($DEBUG){
					echo "\nparsed ini file=";
					print_r($iniValues);
					}
					$isWriteOK = ($iniValues ['available'] == true/* check other conditions */) ? true : 

					false;
					if ($isWriteOK)
						break;
					$des='';
				}
				if($des=='')// if no allowed dir is not found
				//load_error()
				;
				$resolvedFilePath [$index] = $des . $resolvedFilePath [$index];
			
			}
		}
	
	}
	
	if ($DEBUG){
	echo "\n\$resolvedFilePath=";
		print_r ( $resolvedFilePath );
		echo "\n\$_FILES=";
		print_r ($_FILES);
		}
	return $resolvedFilePath;
}

/*
* e.g.
* $desFilePaths=Array(
* [123] => /htdocs/MyLibraryApp/data/dir/123,
* [124] => /htdocs/MyLibrarApp/data/dir/124
* )
*/
function move_uploaded_files($desFilePaths = array()) {
	foreach ( $desFilePaths as $src => $des ) {
		$srcContent = file_get_contents ( $_FILES [$src] ['tmp_name'] );
		if (! is_dir ( dirname ( $des ) ))
			mkdir ( dirname ( $des ), 0, true );
		
		if ($desFile = fopen ( $des, 'w' ))
			if (! fwrite ( $desFile, $srcContent )) {
				// TODO: cancel all uploads,return error
			}
	
	}
}
?>