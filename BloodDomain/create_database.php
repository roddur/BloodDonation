<?php
   $con=mysqli_connect("localhost","root","");
   $sql="CREATE DATABASE BloodDomain";
   if (mysqli_query($con,$sql)) {
      echo "Database BloodDomain created successfully";
   }
?>
