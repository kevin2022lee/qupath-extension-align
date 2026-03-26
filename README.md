# QuPath Align Extension

欢迎使用 [QuPath](http://qupath.github.io) 图像对齐扩展插件！

---

## 功能特性 | Features

- **Interactive image alignment / 交互式图像对齐** - 手动旋转、平移叠加图像
- **Auto alignment / 自动对齐** - 基于 ECC 算法自动计算变换矩阵
- **Multiple transformation modes / 多种变换模式** - Affine（仿射）和 Rigid（刚体）
- **Multiple alignment methods / 多种对齐方式** - Image Intensity、Area Annotations、Point Annotations
- **Annotation propagation / 注释传播** - 将注释从基础图像变换后复制到叠加图像

---

## 安装 | Installation

### 方法一：从 Releases 下载（推荐）
**Method 1: Download from Releases (Recommended)**

从 [Releases](https://github.com/kevin2022lee/qupath-extension-alignment-0.7.0/releases) 下载最新的 `qupath-extension-align-0.7.0.jar` 文件，然后拖拽到 QuPath 主窗口。

Download the latest `qupath-extension-align-0.7.0.jar` from [Releases](https://github.com/kevin2022lee/qupath-extension-alignment-0.7.0/releases) and drag it onto the main QuPath window.

### 方法二：手动安装
**Method 2: Manual Installation**

1. 打开 QuPath → `Extensions` → `Manage extensions`
2. 点击 `Add extension`
3. 选择 JAR 文件
4. 重启 QuPath

Open QuPath → `Extensions` → `Manage extensions`, click `Add extension`, select the JAR file, and restart QuPath.

---

## 使用方法 | Usage

### 启动插件 | Launch the Extension

```
Menu → Extensions → Alignment
```

### 基本操作流程 | Basic Workflow

```
步骤 1: 在 QuPath 中打开一张基础图像
Step 1: Open a base image in QuPath

        ↓

步骤 2: 点击 Extensions → Alignment 打开对齐窗口
Step 2: Click Extensions → Alignment to open the alignment window

        ↓

步骤 3: 点击 "Choose images from project" 选择叠加图像
Step 3: Click "Choose images from project" to select overlay images

        ↓

步骤 4: 在列表中勾选要叠加的图像
Step 4: Check the images you want to overlay in the list

        ↓

步骤 5: 手动调整（旋转 + 平移）
Step 5: Manually adjust (rotation + translation)

        ↓

步骤 6: 点击 "Estimate Transform" 自动精细对齐
Step 6: Click "Estimate Transform" for auto alignment
```

### 手动调整 | Manual Adjustment

| 控件 Control | 功能 Function |
|--------------|---------------|
| **←→↑↓** | 平移图像（Offset increment 控制步长）Translate image |
| **◀ ▶** | 旋转图像（Rotation increment 控制步长）Rotate image |
| **Shift + 拖动** | 快速平移 Quick translate |

### 自动对齐 | Auto Alignment

#### Transformation Types / 变换类型

| 类型 Type | 说明 Description |
|-----------|-----------------|
| **Affine** | 旋转 + 平移 + X/Y独立缩放 + 剪切（支持非均匀变形） |
| **Rigid** | 旋转 + 平移 + 统一缩放（刚体变换） |

#### Alignment Type / 对齐方式

| 方式 Method | 说明 Description |
|-------------|-----------------|
| **Image Intensity** | 基于像素值匹配（推荐，不需要注释） |
| **Area Annotations** | 基于面积注释匹配 |
| **Point Annotations** | 基于点注释匹配 |

### Propagate / 传播注释

将对齐后的注释从基础图像变换并复制到叠加图像。

Propagate annotations from the base image to the overlay image after alignment.

---

## 参数说明 | Parameters

| 参数 Parameter | 说明 Description | 默认值 Default |
|---------------|------------------|----------------|
| **Pixel size** | 自动对齐时的图像分辨率（微米），值越大速度越快 | 20 |
| **Rotation increment** | 每次旋转的角度（度） | 1 |
| **Offset increment** | 每次平移的像素数 | 10 |

---

## 常见问题 | FAQ

**Q: 按钮都是灰色的？**
**Q: Buttons are grayed out?**

A: 需要先选择叠加图像（在列表中勾选）。
A: Select an overlay image first (check it in the list).

---

**Q: "Dimensions too large" 错误？**
**Q: "Dimensions too large" error?**

A: 图像太大，增加 Pixel size 值（如 50-100）。
A: Image is too large. Increase Pixel size value (e.g., 50-100).

---

**Q: 自动对齐效果不好？**
**Q: Auto alignment not good?**

A: 先手动粗调再自动对齐，或尝试 Affine 模式。
A: Manually adjust first, or try Affine mode.

---

## 构建 | Building

```bash
git clone https://github.com/kevin2022lee/qupath-extension-alignment-0.7.0.git
cd qupath-extension-alignment-0.7.0
./gradlew build
```

JAR 文件位于 `build/libs/qupath-extension-align-0.7.0.jar`

JAR file is at `build/libs/qupath-extension-align-0.7.0.jar`

---

## 版本历史 | Changelog

| 版本 Version | 日期 Date | 说明 Changes |
|--------------|-----------|--------------|
| **0.7.0** | 2026-03-26 | 适配 QuPath 0.7.0；新增平移调整功能 (↑↓←→)；支持阿里云 Maven 镜像 |
| 0.5.0 | - | 初始版本分离 |

---

## 系统要求 | Requirements

- QuPath 0.7.0 or later
- Java 17+

---

## 许可证 | License

GNU General Public License v3.0

See [LICENSE](LICENSE) for details.
