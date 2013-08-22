<?php

global $_mylibapp;
$server=$_SERVER['SERVER_NAME'];

$_mylibapp ['mysql'] ['host'] = "mysqlquery.zymichost.com";
$_mylibapp ['mysql'] ['host'] = "127.0.0.1";
$_mylibapp ['mysql'] ['port'] = 3306;
$_mylibapp ['mysql'] ['db'] = "ayflumm_zxq_store";
//$_mylibapp['mysql']['db']="ayfl_current";
if($server=='ayflumm.zxq.net'){

$_mylibapp ['mysql'] ['default_user'] = "739428_admin";
$_mylibapp ['mysql'] ['default_pass'] = "p:-)t:-(@ayf";

}else if($server=='localhost'||$server=='127.0.0.1'){

$_mylibapp ['mysql'] ['default_user'] = 'username';
$_mylibapp ['mysql'] ['default_pass'] = 'password';

}
$_mylibapp ['strings'] ['user'] = "username";
$_mylibapp ['strings'] ['pass'] = "password";
$_mylibapp ['strings'] ['requestcode'] = "rcode";
$_mylibapp ['strings'] ['requestdata'] = "rdata";
$_mylibapp ['strings'] ['postcode'] = "pcode";
$_mylibapp ['strings'] ['postdata'] = "pdata";
$_mylibapp ['strings'] ['xmldataopening'] = "rdata";
$_mylibapp ['strings'] ['xmldataending'] = "pcode";
$_mylibapp ['pages'] ['debug'] = "debug.php";

$_mylibapp ['dirs']['home'] = "/sdcard/htdocs/MyLibraryApp/";
$_mylibapp ['dirs']['data'] = $_mylibapp['dirs']['home'].'data/';

/***** debug switches*****/
$_mylibapp ['debug'] ['index'] = 1;
$_mylibapp ['debug'] ['request'] = 1;
$_mylibapp ['debug'] ['post'] = 1;
$_mylibapp ['debug'] ['utils'] ['Patterns'] = 0;



/***** customs *****/
//$_mylibapp ['mysql'] ['default_user'] = "root";
//$_mylibapp ['mysql'] ['default_pass'] = "";
?>