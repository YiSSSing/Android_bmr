
<?php
$ip = gethostbyname(gethostname()) ;
//set connect to database
$link = mysqli_connect($ip , "root" , "Yisinglabuse" , "android_partice" ) ;
$link -> set_charset("UTF8") ;

$result = $link -> query("SELECT * FROM icon_partice") ;


//put returns into array
while( $row = $result->fetch_assoc() ) {
  $output[] = $row ;
}


//transform datas to Json and print on web
//print( json_encode($output,JSON_UNESCAPED_UNICODE) ) ;
echo json_encode($output) ;
$link->close() ;

?>

