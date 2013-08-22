<?php
function check_account($user, $pass) {
global $_mylibapp;
$link=$_mylibapp['mysql']['dblink'];
	$query = sprintf ( "SELECT `Login` FROM `users` WHERE `Username`='%s' AND `Password`='%s'", mysql_real_escape_string ( $user ), mysql_real_escape_string ( $pass ) );
	$result = mysqli_query ( $link,$query ); $rowNum=mysqli_num_rows($result);
	mysqli_free_result($result);
	if ($rowNum == 0) {
	header ( "Http/1.0 401 Unauthorized" );
		
		header ( "Content-Type: text/html", true );
		echo "<Login msg=\"failed: user=$user pass=$pass\"/>";
		ob_flush();
		login ();
	} else{
		echo "<Login msg=\"Login Success!\"/>";
		return true;
	}
	return false;
}
function login() {
	//ob_clean ();
	include 'index.html';
	exit ();

}