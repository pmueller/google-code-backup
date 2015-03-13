#!/usr/local/bin/php
<?php
/*
Preston Mueller
3-9-11
This script will display the submission page of any server
that is passed in via GET
*/


require 'header.php';

//display info for the server with id in $_GET['id']

if( !isset($_GET['id']) ) {
   echo 'error, need to have a server id';
   die();
}

require 'dbinfo.php';

$c = connect();

$q = sprintf("select submission_id, username, server_title, description, server_url, server_port, server_type, score, vote from server natural left outer join (select submission_id, vote from server_votes where username='%s') where submission_id=%s", $_SESSION['username'], $_GET['id']);

$cmd = ociparse($c, $q);

ociexecute($cmd);

if ( ocifetch($cmd) ) {
  $id = OCIResult($cmd, "SUBMISSION_ID");
  $username = OCIResult($cmd, "USERNAME");
  $server_title = OCIResult($cmd, "SERVER_TITLE");
  $description =  OCIResult($cmd, "DESCRIPTION");
  $server_url =  OCIResult($cmd, "SERVER_URL");
  $server_port = OCIResult($cmd, "SERVER_PORT");
  $server_type =  OCIResult($cmd, "SERVER_TYPE");
  $score = OCIResult($cmd, "SCORE");
  $vote = OCIResult($cmd, "VOTE");
}
else {
  echo 'This server doesnt exist';
  die();
}

//AHHHHH CONNASCENCE AHHHHHH OPEN-CLOSED PRINCIPLE ;_;
if($server_type == 0) {
  $server_type = "Survival";
}
else if($server_type == 1) {
  $server_type = "Creative";
}
else {
  $server_type = "Other";
}

echo '<h2>' . $server_title . '</h2><br>';

echo 'Score: ' . $score . '&nbsp;|&nbsp;<a href="server_vote.php?vote=up&id='.$id.'">';

//check if a user has already voted on this server and display the appropriate graphics
if ($vote == 1) {
 echo '<img src="img/up-arrow-vote.png">';
}
else { echo '<img src="img/up-arrow-no-vote.png">'; }

echo '</a>&nbsp;<a href="server_vote.php?vote=down&id='.$id.'">';

if ($vote == -1) {
 echo '<img src="img/down-arrow-vote.png">';
}
else { echo '<img src="img/down-arrow-no-vote.png">'; }

echo '</a><br>';
echo 'URL: ' . $server_url;
echo '<br>';
echo 'Port: ' . $server_port;
echo '<br>';
echo 'Description:<br> ' . $description;
echo '<br>';
echo 'Server Type: ' . $server_type;
echo '<br>';
echo 'Posted by: <a href="profile.php?user=' . $username . '">' . $username . '</a>';

if(strcmp($_SESSION['username'], $username) == 0 ) {
  echo '<br>';
  echo '<br>';
  echo '<form name="deleteserver" action="deleteserver.php" method="post">';
  echo '<input type="hidden" name="id" value="'.$id.'">';
  echo '<input type="submit" value="Delete this submission">';
  echo '</form>';


}

echo '<br><br><br>';
echo 'Comments<br><br>';

$q = sprintf("select comment_id, username, comment_text, score, date_submitted, vote from comments natural left outer join (select submission_id, comment_id, vote from comment_votes where username='%s') where submission_id=%d order by score desc, date_submitted asc", $_SESSION['username'], $_GET['id']);

$cmd = ociparse($c, $q);
ociexecute($cmd);
while(ocifetch($cmd)) {

$c_id = OCIResult($cmd, "COMMENT_ID");
$c_by = OCIResult($cmd, "USERNAME");
$c_text = OCIResult($cmd, "COMMENT_TEXT");
$c_score = OCIResult($cmd, "SCORE");
$c_vote = OCIResult($cmd, "VOTE");

echo 'Comment by: <a href="profile.php?user=' . $c_by. '">' . $c_by . '</a>';
if( strcmp($c_by, $_SESSION['username']) == 0) {
  
  echo '<form name="deletecomment" action="deletecomment.php" method="post">';
  echo '<input type="hidden" name="id" value="'.$c_id.'">';
  echo '<input type="hidden" name="backto" value="'.$_GET['id'].'">';
  echo '<input type="submit" value="Delete this comment">';
  echo '</form>';

}


echo '<br>';
echo $c_text;
echo '<br>';
echo 'Score: ' . $c_score . '&nbsp;|&nbsp;<a href="comment_vote.php?vote=up&id='.$id.'&cid='.$c_id.'">';

//check if a user has already voted on this comment and display the appropriate graphics
if ($c_vote == 1) {
 echo '<img src="img/up-arrow-vote.png">';
}
else { echo '<img src="img/up-arrow-no-vote.png">'; }

echo '</a>&nbsp;<a href="comment_vote.php?vote=down&id='.$id.'&cid='.$c_id.'">';

if ($c_vote == -1) {
 echo '<img src="img/down-arrow-vote.png">';
}
else { echo '<img src="img/down-arrow-no-vote.png">'; }

echo '</a><br><br>';

}


disconnect($c);

if(isset($_SESSION['username'])) {
   echo 'Leave your own comment!';
   echo '<form name="comment" action="newcomment.php" method="POST">';
   echo '<textarea name="comment_text" rows="8" cols="50" maxlength="4000"></textarea><br>';
   echo '<input name="server_id" type="hidden" value="' .$_GET['id'] .'" />';
   echo '<input type="submit" value="Submit" />';
   echo '</form>';

}

require 'footer.php';

?>
