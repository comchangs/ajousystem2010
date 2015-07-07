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
	$last = "10";
}


$query = "select * from log where section = 2 order by num DESC LIMIT $first , $last"; //柠府(SQL)巩 累己
$result_contents = mysqli_query($db, $query); //孽府 角青

while($row = mysqli_fetch_array($result_contents)) {
	echo "<item>";
	echo "<num>".$row['num']."</num><user>".$row['user']."</user><temp>".$row['temp']."</temp><humi>".$row['humi']."</humi><illu>".$row['illu']."</illu><event>".$row['event']."</event><section>".$row['section']."</section><secure>".$row['secure']."</secure><date>".$row['date']."</date>";
	echo "</item>";
}
?>