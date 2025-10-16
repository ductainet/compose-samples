# 🚀 Hướng dẫn chạy nhanh Nesting 2D

## ✅ Cách chạy đơn giản nhất

```bash
cd /workspace/nesting2d-app
./run-simple.sh
```

## 🎯 Các cách chạy khác

### 1. Console Version (Khuyến nghị - không cần GUI)
```bash
cd /workspace/nesting2d-app
./run-simple.sh
```

### 2. GUI Version (cần display)
```bash
cd /workspace/nesting2d-app
./start-app.sh
```

### 3. Chạy trực tiếp với Java
```bash
cd /workspace/nesting2d-app
java -cp build/classes/java/main com.nesting2d.SimpleConsoleMain
```

## 🎮 Cách sử dụng

1. **Chạy ứng dụng**: Chọn một trong các cách trên
2. **Import file**: Chọn option 1, nhấn Enter để dùng demo
3. **Xem thông tin**: Chọn option 2 để xem chi tiết file
4. **Thoát**: Chọn option 3

## 📊 Kết quả mong đợi

Khi chạy thành công, bạn sẽ thấy:
- Menu với 3 options
- Demo CAD file với 6 entities (LINE, CIRCLE, LWPOLYLINE, ARC, TEXT)
- Thông tin chi tiết về file và entities
- Có thể import nhiều file khác nhau

## 🔧 Troubleshooting

### Lỗi "Java not found"
```bash
sudo apt-get install openjdk-17-jdk
```

### Lỗi "Permission denied"
```bash
chmod +x run-simple.sh
```

### Lỗi compilation
```bash
mkdir -p build/classes/java/main
```

## 🎉 Thành công!

Nếu bạn thấy menu và có thể chọn options, nghĩa là ứng dụng đã chạy thành công! 

Ứng dụng hiện tại có:
- ✅ Import file CAD (demo)
- ✅ Hiển thị thông tin file
- ✅ Hiển thị entities (LINE, CIRCLE, ARC, TEXT, POLYLINE)
- ✅ Thống kê theo loại entity
- ✅ Thông tin layers và colors

Chúc bạn sử dụng vui vẻ! 🎊