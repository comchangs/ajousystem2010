<?php

require_once "setting.php";

$date = date('Y-m-d H:i:s');

$db = new mysqli('localhost', $dbuser, $dbpasswd, $dbname);
if (mysqli_connect_errno())
{
	echo 'Error: Could not connect to database. Please try again later.';
	exit;
}


if(isset($_GET['first'])) {
	$first = $_GET['first'];
} else {
	$first = "0";
}


if(isset($_GET['last'])) {
	$last = $_GET['last'];
} else {
	$last = "30";
}



$query = "select * from resource where section2 > 0 LIMIT $first , $last"; //柠府(SQL)巩 累己
$result_contents = mysqli_query($db, $query); //孽府 角青

while($row = mysqli_fetch_array($result_contents)) {
	echo "<item>";
	echo "<num>".$row['num']."</num><name>".$row['name']."</name><level>".$row['level']."</level><temp_max>".$row['temp_max']."</temp_max><temp_min>".$row['temp_min']."</temp_min><humi_max>".$row['humi_max']."</humi_max><humi_min>".$row['humi_min']."</humi_min><illu>".$row['illu']."</illu><section1>".$row['section1']."</section1><section2>".$row['section2']."</section2><section3>".$row['section3']."</section3><section4>".$row['section4']."</section4>";
	echo "</item>";
}
?>