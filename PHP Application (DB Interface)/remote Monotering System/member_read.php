<?php

require_once "setting.php";

$date = date('Y-m-d H:i:s');

$db = new mysqli('localhost', $dbuser, $dbpasswd, $dbname);
if (mysqli_connect_errno())
{
	echo 'Error: Could not connect to database. Please try again later.';
	exit;
}

$query = "select * from member"; //����(SQL)�� �ۼ�
$result_contents = mysqli_query($db, $query); //���� ����

while($row = mysqli_fetch_array($result_contents)) {
	echo "<item>";
	echo "<user>".$row['user']."</user><password>".$row['password']."</password><level>".$row['level']."</level>";
	echo "</item>";
}
?>