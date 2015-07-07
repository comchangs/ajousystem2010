<?php

require_once "setting.php";

$date = date('Y-m-d H:i:s');

$db = new mysqli('localhost', $dbuser, $dbpasswd, $dbname);
if (mysqli_connect_errno())
{
	echo 'Error: Could not connect to database. Please try again later.';
	exit;
}

if(isset($_GET['sec1_temp'])) {
	$sec1_temp = $_GET['sec1_temp'];
} else {
	$sec1_temp = "0";
}
if(isset($_GET['sec1_humi'])) {
	$sec1_humi = $_GET['sec1_humi'];
} else {
	$sec1_humi = "0";
}
if(isset($_GET['sec1_illu'])) {
	$sec1_illu = $_GET['sec1_illu'];
} else {
	$sec1_illu = "0";
}
if(isset($_GET['sec1_secure'])) {
	$sec1_secure = $_GET['sec1_secure'];
} else {
	$sec1_secure = "0";
}
if(isset($_GET['sec1_on'])) {
	$sec1_on = $_GET['sec1_on'];
} else {
	$sec1_on = "0";
}

if(isset($_GET['sec2_temp'])) {
	$sec2_temp = $_GET['sec2_temp'];
} else {
	$sec2_temp = "0";
}
if(isset($_GET['sec2_humi'])) {
	$sec2_humi = $_GET['sec2_humi'];
} else {
	$sec2_humi = "0";
}
if(isset($_GET['sec2_illu'])) {
	$sec2_illu = $_GET['sec2_illu'];
} else {
	$sec2_illu = "0";
}
if(isset($_GET['sec2_secure'])) {
	$sec2_secure = $_GET['sec2_secure'];
} else {
	$sec2_secure = "0";
}
if(isset($_GET['sec2_on'])) {
	$sec2_on = $_GET['sec2_on'];
} else {
	$sec2_on = "0";
}

if(isset($_GET['sec3_temp'])) {
	$sec3_temp = $_GET['sec3_temp'];
} else {
	$sec3_temp = "0";
}
if(isset($_GET['sec3_humi'])) {
	$sec3_humi = $_GET['sec3_humi'];
} else {
	$sec3_humi = "0";
}
if(isset($_GET['sec3_illu'])) {
	$sec3_illu = $_GET['sec3_illu'];
} else {
	$sec3_illu = "0";
}
if(isset($_GET['sec3_secure'])) {
	$sec3_secure = $_GET['sec3_secure'];
} else {
	$sec3_secure = "0";
}
if(isset($_GET['sec3_on'])) {
	$sec3_on = $_GET['sec3_on'];
} else {
	$sec3_on = "0";
}

if(isset($_GET['sec4_temp'])) {
	$sec4_temp = $_GET['sec4_temp'];
} else {
	$sec4_temp = "0";
}
if(isset($_GET['sec4_humi'])) {
	$sec4_humi = $_GET['sec4_humi'];
} else {
	$sec4_humi = "0";
}
if(isset($_GET['sec4_illu'])) {
	$sec4_illu = $_GET['sec4_illu'];
} else {
	$sec4_illu = "0";
}
if(isset($_GET['sec4_secure'])) {
	$sec4_secure = $_GET['sec4_secure'];
} else {
	$sec4_secure = "0";
}
if(isset($_GET['sec4_on'])) {
	$sec4_on = $_GET['sec4_on'];
} else {
	$sec4_on = "0";
}











$query = "update status set sec1_temp = $sec1_temp, sec1_humi = $sec1_humi, sec1_illu = $sec1_illu, sec1_secure = $sec1_secure, sec1_on = $sec1_on, sec2_temp = $sec2_temp, sec2_humi = $sec2_humi, sec2_illu = $sec2_illu, sec2_secure = $sec2_secure, sec2_on = $sec2_on, sec3_temp = $sec3_temp, sec3_humi = $sec3_humi, sec3_illu = $sec3_illu, sec3_secure = $sec3_secure, sec3_on = $sec3_on, sec4_temp = $sec4_temp, sec4_humi = $sec4_humi, sec4_illu = $sec4_illu, sec4_secure = $sec4_secure, sec4_on = $sec4_on where flag = 1";
$result = $db->query($query);
?>