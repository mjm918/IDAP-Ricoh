<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 04/09/2017
 * Time: 11:25 AM
 */

function getStudentInfo($id,$con){

    $query = mysqli_query($con,"select * from sinfo where matri = '$id'");

    $res = $query->fetch_assoc();

    $programme = $res['programme'];

    return $programme;
}

?>