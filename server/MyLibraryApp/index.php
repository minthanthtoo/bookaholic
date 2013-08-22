<?php
include ("config/startup.php");
include ('authentication.php');
print_r($_SERVER);
$user = '';
$pass = '';
if (isset ( $_SERVER ["HTTP_AUTHORIZATION"] )) {
	
	list ( $user, $pass ) = explode ( ':', base64_decode ( substr ( $_SERVER ['HTTP_AUTHORIZATION'], 6 ) ) );
	print_r(array($user,$pass));

} elseif (isset ( $_REQUEST ['user'] )) {
	
	$user = $_REQUEST ['user'];
	$pass = $_REQUEST ['pass'];

} else {

}
if (! check_account ( $user, $pass )) {
	login ();
}
/* 
if(isset($_SESSION['views']))
$_SESSION['views']=$_SESSION['views']+1;
else
$_SESSION['views']=1;
echo "Views=". $_SESSION['views'];
*/
/*
$db_host= (isset ( $_REQUEST ['host'] ) and $_REQUEST ['host'] != "" ) ? $_REQUEST ['host'] : $_mylibapp ['mysql'] ['host'];

$db_user= (isset ( $_REQUEST ['user'] ) and $_REQUEST ['user'] != "" )? $_REQUEST ['user'] : $_mylibapp ['mysql'] ['default_user'];

$db_pass= (isset ( $_REQUEST ['pass'] ) and $_REQUEST ['pass']) != "" ? $_REQUEST ['pass'] : $_mylibapp ['mysql'] ['default_pass'];
*/

if (isset ( $_REQUEST [$_mylibapp ['strings'] ['requestcode']] )) {
	include ('request.php');
	$requestData = urldecode ( $_REQUEST [$_mylibapp ['strings'] ['requestdata']] );
	
	$requestCode = $_REQUEST [$_mylibapp ['strings'] ['requestcode']];
	
	if (is_db_request ( $requestCode ))
		db_request ( $requestCode, $requestData ,$_mylibapp['mysql']['dblink']);
	
	else
		;

	//header('Location:request.php');
//exit ();
} else if (isset ( $_REQUEST [$_mylibapp ['strings'] ['postcode']] )) {
	
	include ('post.php');
	$postData = urldecode ( $_REQUEST [$_mylibapp ['strings'] ['postdata']] );
	
	$postCode = $_REQUEST [$_mylibapp ['strings'] ['postcode']];
	
	db_post ( $postCode, $postData, (isset ( $_REQUEST ['host'] ) and $_REQUEST ['host']) != "" ? $_REQUEST ['host'] : $_mylibapp ['mysql'] ['host'], (isset ( $_REQUEST ['user'] ) and $_REQUEST ['user']) != "" ? $_REQUEST ['user'] : $_mylibapp ['mysql'] ['default_user'], (isset ( $_REQUEST ['pass'] ) and $_REQUEST ['pass']) != "" ? $_REQUEST ['pass'] : $_mylibapp ['mysql'] ['default_pass'], $_mylibapp ['mysql'] ['db'], $_mylibapp ['mysql'] ['port'] );

	//header('Location: post.php');
//exit ();
} else if (isset ( $_REQUEST ['debug'] )) {
	header ( 'Location:' . $_mylibapp ['pages'] ['debug'] );
} else {
	
	include ("index.html");
}
function db_connect() {
	//echo '$_SERVER[REQUEST_METHOD]'.$_SERVER['REQUEST_METHOD'];
	

	if (isset ( $_REQUEST ['id'] )) {
	
	} else {
	}
	if (isset ( $_REQUEST [$_mylibapp ['strings'] ['user']] )) {
		if (isset ( $_REQUEST [$_mylibapp ['strings'] ['pass']] )) {
			$_user = $_REQUEST [$_mylibapp ['strings'] ['user']];
			$_pass = $_REQUEST [$_mylibapp ['strings'] ['pass']];
			
			echo mysql_connect ( $_mylibapp ['mysql'] ['host'] . ":" . $_mylibapp ['mysql'] ['port'] . ":" . $_mylibapp ['mysql'] ['db'], $_user, $_pass );
		} else {
			$­_mylibapp_returncode [count ( $_mylibapp_returncode )] = 1;
		}
	} else {
		echo mysql_connect ( $_mylibapp ['mysql'] ['host'] . ":" . $_mylibapp ['mysql'] ['port'] . ":" . $_mylibapp ['mysql'] ['db'], $_mylibapp ['mysql'] ['default_user'], $_mylibapp ['mysql'] ['default_pass'] );
	}
	echo "<br>............";
	echo "<br>" . mysql_close ();
	include ("return.php");
}
?>
<?php

/***** START: Login *****

$mysqli = new mysqli('localhost', 'root', 'pass', 'nameofDB');

if (mysqli_connect_errno()) { printf("Connect failed: %s\n", mysqli_connect_error()); exit(); }

$cookie = (isset($_COOKIE['vuid'])) ? $_COOKIE['vuid'] : '';

if(!$cookie) { $user_level = 0; setcookie('vuid', '0.' . md5('anonymous')); } else { $part = explode('.', $cookie); $user_level = (int) $part[0]; $user_name = (string) $part[1];

if($user_level != 0) { $is_logged = check_login($user_level, $user_name); } else { $is_logged = false; } }

 *levels betwen 1-9*/
/* '9.' . md5('PutTheUserNameHere')); echo '<meta http-equiv="refresh" content="1;URL=index.php"> Correct Login<br> You will be redirected to the main page'; } 
} elseif(isset($_GET['logout'])) { setcookie('vuid', '0.' . md5('anonymous')); echo '<meta http-equiv="refresh" content="1;URL=index.php"> You are out now ... see you soon<br> You will be redirected to the main page'; } else { if($is_logged) { echo 'Welcome ' . USERNAME . ' <a href="? logout">LOGOUT</a>'; } else { echo 'You are a Anonymous. LOGIN <a href="? login">HERE</a>'; } }

function check_login($user_level, $user_name) { global $mysqli;

$is_logged = false;

$result = $mysqli->query('SELECT nick FROM accounts WHERE user_level = ' . $user_level); $row = $result->fetch_array(); $result->close(); if($user_name === md5($row['nick'])) { define('USERNAME', $row['nick']); return $is_logged = true; } return $is_logged = false; }

$mysqli->close();
*/
?>