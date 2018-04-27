<?php
   $con=mysqli_connect("localhost","root","","BloodDomain");

   if (mysqli_connect_errno($con)) {
      echo "fail" . mysqli_connect_error();
   }

   $mobile_no = $_GET['mobile'];
   $password = $_GET['password'];
   $result = mysqli_query($con,"SELECT * FROM users where mobile='$mobile_no' 
      and password='$password'");
   $row = mysqli_fetch_array($result);
   $data = $row[0];

   if($data){
      echo json_encode($row);
   }
   mysqli_close($con);
?>
