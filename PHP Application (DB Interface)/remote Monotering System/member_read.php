<?php

require_once "setting.php";

$date = date('Y-m-d H:i:s');

$db = new mysqli('localhost', $dbuser, $dbpasswd, $dbname);
if (mysqli_connect_errno())
{
	echo 'Error: Could not connect to database. Please try again later.';
	exit;
}

$query = "select * from member"; //柠府(SQL)巩 累己
$result_contents = mysqli_query($db, $query); //孽府 角青

while($row = mysqli_fetch_array($result_contents)) {
	echo "<item>";
	echo "<user>".$row['user']."</user><password>".$row['password']."</password><level>".$row['level']."</level>";
	echo "</item>";
}
?>