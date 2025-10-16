# Hướng dẫn sử dụng Nesting 2D

## Tổng quan

Ứng dụng Nesting 2D được thiết kế để import và hiển thị file CAD (.dwg và .dxf) cho dự án nesting 2D cắt ván CNC. Ứng dụng cung cấp giao diện desktop thân thiện để xem và phân tích các file CAD.

## Cài đặt

### Yêu cầu hệ thống
- Java 17 hoặc cao hơn
- JavaFX 17
- Hệ điều hành: Windows, Linux, hoặc macOS

### Cài đặt từ source code

1. Clone repository hoặc download source code
2. Mở terminal/command prompt tại thư mục dự án
3. Chạy lệnh build:
   ```bash
   ./gradlew build
   ```

## Chạy ứng dụng

### Cách 1: Sử dụng Gradle
```bash
./gradlew run
```

### Cách 2: Sử dụng script (cho môi trường headless)
```bash
./run-app.sh
```

### Cách 3: Chạy JAR file
```bash
java -jar build/libs/nesting2d-app-1.0.0.jar
```

## Hướng dẫn sử dụng

### 1. Import file CAD

1. **Mở ứng dụng**: Chạy ứng dụng bằng một trong các cách trên
2. **Import file**: 
   - Click nút "Import CAD File" ở bên trái
   - Hoặc sử dụng menu File > Import CAD File
3. **Chọn file**: Trong dialog mở file, chọn file .dwg hoặc .dxf
4. **Xem kết quả**: Thông tin file sẽ hiển thị ở panel bên trái

### 2. Xem thông tin file

Sau khi import thành công, bạn sẽ thấy:
- **File Name**: Tên file
- **File Type**: Loại file (DWG/DXF)
- **File Size**: Kích thước file
- **Import Status**: Trạng thái import

### 3. Xem preview

- Canvas bên phải hiển thị preview của file CAD
- Hiện tại hiển thị các đối tượng cơ bản như line, circle, polyline

### 4. Xem chi tiết

1. **Mở cửa sổ chi tiết**: Click nút "Show Details"
2. **Tab General Information**: 
   - Thông tin file và drawing
   - Version, units, dimensions
3. **Tab Entities**: 
   - Danh sách tất cả entities trong file
   - Filter theo type và layer
   - Thông tin chi tiết của từng entity
4. **Tab Layers**: 
   - Danh sách layers
   - Số lượng entities trong mỗi layer

## Tính năng chính

### Import file CAD
- Hỗ trợ file .dwg và .dxf
- Tự động detect loại file
- Hiển thị thông tin file cơ bản

### Hiển thị entities
- **LINE**: Đường thẳng
- **CIRCLE**: Hình tròn
- **ARC**: Cung tròn
- **LWPOLYLINE**: Polyline nhẹ
- **POLYLINE**: Polyline
- **TEXT**: Văn bản

### Filter và tìm kiếm
- Filter entities theo type
- Filter entities theo layer
- Clear filters để xem tất cả

### Preview 2D
- Hiển thị preview các đối tượng 2D
- Canvas có thể scroll và zoom
- Hiển thị tên file trên preview

## Cấu trúc file

```
nesting2d-app/
├── src/main/java/com/nesting2d/
│   ├── Main.java                 # Entry point
│   ├── ui/                       # Controllers
│   ├── model/                    # Data models
│   └── service/                  # Business logic
├── src/main/resources/
│   ├── fxml/                     # UI layouts
│   └── css/                      # Styles
├── demo/                         # Sample files
├── build.gradle                  # Build configuration
└── README.md                     # Documentation
```

## Troubleshooting

### Lỗi "Unable to open DISPLAY"
- **Nguyên nhân**: Môi trường headless không có display server
- **Giải pháp**: Sử dụng script `run-app.sh` hoặc cài đặt Xvfb

### Lỗi "Module not found"
- **Nguyên nhân**: JavaFX không được cài đặt đúng
- **Giải pháp**: Kiểm tra JavaFX installation và module path

### File không import được
- **Nguyên nhân**: File format không được hỗ trợ hoặc bị lỗi
- **Giải pháp**: Kiểm tra file có đúng format DXF/DWG không

## Phát triển thêm

### Thêm thư viện đọc file thực
Hiện tại ứng dụng sử dụng placeholder implementation. Để thêm hỗ trợ thực:

1. **Cho DXF**: Thêm dependency Kabeja hoặc DXF4J
2. **Cho DWG**: Thêm ACadSharp hoặc thư viện tương tự
3. Cập nhật `DxfFileReader.java` và `DwgFileReader.java`

### Cải thiện preview
- Thêm zoom/pan controls
- Hiển thị đúng scale và units
- Thêm colors và line styles
- Hiển thị layers với màu sắc khác nhau

### Thêm tính năng nesting
- Thuật toán nesting 2D
- Tối ưu hóa layout
- Export sang format khác
- Tính toán waste material

## Liên hệ và hỗ trợ

Nếu gặp vấn đề hoặc có góp ý, vui lòng tạo issue trong repository hoặc liên hệ qua email.

## License

MIT License - Xem file LICENSE để biết thêm chi tiết.