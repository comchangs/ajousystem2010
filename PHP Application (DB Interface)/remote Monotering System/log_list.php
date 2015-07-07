<?

require_once "setting.php";

$date = date('Y-m-d H:i:s');
session_start();
if($_SESSION['root'] != "root") {
	redirect("index.php");
}

$db = new mysqli('localhost', $dbuser, $dbpasswd, $dbname);
if (mysqli_connect_errno())
{
	echo 'Error: Could not connect to database. Please try again later.';
	exit;
}

if(isset($_GET['page'])) {
	$page = $_GET['page'];
} else {
	$page = "1";
}

if(isset($_GET['first'])) {
	$first = $_GET['first'];
} else {
	$first = "0";
}


if(isset($_GET['last'])) {
	$last = $_GET['last'];
} else {
	$last = "19";
}
settype($first, "integer");
settype($last,"integer");

$first += 20*($page-1);
$last;

$query_row = "select * from log order by num DESC";
$query = "select * from log order by num DESC LIMIT $first , $last"; //퀴리(SQL)문 작성
$result_row = mysqli_query($db, $query_row); //쿼리 실행
$result_contents = mysqli_query($db, $query); //쿼리 실행
$row_count = mysqli_num_rows($result_row);

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>MediSto - Remote Monitoring System</title>
<meta http-equiv="Content-Type"
content="text/html; charset=iso-8859-1" />
<meta name="Robots" content="NOINDEX" />
<meta http-equiv="PRAGMA" content="NO-CACHE" /><!--
  Web Site: www.ssi-developer.net
  Comments: Copyright 2003 www.ssi-developer.net
  Licence:  Creative Commons - Non-commercial Share-Alike
-->
<link rel="stylesheet" type="text/css"
href="2c-lc-static-layout.css" />
<link rel="stylesheet" type="text/css"
href="2c-lc-static-presentation.css" />
</head>
<body>
<!-- left column -->
<div id="lh-col">
<img src="images/medisto.jpg" width="180" height="69" alt="Logo" />
<br /><br /><br /><br /><br />

<a href="status_list.php"><img src="images/status.jpg" width="180" height="21" alt="Status" border="0" /></a><div id="blank"></div>
<a href="log_list.php"><img src="images/eventlog.jpg" width="180" height="24" alt="Event Log" border="0" /></a><div id="blank"></div>
<a href="resource_list.php"><img src="images/inventory.jpg" width="180" height="22" alt="Inventory" border="0" /></a><br /><br /><br /><br />
<a href="logout_process.php"><img src="images/logout.jpg" width="180" height="21" alt="Logout" border="0" /></a><br /><br />

<br />
<br /><br />

<img src="images/sigma.jpg" width="180" height="48" alt="정문창, 국용상, 이승진, 이재훈" />
</div>
<!-- end of left column -->
<!-- right column -->
<div id="rh-col">
<h3 align="center">Event Log</h3>
<!-- 내용 -->
<table>
	<tr>
		<td><b>No</b></td>
		<td><b>User</b></td>
		<td><b>Temp</b></td>
		<td><b>Humi</b></td>
		<td><b>Illu</b></td>
		<td><b>Event</b></td>
		<td><b>Section</b></td>
		<td><b>Secure</b></td>
		<td><b>Date</b></td>
	</tr>
	



<?

while($row = mysqli_fetch_array($result_contents)) {
	echo "


	<tr>
		<td>".$row['num']."</td>
		<td>".$row['user']."</td>
		<td>".$row['temp']."</td>
		<td>".$row['humi']."</td>
		<td>".($row['illu']?"밝음":"어두움")."</td>
		<td>".$row['event']."</td>
		<td>".$row['section']."</td>
		<td>".($row['secure']?"침입":"정상")."</td>
		<td>".$row['date']."</td>
	</tr>";
}

?>
</table>
<?
if($page > 1) { echo "<a href=log_list.php?page=".($page-1).">이전</a>&nbsp;&nbsp;&nbsp;"; }
if($row_count / (20 * $page) > 1) { echo "<a href=log_list.php?page=".($page+1).">다음</a>"; }
?>
</div>
<!-- end of right column -->

</body>
</html>
