# Nesting 2D - CAD File Importer

Ứng dụng desktop để import và hiển thị file CAD (.dwg và .dxf) cho dự án nesting 2D cắt ván CNC.

## Tính năng

- **Import file CAD**: Hỗ trợ import file .dwg và .dxf
- **Hiển thị thông tin file**: Thông tin chi tiết về file CAD đã import
- **Xem trước 2D**: Hiển thị preview các đối tượng 2D trong file
- **Chi tiết entities**: Xem danh sách và thông tin chi tiết của các đối tượng CAD
- **Quản lý layers**: Hiển thị thông tin về các layer trong file CAD

## Yêu cầu hệ thống

- Java 17 hoặc cao hơn
- JavaFX 17
- Windows/Linux/macOS

## Cài đặt và chạy

### Sử dụng Gradle Wrapper

```bash
# Chạy ứng dụng
./gradlew run

# Build JAR file
./gradlew build

# Chạy JAR file
java -jar build/libs/nesting2d-app-1.0.0.jar
```

### Sử dụng IDE

1. Mở project trong IntelliJ IDEA hoặc Eclipse
2. Import Gradle project
3. Chạy class `com.nesting2d.Main`

## Cấu trúc dự án

```
src/main/java/com/nesting2d/
├── Main.java                 # Entry point
├── ui/                       # Controllers cho giao diện
│   ├── MainController.java   # Controller chính
│   └── DetailsController.java # Controller cửa sổ chi tiết
├── model/                    # Model classes
│   ├── CadFile.java         # Model cho file CAD
│   ├── CadEntity.java       # Model cho entity CAD
│   ├── Point2D.java         # Model cho điểm 2D
│   └── CadDrawingInfo.java  # Model cho thông tin drawing
└── service/                  # Services
    ├── CadFileService.java  # Service chính
    ├── DxfFileReader.java   # Reader cho file DXF
    └── DwgFileReader.java   # Reader cho file DWG
```

## Thư viện sử dụng

- **Kabeja**: Đọc file DXF
- **ACadSharp**: Đọc file DWG (placeholder implementation)
- **JavaFX**: Giao diện desktop
- **SLF4J + Logback**: Logging

## Hướng dẫn sử dụng

1. **Import file CAD**:
   - Click nút "Import CAD File" hoặc File > Import CAD File
   - Chọn file .dwg hoặc .dxf
   - Ứng dụng sẽ tự động đọc và hiển thị thông tin

2. **Xem chi tiết**:
   - Click nút "Show Details" để mở cửa sổ chi tiết
   - Xem thông tin general, entities, và layers
   - Sử dụng filter để lọc entities theo type hoặc layer

3. **Preview**:
   - Canvas bên phải hiển thị preview của file CAD
   - Hiện tại hiển thị các đối tượng cơ bản

## Phát triển thêm

### Thêm hỗ trợ đọc DWG

Hiện tại DWG reader chỉ là placeholder. Để thêm hỗ trợ thực sự:

1. Thêm dependency ACadSharp vào build.gradle
2. Implement thực sự trong `DwgFileReader.java`

### Cải thiện preview

- Thêm zoom/pan cho canvas
- Hiển thị đúng scale và units
- Thêm colors và line styles

### Thêm tính năng nesting

- Thuật toán nesting 2D
- Tối ưu hóa layout
- Export sang format khác

## Troubleshooting

### Lỗi "Module not found"

Đảm bảo JavaFX được cài đặt đúng và module path được set:

```bash
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

### Lỗi đọc file DXF

Kiểm tra file DXF có đúng format không. Một số file DXF có thể cần version khác của Kabeja.

## License

MIT License