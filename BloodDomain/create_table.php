<?php
   $con=mysqli_connect("localhost","root","","BloodDomain");
   $sql="CREATE TABLE users(firstname VARCHAR(30),lastname VARCHAR(30), email VARCHAR(30),
	password VARCHAR(30), date_of_birth DATE, mobile VARCHAR(15), blood_group CHAR(2),
	last_donation DATE, no_of_donation INT)";
   if (mysqli_query($con,$sql)) {
      echo "Table user have been created successfully";
   }
	 $sql="CREATE TABLE history(mobile VARCHAR(15),date DATE)";
   if (mysqli_query($con,$sql)) {
      echo "Table history have been created successfully";
   }
?>
