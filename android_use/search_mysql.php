<?php
$ip = gethostbyname(gethostname()) ;
//set connect to database
$link = mysqli_connect($ip , "root" , "Yisinglabuse" , "android_partice" ) ;
$link -> set_charset("UTF8") ;

$pname = $_POST['pname'] ;

$result = $link -> query("SELECT * FROM bmr_partice where name = '$pname'") ;


//put returns into array
while( $row = $result->fetch_assoc() ) {
  $output[] = $row ;
}


//transform datas to Json and print on web
//print( json_encode($output,JSON_UNESCAPED_UNICODE) ) ;
echo json_encode($output) ;
$link->close() ;

?>

