<?php
include_once ("config.php");
include_once ("error.php");
global $_mylibapp_returncode;

global $_mylibapp;


//output buffer handling
ob_start ("ob_handler" );

// prerpare for error handling
//error_reporting(0);
set_error_handler('error_handler');

// create global db link
$_mylibapp['mysql']['dblink']=create_db_link(
$_mylibapp ['mysql'] ['host'] ,
$_mylibapp ['mysql'] ['default_user'] ,
$_mylibapp ['mysql'] ['default_pass'] ,
$_mylibapp ['mysql'] ['db'] ,
$_mylibapp ['mysql'] ['port']
);

/***** functions *****/

function ob_handler($string, $flags) {
	static $input = array ();
	static $foundXmlData=0;
	$output = '';
	$input [] = $string;
	if ($flags & PHP_OUTPUT_HANDLER_END) {
		$isDebug = isset ( $_REQUEST ['debug'] ) & $_REQUEST['debug']!=0;
		if ($isDebug)
			foreach ( $input as $k => $v )
				$output .= $v; //show all if debug
		else {
			foreach ( $input as $k => $v ) {
				$foundXmlData |= substr ( $v, 0, 6 ) == '<?xml ';
				if ($foundXmlData)
					$output = substr ( $v, 0, strlen ( $v ) - strlen ( '</MyLibraryApp>' ) ) . $output;
				else if (substr ( $v, 0, 1 ) == '<' && substr ( $v, - 1 ) == '>')
					$output .= $v;
			
			}
			
			$output .= ($foundXmlData) ? '</MyLibraryApp>' : '';
		} // show only xml data if not debug
	} // end of ob_end_flush
	

	return $output;
}


function create_db_link($host ,$user ,$pass ,$db ,$port='3306'){
static $count=0;
print_r(++$count);
		$link = mysqli_connect($host,
		$user,
		$pass,
		$db,$port);

		// check connection //
		if (mysqli_connect_errno()) {
			printf("<error msg=\"Connect failed: %s ;host=$host;db=$db:$port\"/>", mysqli_connect_error());
			exit();
		}
		
		mysqli_set_charset($link,"utf8");
		return $link;
}
?>