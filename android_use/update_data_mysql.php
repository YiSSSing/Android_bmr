<?php
$ip = gethostbyname(gethostname()) ;
//set connect to database
$link = mysqli_connect($ip , "root" , "Yisinglabuse" , "android_partice" ) or die('Error with MySQL connection') ;
$link -> set_charset("UTF8") ;

$psearch = $_POST['psearch'] ;
$pname = $_POST['pname'] ;
$psex = $_POST['psex'] ;
$pweight = $_POST['pweight'] ;
$pheight = $_POST['pheight'] ;
$page = $_POST['page'] ;


$result = $link -> query("UPDATE bmr_partice set name   = '$pname'   ,
                                                 sex    = '$psex'    ,
                                                 weight = '$pweight' ,	
												 height = '$pheight' ,
												 age    = '$page'      where name = '$psearch' ") ;


$link->close() ;

?>
