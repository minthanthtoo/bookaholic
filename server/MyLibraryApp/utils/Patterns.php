<?php
/*
$requestData="1=hello&&2=minthant&&3=htoo";
$query="SELECT * FROM ayfl_store.`authors` where authors.Name=%[*d1]AND%[d1].%[*d2;]%[d1]";
$xmlPattern="
<Main>%[l1]
<L1>%[l2]
<L2>%[x3]</L2>%[l4]
</L1>%[l2]
</Main>";
$requestTable=array(
	0=>array('r0c0','r0c1','r0c2','r0c3','HELLO'=>"hello!Min THant Htoo r0"),
	1=>array('r1c0','r1c1','r1c2','r1c3','HELLO'=>"hello!Min THant Htoo r1"),
	2=>array('r2c0','r2c1','r2c2','r2c3','HELLO'=>"hello!Min THant Htoo r2")
);
//echo 'translateed pattern'.translate_xmlpattern($xmlPattern,$requestTable);
//echo "\n".$xmlPattern;
*/

/* fetch values 
if ($result=mysqli_query($link,$query)) {
	$table=array();
	while($row=mysqli_fetch_row($result)){
	$table[]=$row;
	}
	$xmlOutput=translate_xmlCapsule($xmlPattern,$table);
	//echo $xmlOutput;
}
*/

function dataQueryToArray($data){
global $_mylibapp;
$DEBUG = $_mylibapp['debug']['utils']['Patterns'];

	$temp = explode ( '&&', $data );
	$data = array ();
	foreach ( $temp as $t ) {
		$t = explode ( '=', $t );
		if (! isset ( $t [1] ))
			$data [] = $t [0];
		else if (! isset ( $t [0] ))
			$data [] = $t [1];
		else
			$data [$t [0]] = "$t[1]";
	}
	if($DEBUG){
	echo "\nIn dataQueryToArray(),\$data=";
	print_r($data);
	}
	return $data;
}

/*** START: direct lateral replacement ***/
// direct laterals : %[d_] , %[f_]
function translate_direct_literals($stmt, $data,$filesPosted=null) {
	global $_mylibapp;
	$DEBUG = $_mylibapp ['debug'] ['utils'] ['Patterns'];
	
	if($DEBUG){
	echo "\nIn translate_direct_literal(),";
	echo "\n\$stmt=$stmt";
	echo "\ndata= ";
	print_r($data);
	echo "\n";
	echo "\nfilesPosted=";
	print_r($filesPosted);
	echo "\n";
	}
	
	$is_turn = false;
	$is_turn = preg_match_all ( '/%\[([*]?[df][^\]]*)\]/',$stmt,$matches)
	;// exclude literals %[l_],%[x_]
	//print_r($matches);
	foreach ( $matches [1] as $mkey => &$m ) {
		preg_match_all ( "/[*]?([df])([\d]+|\".*\")/", $m, $values );
		foreach ( $values [2] as $vkey => &$v ) {
			$v = str_replace ( '"', '', $v );
			switch ($values [1] [$vkey]) {
				case 'd' :
					$matches [2] [$mkey] [$vkey] = isset ( $data [$v] ) ? $data [$v] : (((strpos ( $values [0] [$vkey], "*" ) === false)) ? "" : _load_error ( "Incificient request data" ));
 					break;
				case 'f' :
				if($filesPosted) $matches [2] [$mkey] [$vkey] = isset ( $filesPosted [$v] ) ? $filesPosted [$v] : (((strpos ( $values [0] [$vkey], "*" ) === false)) ? "" : _load_error ( "Incificient request data" )); //for further development
					break;
				case 'x' : // will never come here
				 /* unnecessary for now. see translate_result_literals*/
					break;
			
			}
		
		}
		$matches [3] [$mkey] = str_replace ( $values [0], $matches [2] [$mkey], $matches [1] [$mkey] );
	
		//$matches[3][$mkey]=execute_expression($matches[3][$mkey]);
	
	//echo "\nvalues=";
	//print_r($values);
	}
	if($DEBUG){
	echo "\nmatches=";
	print_r($matches);
	}
	if(isset($matches[3]))
	$stmt = str_replace ( $matches [0], $matches [3], $stmt );
	if ($DEBUG)
		echo "\nstmt=$stmt";
	return $stmt;
}
/*** END: direct literal replacement ***/

