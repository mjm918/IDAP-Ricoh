<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 04/09/2017
 * Time: 11:14 AM
 */
function getRole($string){
    $role = "";
    switch ($string){
        case "HOP":
            $role = "0";
            break;
    }
    return $role;
}