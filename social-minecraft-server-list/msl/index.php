#!/usr/local/bin/php
<?php
/*
Preston Mueller
2-17-11
Main page for the site with the server list
*/

session_start();

require 'header.php';

echo '<div id="index">';
echo '<div id="indexcontentheader">';
echo '<div id="indexcontentheaderoptions">';
if(!isset($_GET['type'])) {
  echo '<a href="index.php?sortby=top">Top</a>';
  echo '<a href="index.php?sortby=newest">Newest</a>';
} else {
  echo '<a href="index.php?sortby=top&type=' .$_GET['type'] .'">Top</a>';
  echo '<a href="index.php?sortby=newest&type=' .$_GET['type'] .'">Newest</a>';
}
echo '</div>'; //end indexcontentheaderoptions
echo '<div id="indexcontentheadersortby">';
echo 'Type:';
echo '<a href="index.php?type=all">All</a>';
echo '<a href="index.php?type=survival">Survival</a>';
echo '<a href="index.php?type=creative">Creative</a>';
echo '<a href="index.php?type=other">Other</a>';
echo '</div>'; //end indexcontentheadersortby
echo '&nbsp;</div>'; //end indexcontentheader
echo '<div id="indexcontent">';

if( isset($_SESSION['username']) ) {

echo '<a href="newserver.php">New Server Submission</a>';
echo '<br><br>';

}

//use get to determine starting entry on page and sort options
//get the starting entry through starting entry +30 based on sort options

if(!isset($_GET['n']) ) {
  $n = 0;
}
else {
  $n = $_GET['n'];
}

require 'dbinfo.php';

$c = connect();

$q = sprintf("select submission_id, server_title, username, score, date_submitted, server_type from server");

$sortby = ' order by score desc, date_submitted asc';
if(strcmp($_GET['sortby'], 'newest') == 0) {
  $sortby = ' order by date_submitted desc';
}

if(isset($_GET['type'])) {
  $type = ' where server_type in (0, 1, 2)';

  if(strcmp($_GET['type'], "creative")  == 0) {
    $type = ' where server_type = 1';
  }
  else if (strcmp($_GET['type'], "survival") == 0) {
    $type = ' where server_type = 0';
  }
  else if (strcmp($_GET['type'], "other") == 0) {
    $type = ' where server_type = 2';
  }
  
  $q = $q . $type;
}

$q = $q . $sortby;

$cmd = ociparse($c, $q);

ociexecute($cmd);

$count = 0;
while($n > 0 && ocifetch($cmd) && $count < $n) {
  $count++;
}

for( $i = 0; $i < $n+30 && ocifetch($cmd); ++$i ) {
  $title = OCIResult($cmd, 'SERVER_TITLE');
  $submitter = OCIResult($cmd, 'USERNAME');
  $score = OCIResult($cmd, 'SCORE');
  $id = OCIResult($cmd, 'SUBMISSION_ID');
  $type_int = OCIResult($cmd, 'SERVER_TYPE');

if ($type_int == 0) {
  $type = "Survival";
}
else if ($type_int == 1) {
  $type = "Creative";
}
else
  $type = "Other";

  echo $score . "&nbsp;&nbsp;|&nbsp;&nbsp;<a href='serverinfo.php?id=" . $id . "'>" . $title . "</a>&nbsp;&nbsp;|&nbsp;&nbsp;Submitted by: <a href='profile.php?user=" . $submitter . "'>" . $submitter . "</a>&nbsp;&nbsp;|&nbsp;&nbsp;Type: ". $type;
  echo '<br><br>';
}

disconnect($c);

$next = $n+30;

if($n !=0 ) {
$prev = $n-30;
if($prev < 0)
  $prev = 0;
echo "<a href='index.php?n=" .$prev. "'>Prev</a>";
echo "  ||  ";

}
echo "<a href='index.php?n=" .$next. "'>Next</a>";

echo '</div>'; //end indexcontent
echo '</div>'; //end index

require 'footer.php';

?>
