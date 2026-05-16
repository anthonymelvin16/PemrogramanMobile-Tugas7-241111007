<?php
header("Content-Type: application/json");
echo json_encode([
    "success" => true,
    "message" => "API Data Hujan berjalan dengan baik!",
    "version" => "1.0"
]);
?>