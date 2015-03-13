<?php

/*
Preston Mueller
3-9-11
This script has the db username, password and instace id.
It will be included in any other script that needs to access
the db, essentially making these globals, but only in the right spots.
*/

$u = "pmueller";
$p = "lollol";
$o = "orcl";

function connect() {
   putenv("ORACLE_HOME=/usr/local/libexec/oracle/app/oracle/product/11.2.0/client_1");
   //$connection = ocilogon($u, $p, $o);
   $connection = ocilogon("", "", "orcl");
   if( !$connection ) {
      echo 'dfs';
     die();
   }

   return $connection;
}

function disconnect( $connection ) {
   ocilogoff($connection);
}

?>
