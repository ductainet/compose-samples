# Nesting 2D - CAD File Viewer

Ứng dụng JavaFX để import và hiển thị file CAD (.dwg/.dxf) cho dự án nesting 2D.

## Tính năng

- Import file .dwg và .dxf
- Hiển thị chi tiết file CAD
- Render các entity 2D (đường thẳng, hình tròn, hình chữ nhật)
- Giao diện đơn giản và dễ sử dụng

## Yêu cầu hệ thống

- Java 17 hoặc cao hơn
- JavaFX 21

## Cách chạy

1. Cài đặt JavaFX:
```bash
# Trên Ubuntu/Debian
sudo apt install openjfx

# Hoặc tải JavaFX SDK từ https://openjfx.io/
```

2. Build và chạy ứng dụng:
```bash
./gradlew run
```

## Cấu trúc dự án

- `Main.java` - Class chính chứa giao diện chính
- `CADViewer.java` - Component hiển thị file CAD
- `CADEntity.java` - Class đại diện cho các entity CAD
- `CADFileReader.java` - Class đọc file CAD

## Hạn chế hiện tại

- Chỉ hỗ trợ đọc file .dxf (ASCII format)
- File .dwg hiển thị dữ liệu mẫu
- Chưa hỗ trợ đầy đủ tất cả entity types của AutoCAD

## Kế hoạch phát triển

- Tích hợp thư viện chuyên dụng để đọc file .dwg
- Hỗ trợ thêm các entity types khác
- Thêm tính năng zoom và pan
- Export sang các format khác