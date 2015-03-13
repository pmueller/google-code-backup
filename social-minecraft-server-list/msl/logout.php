#!/usr/local/bin/php
<?php
/*
Preston Mueller
3-9-11
This script logs the current user out by ending their session
and then going back to the index
*/
session_start();

unset($_SESSION['username']);

header('Location: index.php');

?>