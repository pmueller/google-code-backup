#!/usr/local/bin/php
<?php

/*
Preston Mueller
4-4-11
This script deletes the comment from the database
*/

require 'header.php';

if(!isset($_SESSION['username']) || !isset($_POST['id'])) {
  die('gtfo');
}

require 'dbinfo.php';

$c = connect();

$q = sprintf("delete comments where comment_id='%d'", $_POST['id']);

$cmd = ociparse($c, $q);

ociexecute($cmd);

disconnect($c);

header('Location: serverinfo.php?id='.$_POST['backto']);

?>
