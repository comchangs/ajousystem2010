<?php

require_once "setting.php";

$date = date('Y-m-d H:i:s');

$db = new mysqli('localhost', $dbuser, $dbpasswd, $dbname);
if (mysqli_connect_errno())
{
	echo 'Error: Could not connect to database. Please try again later.';
	exit;
}

//로그인페이지에서 정상적인 값이 왔는지 확인
if(isset($_POST['userId']))	$userId = $_POST['userId'];
if(isset($_POST['userPW']))	$userPW = $_POST['userPW'];


//데이터베이스 테이블에서 데이터 추출
$query = "select * from root_member where id = '$userId'";
$result = mysqli_query($db, $query);
$user_info = mysqli_fetch_array($result);

//ID가 없는 경우 다시 로그인 페이지로 이동
if(!$user_info) {
	redirect("index.php");
}

//Password 확인
if(md5($userPW) == $user_info[password])
{
	session_start();

	$_SESSION['root'] = "root";
	echo $_SESSION['root'];
	//로그인 후 정상페이지로 이동
	redirect("status_list.php");
}
else
{
	//Password가 맞지 않을 경우 다시 로그인 페이지로 이동
	redirect("index.php");
}


//데이터베이스 정보 소명 및 연결 종료
mysqli_free_result($result);
mysqli_close($db);


?>