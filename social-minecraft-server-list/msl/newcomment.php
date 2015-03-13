#!/usr/local/bin/php
<?php

/*
3-30-11
Preston Mueller

This takes the comment data from the serverinfo page
and makes the new comment in the databse using post
*/

session_start();

$server_id = $_POST['server_id'];

require 'dbinfo.php';

$c = connect();

//another hack to change to the next comment id

$cmd = ociparse($c, "select max(comment_id) as ID from comments");
ociexecute($cmd);
if (ocifetch($cmd) ) {
$key = OCIResult($cmd, 'ID') + 1;
}
else {
  die('failed to get key');
}


$q = sprintf("insert into comments values(%d, %d, '%s', '%s', %d, null)", $server_id, $key, $_SESSION['username'], $_POST['comment_text'], 0);

$cmd = ociparse($c, $q);

if(!ociexecute($cmd)) {
  die('error inserting comment into databse');
}

disconnect($c);

header("Location: serverinfo.php?id=" . $server_id);

?>
