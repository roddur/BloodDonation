<?php
   $con=mysqli_connect("localhost","root","","BloodDomain");

   if (mysqli_connect_errno($con)) {
      echo "fail" . mysqli_connect_error();
   }

   $mobile = $_GET['mobile'];
   $result = mysqli_query($con,"SELECT date FROM history 
	where mobile='$mobile'");

   $data=null;
   while($row = mysqli_fetch_array($result)){
	$data[]=$row;
   }

   if($data!=null) echo json_encode($data);
   mysqli_close($con);
?>
