# QuPath Image Alignment 插件用户指南

> 适用版本：QuPath 0.7.0+
> 插件版本：qupath-extension-align 0.7.0

---

## 目录

1. [插件简介](#1-插件简介)
2. [安装插件](#2-安装插件)
3. [界面概览](#3-界面概览)
4. [快速入门](#4-快速入门)
5. [功能详解](#5-功能详解)
6. [参数说明](#6-参数说明)
7. [常见问题](#7-常见问题)

---

## 1. 插件简介

QuPath Image Alignment 插件用于**交互式图像对齐**，可以将多张图像叠加在一起进行对比分析。

### 典型应用场景

- **多模态成像**：将不同染色或不同成像模式的图像对齐
- **相邻切片对齐**：病理学中相邻组织切片的配准
- **图像融合**：将荧光图像与明场图像叠加分析
- **注释传播**：将一张图像上的标注同步到另一张

---

## 2. 安装插件

### 2.1 构建插件

如果需要从源码构建：

```bash
git clone https://github.com/qupath/qupath-extension-align.git
cd qupath-extension-align
./gradlew build
```

生成的 JAR 文件位于 `build/libs/qupath-extension-align-0.7.0.jar`

### 2.2 安装到 QuPath

1. 打开 QuPath 0.7.0
2. 进入菜单：`Extensions` → `Manage extensions`
3. 点击 **Add extension**
4. 选择 `qupath-extension-align-0.7.0.jar`
5. 点击确认安装

### 2.3 启动插件

安装完成后，插件入口位于：

```
Menu → Extensions → Alignment
```

---

## 3. 界面概览

插件界面分为三个主要区域：

### 3.1 Image & Overlays（图像与叠加层）

```
┌─────────────────────────────────┐
│  ☐ Image 1                      │
│  ☑ Image 2  ← 选中的叠加图像     │
│  ☐ Image 3                      │
├─────────────────────────────────┤
│ [Choose images from project]    │
├─────────────────────────────────┤
│ Opacity: ────●───── 100%        │
└─────────────────────────────────┘
```

| 控件 | 说明 |
|------|------|
| 复选框列表 | 选择要叠加的图像 |
| Choose images | 从当前项目选择图像 |
| Opacity | 调整叠加图像透明度 |

### 3.2 Interactive Alignment（交互式对齐）

```
┌─────────────────────────────────┐
│ Adjust by Shift+drag on image   │
│ Rotation increment: [1°]  ◀ ▶   │
│ Offset increment: [10px]  ↑↓←→  │
└─────────────────────────────────┘
```

| 控件 | 说明 |
|------|------|
| Rotation increment | 旋转步长（度） |
| Rotate Left/Right | 左/右旋转叠加图像 |
| Offset increment | 平移步长（像素） |
| ↑↓←→ | 上下左右平移叠加图像 |

> **提示**：也可以按住 `Shift` 键并拖动图像进行快速平移

### 3.3 Auto-Alignment（自动对齐）

```
┌─────────────────────────────────┐
│ ⚠ Auto-align works better after │
│   coarse manual alignment       │
│                                 │
│ Transformation: [Affine ▼]      │
│ Alignment type: [Intensity ▼]  │
│ Pixel size: [20] microns        │
│                                 │
│ [====== Estimate Transform ====]│
└─────────────────────────────────┘
```

### 3.4 Affine Transform（仿射变换）

```
┌─────────────────────────────────┐
│ Current transform matrix:       │
│ ┌───────────────────────────┐   │
│ │ 1.0000   0.0000   0.0000  │   │
│ │ 0.0000   1.0000   0.0000  │   │
│ └───────────────────────────┘   │
│ [Update][Invert][Reset][Copy]  │
│ [========== Propagate =========]│
└─────────────────────────────────┘
```

| 按钮 | 说明 |
|------|------|
| Update | 用文本框中的矩阵更新变换 |
| Invert | 反转变换方向 |
| Reset | 重置为初始变换 |
| Copy | 复制矩阵到剪贴板 |
| Propagate | 将注释传播到叠加图像 |

---

## 4. 快速入门

### 4.1 基本对齐流程

```
步骤 1: 打开基础图像
        ↓
步骤 2: 打开 Alignment 窗口 (Extensions → Alignment)
        ↓
步骤 3: 点击 "Choose images from project" 选择叠加图像
        ↓
步骤 4: 在列表中勾选要叠加的图像
        ↓
步骤 5: 手动粗调 (旋转 + 平移)
        ↓
步骤 6: 点击 "Estimate Transform" 自动精细对齐
        ↓
步骤 7: (可选) 使用 Propagate 传播注释
```

### 4.2 手动对齐示例

1. 打开 QuPath 并加载项目
2. 打开一张图像作为基础图像
3. `Menu → Extensions → Alignment` 打开对齐窗口
4. 点击 **Choose images from project**，选择第二张图像
5. 选中第二张图像（左侧复选框打勾）
6. 使用 **←→↑↓** 按钮或 **Shift+拖动** 进行平移
7. 使用 **◀ ▶** 按钮进行旋转
8. 满意后点击 **Estimate Transform** 进行自动优化

---

## 5. 功能详解

### 5.1 变换类型 (Transformation Types)

| 类型 | 说明 | 适用场景 |
|------|------|----------|
| **Affine** | 旋转 + 平移 + X/Y独立缩放 + 剪切 | 图像有非均匀变形 |
| **Rigid** | 旋转 + 平移 + 统一缩放 | 图像仅需刚体对齐 |

#### Affine 变换矩阵

```
| scaleX  shearX  translateX |
| shearY  scaleY  translateY |
```

Affine 模式可以处理：
- X轴和Y轴的不同缩放比例
- 图像剪切变形
- 旋转 + 平移

#### Rigid 变换矩阵

```
| cos(θ)  -sin(θ)  translateX |
| sin(θ)   cos(θ)  translateY |
```

Rigid 模式强制：
- `scaleX == scaleY`（统一缩放）
- 无剪切变形

### 5.2 对齐类型 (Alignment Type)

| 类型 | 说明 | 依赖 |
|------|------|------|
| **Image Intensity** | 基于像素值匹配 | 无 |
| **Area Annotations** | 基于面积注释匹配 | 需要相同类的注释 |
| **Point Annotations** | 基于点注释匹配 | 需要对应点 |

#### Image Intensity（推荐）

- 使用 ECC 算法基于图像灰度/强度值进行匹配
- **不需要任何注释**
- 自动对齐效果取决于图像相似度
- 建议先手动粗调再自动对齐

#### Area Annotations

- 将两张图像的面积注释作为特征进行匹配
- 需要两张图像都有**相同类**的注释
- 适合标注了组织区域的情况

#### Point Annotations

- 使用对应点进行对齐
- **两张图像必须具有相同数量的点**
- 每个点一一对应
- 适合有明确地标点的场景

### 5.3 Propagate（注释传播）

将基础图像上的注释**变换后**复制到叠加图像。

**使用条件**：
- 两张图像必须在同一个项目中
- 基础图像上有注释对象

**操作步骤**：
1. 完成图像对齐
2. 确保基础图像有注释
3. 点击 **Propagate**
4. 注释会被变换后添加到叠加图像

---

## 6. 参数说明

### 6.1 Pixel Size（像素大小）

```
Pixel size: 20 microns
```

控制自动对齐时使用的图像分辨率。

| 值 | 效果 |
|------|------|
| 0 | 使用完整原始图像 |
| 较小值 (如 5-10) | 高分辨率，精度高但速度慢 |
| 较大值 (如 50-100) | 低分辨率，速度快，适合大图 |

**大图像建议**：如果图像很大（如 80000×50000 像素），Java 可能无法处理，建议设置较大的 Pixel Size (如 50-100)。

### 6.2 Rotation Increment（旋转步长）

每次点击旋转按钮时图像旋转的角度（度）。

### 6.3 Offset Increment（平移步长）

每次点击方向按钮时图像平移的像素数。

---

## 7. 常见问题

### Q1: 按钮都是灰色的怎么办？

**原因**：没有选择叠加图像

**解决**：
1. 先打开一张基础图像
2. 点击 "Choose images from project" 选择叠加图像
3. 在列表中勾选要使用的叠加图像

---

### Q2: "No points found" 错误

**错误信息**：
```
No points found for either image ImageData: ... or ImageData: ...
```

**原因**：选择了 Point Annotations 对齐模式，但图像上没有点注释

**解决**：
1. 将 Alignment type 改为 **Image Intensity**
2. 或者在两张图像上添加对应的点注释

---

### Q3: "Dimensions too large" 错误

**错误信息**：
```
Dimensions (width=84848 height=50808) are too large
```

**原因**：图像尺寸太大，Java 无法处理

**解决**：
1. 增加 Pixel Size 值（如改为 50 或 100）
2. 或使用图像金字塔/瓦片处理

---

### Q4: 自动对齐效果不好

**建议流程**：
1. **先手动粗调**：用方向键和旋转按钮将图像大致对齐
2. **选择合适模式**：
   - 图像有变形 → Affine
   - 仅需旋转平移 → Rigid
3. **设置合适的 Pixel Size**：大图用较大值
4. **多次迭代**：手动调整后再自动对齐

---

### Q5: Propagate 按钮不可用

**原因**：
- 叠加图像不在项目中
- 没有正确选择叠加图像

**解决**：
1. 确保两张图像都在同一个 QuPath 项目中
2. 选中叠加图像（不是基础图像）

---

### Q6: 如何手动精细调整？

**方法1**：方向键平移
- 设置较小的 Offset increment（如 1-5 像素）
- 使用 ↑↓←→ 进行微调

**方法2**：Shift+拖动
- 按住 Shift 键
- 在图像上拖动进行平移

**方法3**：旋转+平移组合
- 先粗略旋转
- 再平移校正位置
- 循环迭代直到满意

---

## 附录：键盘快捷键

| 操作 | 快捷键 |
|------|--------|
| 平移图像 | Shift + 拖动 |
| 缩放（鼠标滚轮） | Ctrl + 滚轮 |
| 平滑滚动 | 按住空格 + 拖动 |

---

## 更新日志

| 版本 | 日期 | 说明 |
|------|------|------|
| 0.7.0 | 2026-03-26 | 适配 QuPath 0.7.0，新增平移调整功能 |
| 0.5.0 | - | 初始版本 |

---

*文档更新时间：2026-03-26*
