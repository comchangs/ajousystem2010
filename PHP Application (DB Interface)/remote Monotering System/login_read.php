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


$query = "select * from login where section = $section"; //����(SQL)�� �ۼ�
$result_contents = mysqli_query($db, $query); //���� ����

while($row = mysqli_fetch_array($result_contents)) {
	echo "<item>";
	echo "<user>".$row['user']."</user><level>".$row['level']."</level>";
	echo "</item>";
}
?>