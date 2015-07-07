<?php

require_once "setting.php";

$date = date('Y-m-d H:i:s');

$db = new mysqli('localhost', $dbuser, $dbpasswd, $dbname);
if (mysqli_connect_errno())
{
	echo 'Error: Could not connect to database. Please try again later.';
	exit;
}


if(isset($_GET['user'])) {
	$user = $_GET['user'];
} else {
	$user = "";
}


if(isset($_GET['temp'])) {
	$temp = $_GET['temp'];
} else {
	$temp = "";
}

if(isset($_GET['humi'])) {
	$humi = $_GET['humi'];
} else {
	$humi = "";
}

if(isset($_GET['illu'])) {
	$illu = $_GET['illu'];
} else {
	$illu = "";
}

if(isset($_GET['event'])) {
	$event = $_GET['event'];
} else {
	$event = "";
}

if(isset($_GET['section'])) {
	$section = $_GET['section'];
} else {
	$section = "";
}

if(isset($_GET['secure'])) {
	$secure = $_GET['secure'];
} else {
	$secure = "";
}

$query = "insert into log (user, temp, humi, illu, event, section, secure, date) values ($user, $temp, $humi, $illu, '$event', $section, $secure, '$date')";
$result = $db->query($query);
?>