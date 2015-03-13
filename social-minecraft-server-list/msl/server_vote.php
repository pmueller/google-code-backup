#!/usr/local/bin/php
<?php

/*
Preston Mueller
3-9-11
This script will be called through ajax to vote up or down on a 
server. The correct error checking will be made as well.
*/
require 'header.php';

if( !isset( $_SESSION['username'] ) ) {
  header("Location: signin.php");  
}


if( !isset( $_GET['id'])  || !isset( $_GET['vote'] ) ) {
  die('gotta call this with an id, and a vote that are right');
}


if(strcmp($_GET['vote'], "up")==0 ) {
$v = 1;
} else if (strcmp($_GET['vote'], "down")==0 )  {
$v = -1;
} else {
  die('bad vote value');
}

require 'dbinfo.php';

$c = connect();

$q = sprintf("select username from server_votes where username='%s' and submission_id=%d", $_SESSION['username'], $_GET['id']);

$cmd = ociparse($c, $q);

ociexecute($cmd);

if(ocifetch($cmd)) {
  $q = sprintf("update server_votes set vote=%d where username='%s' and submission_id=%d", $v, $_SESSION['username'], $_GET['id']);

} else {
  $q = sprintf("insert into server_votes values('%s', %d, %d)", $_SESSION['username'], $_GET['id'],  $v);
}

$cmd2 = ociparse($c, $q);
ociexecute($cmd2);

disconnect($c);

header("Location: serverinfo.php?id=". $_GET['id']);



?>
