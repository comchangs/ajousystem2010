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
	$user = "0";
}


if(isset($_GET['level'])) {
	$level = $_GET['level'];
} else {
	$level = "0";
}


if(isset($_GET['section'])) {
	$section = $_GET['section'];
} else {
	$section = "0";
}


$query = "select * from login where user = $user"; //柠府(SQL)巩 累己
$result_contents = mysqli_query($db, $query); //孽府 角青
$row = mysqli_fetch_array($result_contents);

if(!(isset($row))) {
	$query = "insert into login (user, level, section) values ($user, $level, $section)";
	$result = $db->query($query);
} else {
	$query = "update login set user = $user, level = $level, section = $section";
	$result = $db->query($query);
}
?>