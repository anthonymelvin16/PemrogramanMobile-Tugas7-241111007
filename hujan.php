<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE");
header("Access-Control-Allow-Headers: Content-Type");

require_once 'koneksi.php';

$method = $_SERVER['REQUEST_METHOD'];

switch ($method) {

    // GET semua data / GET by ID
    case 'GET':
        if (isset($_GET['id'])) {
            $id   = intval($_GET['id']);
            $stmt = $conn->prepare("SELECT * FROM data_hujan WHERE id = ?");
            $stmt->bind_param("i", $id);
            $stmt->execute();
            $result = $stmt->get_result();
            $data   = $result->fetch_assoc();

            if ($data) {
                echo json_encode(["success" => true, "message" => "Data ditemukan", "data" => $data]);
            } else {
                echo json_encode(["success" => false, "message" => "Data tidak ditemukan", "data" => null]);
            }
        } else {
            $result = $conn->query("SELECT * FROM data_hujan ORDER BY tanggal DESC");
            $data   = [];
            while ($row = $result->fetch_assoc()) {
                $data[] = $row;
            }
            echo json_encode(["success" => true, "message" => "Berhasil mengambil data", "data" => $data]);
        }
        break;

    // POST tambah data baru
    case 'POST':
        $body          = json_decode(file_get_contents("php://input"), true);
        $lokasi        = $body['lokasi']      ?? '';
        $tanggal       = $body['tanggal']     ?? '';
        $curah_hujan   = $body['curah_hujan'] ?? 0;
        $kategori      = $body['kategori']    ?? '';
        $keterangan    = $body['keterangan']  ?? '';

        if (empty($lokasi) || empty($tanggal) || empty($kategori)) {
            echo json_encode(["success" => false, "message" => "Field wajib tidak boleh kosong"]);
            break;
        }

        $stmt = $conn->prepare(
            "INSERT INTO data_hujan (lokasi, tanggal, curah_hujan, kategori, keterangan) VALUES (?, ?, ?, ?, ?)"
        );
        $stmt->bind_param("ssdss", $lokasi, $tanggal, $curah_hujan, $kategori, $keterangan);

        if ($stmt->execute()) {
            echo json_encode(["success" => true, "message" => "Data berhasil ditambahkan", "data" => ["id" => $conn->insert_id]]);
        } else {
            echo json_encode(["success" => false, "message" => "Gagal menambahkan data"]);
        }
        break;

    // PUT update data
    case 'PUT':
        $id            = intval($_GET['id'] ?? 0);
        $body          = json_decode(file_get_contents("php://input"), true);
        $lokasi        = $body['lokasi']      ?? '';
        $tanggal       = $body['tanggal']     ?? '';
        $curah_hujan   = $body['curah_hujan'] ?? 0;
        $kategori      = $body['kategori']    ?? '';
        $keterangan    = $body['keterangan']  ?? '';

        $stmt = $conn->prepare(
            "UPDATE data_hujan SET lokasi=?, tanggal=?, curah_hujan=?, kategori=?, keterangan=? WHERE id=?"
        );
        $stmt->bind_param("ssdssi", $lokasi, $tanggal, $curah_hujan, $kategori, $keterangan, $id);

        if ($stmt->execute()) {
            echo json_encode(["success" => true, "message" => "Data berhasil diupdate"]);
        } else {
            echo json_encode(["success" => false, "message" => "Gagal mengupdate data"]);
        }
        break;

    // DELETE hapus data
    case 'DELETE':
        $id   = intval($_GET['id'] ?? 0);
        $stmt = $conn->prepare("DELETE FROM data_hujan WHERE id = ?");
        $stmt->bind_param("i", $id);

        if ($stmt->execute()) {
            echo json_encode(["success" => true, "message" => "Data berhasil dihapus"]);
        } else {
            echo json_encode(["success" => false, "message" => "Gagal menghapus data"]);
        }
        break;

    default:
        echo json_encode(["success" => false, "message" => "Method tidak diizinkan"]);
        break;
}

$conn->close();
?>