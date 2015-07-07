<?php

require_once "setting.php";

$date = date('Y-m-d H:i:s');

$db = new mysqli('localhost', $dbuser, $dbpasswd, $dbname);
if (mysqli_connect_errno())
{
	echo 'Error: Could not connect to database. Please try again later.';
	exit;
}


if(isset($_GET['section'])) {
	$section = $_GET['section'];
} else {
	$section = "0";
}



$query = "select * from status"; //柠府(SQL)巩 累己
	$result_contents = mysqli_query($db, $query); //孽府 角青

if($section == "1") {
	while($row = mysqli_fetch_array($result_contents)) {
		echo "<item>";
		echo "<temp>".$row['sec1_temp']."</temp><humi>".$row['sec1_humi']."</humi>";
		echo "</item>";
	}
}

if($section == "2") {
	while($row = mysqli_fetch_array($result_contents)) {
		echo "<item>";
		echo "<temp>".$row['sec2_temp']."</temp><humi>".$row['sec2_humi']."</humi>";
		echo "</item>";
	}
}

if($section == "3") {
	while($row = mysqli_fetch_array($result_contents)) {
		echo "<item>";
		echo "<temp>".$row['sec3_temp']."</temp><humi>".$row['sec3_humi']."</humi>";
		echo "</item>";
	}
}

if($section == "4") {
	while($row = mysqli_fetch_array($result_contents)) {
		echo "<item>";
		echo "<temp>".$row['sec4_temp']."</temp><humi>".$row['sec4_humi']."</humi>";
		echo "</item>";
	}
}
?>