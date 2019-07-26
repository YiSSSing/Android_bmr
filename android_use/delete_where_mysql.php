<?php
$ip = gethostbyname(gethostname()) ;
//set connect to database
$link = mysqli_connect($ip , "root" , "Yisinglabuse" , "android_partice" ) or die('Error with MySQL connection') ;
$link -> set_charset("UTF8") ;

$pname = $_POST['pname'] ;
$psex = $_POST['psex'] ;
$page = $_POST['page'] ;


$result = $link -> query("DELETE FROM bmr_partice where name = '$pname' and sex = '$psex' and age = '$page'") ;

$link->close() ;

?>