/*** START : result literal replacement ***/
// result literals: %[x_] ( they are elements of data table(row�~able) )
// loop literals: %[l_]
function translate_result_literals( $xmlpattern , $datatable ) {
	
	global $_mylibapp;
	$DEBUG = $_mylibapp ['debug'] ['utils'] ['Patterns'];
	
	
	if ($DEBUG) {
		echo "\nIn translate_result_literal(),xmlpattern=$xmlpattern";
		echo "\ndatatable=";
		print_r ( $datatable );
	}
	//print_r($datatable); 
	preg_match_all ( '/%\[([*]?x[^\]]*)\]|(%\[l\d+[^\]]*\])/', $xmlpattern, $matches ); // only literals:%[x_] ; exclude literals: %[d_],%[f_]
	$temp = array ();
	foreach ( $matches [2] as &$loop ) {
		if ($loop != NULL)
			$temp [] = $loop;
	}
	
	if($DEBUG){
	echo "\n\$matches=";
	print_r($matches);
	print_r($temp);
	}
	$loopstack = array ();
	$level = 0;
	$storeStack = array ();
	reset ( $temp );
	while ( $loop = current ( $temp ) ) {
		
		if($DEBUG)
		echo "\nloopitem:".$loop;
		

		//echo '$pos1:'.$pos1;
		//echo '$pos2:'.$pos2;
		//echo "\ngrepstring:";//print_r($grepString);
		//echo "\nxmlPattern:\n".$xmlpattern;
		

		if (isset ( $loopstack [$loop] ) == true) { //echo "\nnow loop!";
			if ((($looplevel = $loopstack [$loop]) == $level))
				$level --;
			else { //_load_error();
				//echo "\nTried to relevel";
				end ( $storeStack );
				while ( ($end = end ( $storeStack )) != $loop ) {
					//echo "\nend=$end";	
					

					$loopstrlength = mb_strlen ( $loop );
					$endstrlength = mb_strlen ( $end );
					$pos1 = mb_strpos ( $xmlpattern, $end );
					$pos2 = mb_strpos ( $xmlpattern, $loop, $pos1 + $endstrlength );
					$find = mb_strcut ( $xmlpattern, $pos1, $pos2 - $pos1 );
					$replace = $find . $end;
					$xmlpattern = str_replace ( $find, $replace, $xmlpattern );
					$grepString [$end] [0] = $replace;
					$grepString [$end] [1] = mb_strcut ( $xmlpattern, $pos1 + $endstrlength, $pos2 - $pos1 - $endstrlength );
					$grepString [$end] [2] = '';
					$level --;
					array_pop ( $storeStack );
				}
			}
			
			$loopstrlength = mb_strlen ( $loop );
			$pos1 = mb_strpos ( $xmlpattern, $loop );
			$pos2 = mb_strpos ( $xmlpattern, $loop, $pos1 + $loopstrlength );
			$grepString [$loop] [0] = mb_strcut ( $xmlpattern, $pos1, $pos2 - $pos1 + $loopstrlength );
			$grepString [$loop] [1] = mb_strcut ( $xmlpattern, $pos1 + $loopstrlength, $pos2 - $pos1 - $loopstrlength );
			$grepString [$loop] [2] = '';
			
			array_pop ( $storeStack );
		} else {
			$level ++;
			$loopstack [$loop] = $level;
			$storeStack [] = $loop;
		}
		if (! isset ( $temp [key ( $temp ) + 1] )) {
			end ( $storeStack );
			while ( $item = current ( $storeStack ) ) {
				$temp [] = $item;
				$xmlpattern .= $item;
				prev ( $storeStack );
			}
		}
		next ( $temp );
	
	}
	$grepString ['target'] [1] = &$xmlpattern;
	$grepString ['target'] [0] = $xmlpattern;
	$count = 0;
	$_prevLevel = 0;
	$_tops = array ();
	$_tops [0] = 'target';
	//echo "\n\$loopstack";
	//print_r($loopstack);
	foreach ( $loopstack as $loop => $level ) {
		if ($level > $_prevLevel) {
			$_prevLevel = $level - 1;
		} else if ($level < $_prevLevel) {
			$_prevLevel = $level - 1;
		} else {
		}
		$loopparent [$loop] [$level] = $_tops [$_prevLevel];
		$_tops [$level] = $loop;
	}
	$loopstack = array_reverse ( $loopstack );
	if($DEBUG){
	echo "\n\$loopstack=";
	print_r($loopstack);
	echo "\n\$loopparent=";	 
	print_r($loopparent);
	}
	

	foreach ( $loopstack as $loop => $level ) {
		
		/* Now find the loop limit.The starting and ending limit lies between start and end of
the data array.*/
		preg_match ( '/%\[l\d+=(\d+)\]|%\[l\d+=\d+-(\d+)/', $loop, $loopmatch ); // e.g. %[l=3] , %[l=3-8]
		$loopstart = 0;
		$loopend = count ( $datatable ) - 1;
		if (isset ( $loopmatch [1] )) {
			$loopstart = $loopmatch [1];
			if (isset ( $loopmatch [2] )) {
				$loopend = ($loopend > ( int ) $loopmatch [2]) ? ( int ) $loopmatch [2] : $loopend;
			}
		}
		
		/***************************************
		 *Now replace the values in the place of patterns for each row.
		 *Number of rows is within the limit of loop start and end.
		 **************/
		for($looptime = $loopstart; $looptime <= $loopend; $looptime ++) {
			//echo "\nReplacing each row...";
			

			foreach ( $matches [1] as $mkey => &$m ) {
				//echo "\nReplacing each expression...";
				preg_match_all ( "/[*]?(x)([\d]+|\".*\")/", $m, $values );
				//echo "\n".'$values(Before replace)';
				//print_r($values);
				foreach ( $values [2] as $vkey => &$v ) {
					$v = str_replace ( '"', '', $v );
					switch ($values [1] [$vkey]) {
						case 'd' : // will never come here
						// see translate_direct_literals
							break;
						case 'f' : // will never come here
						// see translate_direct_laNterals
							break;
						case 'x' :
							$matches [2] [$mkey] [$vkey] = isset ( $datatable [$looptime] [$v] ) ? $datatable [$looptime] [$v] : (((strpos ( $values [0] [$vkey], "*" ) === false)) ? "" : _load_error ( "Incificient request data" ));
							break;
					
					}
				}
				$matches [3] [$mkey] = str_replace ( $values [0], $matches [2] [$mkey], $matches [1] [$mkey] );
			
		//echo '$values';
			//print_r($values);
			

			}
			//echo "\nExpression replacement....";
			//Now the values of $matches have been defined for each row of $datatable
			//These $matches will replace the patterns in the $grepString for each row and the $grepString will append
			$grepString [$loop] [2] .= str_replace ( $matches [0], $matches [3], $grepString [$loop] [1] );
		
		//echo "\n\$matches(After each row)";
		//print_r($matches);
		}
		//echo "\n$loop";
		//print_r($grepString);
		//echo "\nReplacing to up-level...";
		//$grepString will now append for one $loop
		$grepString [$loopparent [$loop] [$level]] [1] = str_replace ( $grepString [$loop] [0], $grepString [$loop] [2], $grepString [$loopparent [$loop] [$level]] [1] );
		if ($level != 0)
			array_shift ( $loopstack );
	
		//echo "$level";
	//echo "\nStack_POP";
	//print_r($level);
	//print_r($loopstack);
	//echo "\ngrepString=";
	//print_r($grepString);
	

	}
	
	//echo '$matches';
	//print_r($matches);
	//print_r($grepString);
	//echo "\n\$xmlpattern".$xmlpattern;
	//echo "\n\$matches";//print_r($matches);
	//echo "\n\$loopstack";//print_r($loopstack);
	//print_r($grepString);
	//echo "\n\$loop".$loop;
	//var_dump($loopparent);
	if ($DEBUG){
	echo '\n$matches = ';
	print_r($matches);
		echo "\n<br>In translate_result_literals(),";
		echo "\nxmlpattern=$xmlpattern";
		}
		
	return $xmlpattern;
}

function _load_error($errorString) {
	$hello;
	die( "<BR/>" . $errorString);
}
?>