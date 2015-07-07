<?
require_once "setting.php";

session_start();

$_SESSION['root'] = "no";

redirect("index.php");

//session_destroy();
?>