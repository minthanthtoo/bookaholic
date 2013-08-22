<?php
/***** functions *****/
function error_handler($errno, $errmsg, $filename, $linenum, $vars) {
global $_mylibapp;
$dt = date("Y-m-d H:i:s (T)");

// define an assoc array of error string
// in reality the only entries we should
// consider are E_WARNING, E_NOTICE, E_USER_ERROR,
// E_USER_WARNING and E_USER_NOTICE
$errortype = array ( E_ERROR => "Error", E_WARNING => "Warning", E_PARSE => "Parsing Error", E_NOTICE => "Notice", E_CORE_ERROR => "Core Error", E_CORE_WARNING => "Core Warning", E_COMPILE_ERROR => "Compile Error", E_COMPILE_WARNING => "Compile Warning", E_USER_ERROR => "User Error", E_USER_WARNING => "User Warning", E_USER_NOTICE => "User Notice", E_STRICT => "Runtime Notice" );
// set of errors for which a var trace will be saved 
$user_errors = array(E_USER_ERROR, E_USER_WARNING, E_USER_NOTICE);

$err = "<error>\n";
$err .= "\t<datetime>" . $dt . "</datetime>\n";
$err .= "\t<num>" . $errno . "</num>\n";
$err .= "\t<type>" . $errortype[$errno] . "</type>\n";
$err .= "\t<msg>" . $errmsg . "</msg>\n";
$err .= "\t<script>" . $filename . "</script>\n";
$err .= "\t<linenum>" . $linenum . "</linenum>\n";

if (in_array($errno, $user_errors)) {
$err .= "\t<vartrace>" . wddx_serialize_value($vars, "Variables") . "</vartrace>\n";
}
$err .= "</errorentry>\n\n";

// for testing
echo $err;

// save to the error log, and e-mail me if there is a critical user error
error_log($err, 3, $_mylibapp['dirs']['data']."error.log");
if ($errno == E_USER_ERROR) {
mail("minthanthtoo1994@gmail.com", "Critical User Error", $err); }
}

?>