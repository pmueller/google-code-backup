#!/usr/local/bin/php
<?php

/*
Preston Mueller
4-4-11
This script will delete the given user from the database
*/
require 'header.php';

if(!isset($_SESSION['username'])) {
  die('gtfo');
}

require 'dbinfo.php';

$c = connect();

$q = sprintf("delete users where username='%s'", $_SESSION['username']);

$cmd = ociparse($c, $q);

ociexecute($cmd);

disconnect($c);

unset($_SESSION['username']); 
 
header('Location: index.php'); 

?>
