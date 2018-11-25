<?php
	$con = mysqli_connect("localhost","root","","BloodDomain");

    	if (mysqli_connect_errno()){
	       echo "fail" . mysqli_connect_error();
	}

	$mobile=$_GET["mobile"];
	$last_donation=$_GET["last_donation"];

	$sql= "UPDATE users SET last_donation='$last_donation', no_of_donation=no_of_donation+1
		 WHERE mobile='$mobile'";

	mysqli_query($con ,$sql);

	$sql="INSERT INTO history VALUES('$mobile','$last_donation')";
	mysqli_query($con, $sql);
	mysqli_close($con);
?>
