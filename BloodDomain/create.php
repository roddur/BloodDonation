<?php
	$con = mysqli_connect("localhost","root","","BloodDomain");

    	if (mysqli_connect_errno()){
	       echo "fail" . mysqli_connect_error();
	}

	$firstname = $_GET["firstname"];
	$lastname=$_GET["lastname"];
	$email=$_GET["email"];
	$password=$_GET["password"];
	$dob=$_GET["date_of_birth"];
	$mobile=$_GET["mobile"];
	$blood_group=$_GET["blood_group"];
	$last_donation=$_GET["last_donation"];
	$nod=$_GET["hospital"];

	$sql="select * from users where mobile='$mobile'";

	$result=mysqli_query($con,$sql);

	$row=mysqli_fetch_array($result);
	$data=$row[0];

	if($data){
		echo json_encode("miss");
	} else{
		$sql= "insert into users values ('$firstname', '$lastname', '$email', '$password',
			'$dob', '$mobile', '$blood_group', '$last_donation', '$nod')";

		mysqli_query($con ,$sql);
	}
	mysqli_close($con);
?>
