<?php

require_once "setting.php";

$date = date('Y-m-d H:i:s');

$db = new mysqli('localhost', $dbuser, $dbpasswd, $dbname);
if (mysqli_connect_errno())
{
	echo 'Error: Could not connect to database. Please try again later.';
	exit;
}


if(isset($_GET['num'])) {
	$num = $_GET['num'];
} else {
	$num = "0";
}


if(isset($_GET['name'])) {
	$name = $_GET['name'];
} else {
	$name = "0";
}

if(isset($_GET['level'])) {
	$level = $_GET['level'];
} else {
	$level = "0";
}

if(isset($_GET['temp_max'])) {
	$temp_max = $_GET['temp_max'];
} else {
	$temp_max = "0";
}

if(isset($_GET['temp_min'])) {
	$temp_min = $_GET['temp_min'];
} else {
	$temp_min = "0";
}

if(isset($_GET['humi_max'])) {
	$humi_max = $_GET['humi_max'];
} else {
	$humi_max = "0";
}

if(isset($_GET['humi_min'])) {
	$humi_min = $_GET['humi_min'];
} else {
	$humi_min = "0";
}

if(isset($_GET['illu'])) {
	$illu = $_GET['illu'];
} else {
	$illu = "0";
}


if(isset($_GET['section1'])) {
	$section1 = $_GET['section1'];
} else {
	$section1 = "0";
}

if(isset($_GET['section2'])) {
	$section2 = $_GET['section2'];
} else {
	$section2 = "0";
}

if(isset($_GET['section3'])) {
	$section3 = $_GET['section3'];
} else {
	$section3 = "0";
}

if(isset($_GET['section4'])) {
	$section4 = $_GET['section4'];
} else {
	$section4 = "0";
}





$query = "select * from resource where num = $num"; //柠府(SQL)巩 累己
$result_contents = mysqli_query($db, $query); //孽府 角青
$row = mysqli_fetch_array($result_contents);

if(!(isset($row))) {
	$query = "insert into resource (num, name, level, temp_max, temp_min, humi_max, humi_min, illu, section1, section2, section3, section4) values ($num, '$name', $level, $temp_max, $temp_min, $humi_max, $humi_min, $illu, $section1, $section2, $section3, $section4)";
	$result = $db->query($query);
} else {
	$query = "update resource set num = $num, name = '$name', level = $level, temp_max = $temp_max, temp_min = $temp_min, humi_max = $humi_max, humi_min = $humi_min, illu = $illu, section1 = $section1, section2 = $section2, section3 = $section3, section4 = $section4 where num = $num";
	$result = $db->query($query);
}
?>