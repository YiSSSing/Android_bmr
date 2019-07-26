<?php
$ip = gethostbyname(gethostname()) ;

$link = mysqli_connect($ip , "root" , "Yisinglabuse" , "android_partice" ) or die('Error with MySQL connection') ;
$link -> set_charset("UTF8") ;


$pname = $_POST['pname'] ;
$psex = $_POST['psex'] ;
$pweight = $_POST['pweight'] ;
$pheight = $_POST['pheight'] ;
$page = $_POST['page'] ;


$result = $link -> query("INSERT INTO bmr_partice (name,sex,weight,height,age) VALUES ('$pname','$psex','$pweight','$pheight','$page')") ;


$link->close() ;

?>

