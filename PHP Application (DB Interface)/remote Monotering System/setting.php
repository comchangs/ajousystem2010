<?
$dbuser = 'sysprog';
$dbpasswd = 'sysprogking';
$dbname = 'sysprog';

//자동 링크 함수
function redirect($url)
{
	header("Location: $url");
	exit();
}
?>