# 🚀 Hướng dẫn chạy ứng dụng Nesting 2D

## Cách 1: Chạy nhanh (Khuyến nghị)

```bash
cd /workspace/nesting2d-app
./start-app.sh
```

Script này sẽ:
- ✅ Kiểm tra Java version
- ✅ Build JAR file nếu cần
- ✅ Tạo virtual display nếu cần
- ✅ Chạy ứng dụng

## Cách 2: Chạy thủ công

### Bước 1: Kiểm tra Java
```bash
java -version
```
Cần Java 17 hoặc cao hơn.

### Bước 2: Build ứng dụng
```bash
cd /workspace/nesting2d-app
./gradlew build
```

### Bước 3: Chạy ứng dụng

#### Nếu có display (máy tính có GUI):
```bash
./gradlew run
```

#### Nếu không có display (server/headless):
```bash
# Cài đặt Xvfb (nếu chưa có)
sudo apt-get install xvfb

# Chạy với virtual display
Xvfb :99 -screen 0 1024x768x24 &
export DISPLAY=:99
java -jar build/libs/nesting2d-app-1.0.0.jar
```

## Cách 3: Chạy với JAR file

```bash
cd /workspace/nesting2d-app
java -jar build/libs/nesting2d-app-1.0.0.jar
```

## Cách 4: Chạy trong IDE

1. Mở IntelliJ IDEA hoặc Eclipse
2. Import project từ thư mục `/workspace/nesting2d-app`
3. Chạy class `com.nesting2d.Main`

## 🎯 Cách sử dụng ứng dụng

1. **Mở ứng dụng**: Chạy bằng một trong các cách trên
2. **Import file CAD**: 
   - Click nút "Import CAD File"
   - Chọn file .dwg hoặc .dxf
3. **Xem thông tin**: Thông tin file hiển thị ở panel trái
4. **Xem preview**: Canvas bên phải hiển thị preview
5. **Xem chi tiết**: Click "Show Details" để xem thông tin chi tiết

## 🔧 Troubleshooting

### Lỗi "Unable to open DISPLAY"
```bash
# Cài đặt Xvfb
sudo apt-get install xvfb

# Chạy với virtual display
Xvfb :99 -screen 0 1024x768x24 &
export DISPLAY=:99
java -jar build/libs/nesting2d-app-1.0.0.jar
```

### Lỗi "Java version too old"
```bash
# Cài đặt Java 17
sudo apt-get install openjdk-17-jdk

# Hoặc sử dụng SDKMAN
curl -s "https://get.sdkman.io" | bash
source ~/.sdkman/bin/sdkman-init.sh
sdk install java 17.0.2-open
```

### Lỗi "JAR file not found"
```bash
# Build lại ứng dụng
./gradlew clean build
```

## 📁 File demo

Trong thư mục `demo/` có sẵn file mẫu:
- `sample.dxf`: File DXF mẫu
- `sample.dwg`: File DWG mẫu (placeholder)

## 🎉 Kết quả mong đợi

Khi chạy thành công, bạn sẽ thấy:
- Cửa sổ ứng dụng với giao diện đẹp
- Nút "Import CAD File" để import file
- Canvas preview ở bên phải
- Panel thông tin ở bên trái

Chúc bạn sử dụng ứng dụng thành công! 🎊