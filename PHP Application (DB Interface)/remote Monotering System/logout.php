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

$query = "delete from login where user = $user";
$result = $db->query($query);
?>