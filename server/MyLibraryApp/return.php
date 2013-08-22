<?php

if(!isset($_mylibapp_returncode)){
return;
}else{
	include("config/returnCodes.php");
$count=count($_mylibapp_returncode);
for ($i = 0; $i <= count; $i++) {
	if($_mylibapp_returncode[i]=='0')continue;
	echo "<error>\n".
		"<code>".$_mylibapp_returncode[i]."\n".
		"</code>\n".
		"<string>".$_mylibapp_returndodes[$_mylibapp_returncode[i]].
		"</string>\n".
		"</error>";
}
}
?>