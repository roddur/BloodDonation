<?php
   $con=mysqli_connect("localhost","root","","BloodDomain");

   if (mysqli_connect_errno($con)) {
      echo "fail" . mysqli_connect_error();
   }

   $blood = $_GET['blood'];
   $result = mysqli_query($con,"SELECT firstname, lastname, mobile FROM users 
	where blood_group='$blood' AND DATEDIFF(CURDATE(), last_donation)>90");

   $data=null;
   while($row = mysqli_fetch_array($result)){
	$data[]=$row;
   }

   if($data!=null) echo json_encode($data);
   mysqli_close($con);
?>
